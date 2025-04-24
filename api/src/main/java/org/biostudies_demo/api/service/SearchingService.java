package org.biostudies_demo.api.service;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchingService {

    @Value("${lucene_index_dir}")
    private String luceneIndexDir;

    private static final String[] SEARCH_FIELDS = {"title", "abstractText", "authors"};

    public List<StudySummary> search(String queryStr) throws IOException, ParseException {
        List<StudySummary> results = new ArrayList<>();

        try (
                FSDirectory directory = FSDirectory.open(Paths.get(luceneIndexDir));
                DirectoryReader reader = DirectoryReader.open(directory)
        ) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            MultiFieldQueryParser parser = new MultiFieldQueryParser(SEARCH_FIELDS, analyzer);
            Query query = parser.parse(queryStr);

            TopDocs topDocs = searcher.search(query, 10); // return top 10 matches

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);

                StudySummary summary = new StudySummary();
                summary.setAccno(doc.get("accno"));
                summary.setTitle(doc.get("title"));
                summary.setAbstractText(doc.get("abstractText"));
                summary.setAuthors(doc.get("authors"));

                results.add(summary);
            }
        }

        return results;
    }

    public LuceneSearchResult search(String queryStr, int page, int pageSize) throws IOException, ParseException {
        List<String> accnos = new ArrayList<>();

        try (
                FSDirectory directory = FSDirectory.open(Paths.get(luceneIndexDir));
                DirectoryReader reader = DirectoryReader.open(directory)
        ) {
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();
            MultiFieldQueryParser parser = new MultiFieldQueryParser(SEARCH_FIELDS, analyzer);
            Query query = parser.parse(queryStr);

            int start = (page - 1) * pageSize;
            int end = start + pageSize;

            TopDocs topDocs = searcher.search(query, end);
            ScoreDoc[] hits = topDocs.scoreDocs;

            for (int i = start; i < Math.min(end, hits.length); i++) {
                Document doc = searcher.doc(hits[i].doc);
                accnos.add(doc.get("accno"));
            }

            LuceneSearchResult result = new LuceneSearchResult();
            result.setAccnos(accnos);
            result.setTotalHits(topDocs.totalHits.value);
            result.setTotalHitsExact(topDocs.totalHits.relation == TotalHits.Relation.EQUAL_TO);
            return result;
        }
    }
}
