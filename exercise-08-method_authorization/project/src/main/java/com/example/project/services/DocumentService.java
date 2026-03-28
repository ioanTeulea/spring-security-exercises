package com.example.project.services;

import com.example.project.entities.Document;
import com.example.project.repositories.DocumentRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PostAuthorize("hasPermission(returnObject,'ROLE_ADMIN')")
    public Document getDocument(String id) {
        return documentRepository.findDocument(id);
    }


    @PreFilter("filterObject.owner == authentication.name")
    public List<Document> signDocuments(List<Document> documents){
        //sign the documents
        return documents;
    }

    @PostFilter("filterObject.owner == authentication.name")
    public List<Document> findDocuments() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document("nikolai"));
        documents.add(new Document("john"));
        documents.add(new Document("jane"));
        return documents;
    }
}
