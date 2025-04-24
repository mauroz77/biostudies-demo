package org.biostudies_demo.api.study.service;

import jakarta.annotation.PostConstruct;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.biostudies_demo.api.study.model.Study;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexingService {

    @Value("${lucene_index_dir}")
    private String luceneIndexDir;

    private Directory indexDirectory;
    private Analyzer analyzer;
    private IndexWriter indexWriter;

    @PostConstruct
    public void init() throws IOException {
        indexDirectory = FSDirectory.open(Paths.get(luceneIndexDir));
        analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        indexWriter = new IndexWriter(indexDirectory, config);
    }

    public void indexStudySummaries(List<Study> studies)  throws IOException {
        List<StudySummary> studySummaries = new ArrayList<>();
        if (studies != null) {
            for (Study study : studies) {
                StudySummary studySummary = studyToStudySummary(study);
                Document document = createDocument(studySummary);
                indexWriter.addDocument(document);
            }
            indexWriter.commit();
        }
    }

    public void close() throws IOException {
        indexWriter.close();
        indexDirectory.close();
    }

    private Document createDocument(StudySummary studySummary) {
        Document doc = new Document();
        doc.add(new StringField("accno", studySummary.getAccno(), Field.Store.YES));
        doc.add(new TextField("title", studySummary.getTitle(), Field.Store.YES));
        doc.add(new TextField("abstractText", studySummary.getAbstractText(), Field.Store.YES));
        doc.add(new TextField("authors", studySummary.getAuthors(), Field.Store.YES));
        return doc;
    }

    private StudySummary studyToStudySummary(Study study) {
        StudySummary studySummary = new StudySummary();
        studySummary.setAccno(study.getAccno());
        studySummary.setTitle(study.getTitle());
        studySummary.setAbstractText(study.getSection().getAbstract());
        studySummary.setAuthors(study.getAuthorsAsText());
        return studySummary;
    }
}