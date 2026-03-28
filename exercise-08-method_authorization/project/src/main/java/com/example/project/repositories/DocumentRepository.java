package com.example.project.repositories;

import com.example.project.entities.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DocumentRepository {

    private Map<String, Document> documents=Map.of(
            "abc123", new Document("john"),
            "abc456", new Document("jane")
    );
    public Document findDocument(String id) {
        return documents.get(id);
    }
}
