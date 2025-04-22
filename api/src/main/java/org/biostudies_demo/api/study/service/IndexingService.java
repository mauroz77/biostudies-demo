package org.biostudies_demo.api.study.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class IndexingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    //@PostConstruct
    public void buildInitialIndex() {
        // Manually manage transaction since @PostConstruct and @Transactional don't work well together
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try {
                SearchSession searchSession = Search.session(entityManager);
                searchSession.massIndexer()
                        .startAndWait();
                return null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Indexing was interrupted", e);
            }
        });
    }

    // Keep this method for programmatic reindexing needs
    @Transactional
    public void indexAllStudies() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer()
                    .startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Indexing was interrupted", e);
        }
    }
}