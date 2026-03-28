package com.example.project.controllers;

import com.example.project.entities.Document;
import com.example.project.services.DocumentService;
import com.example.project.services.NameService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private final NameService nameService;
    private final DocumentService documentService;

    public HelloController(NameService nameService,DocumentService documentService) {
        this.nameService = nameService;
        this.documentService=documentService;
    }

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        return "Hello, " + nameService.getName(authentication.getName()) + "!";
    }
    @GetMapping("details/{username}")
    public String details(@PathVariable String username) {
        return nameService.getDetails(username);
    }
    @GetMapping("/documents/{code}")
    public Document getDetails(@PathVariable String code) {
        return documentService.getDocument(code);
    }
    @PostMapping("/documents/sign")
    public List<Document> signDocuments(@RequestBody List<Document> documents) {
        return documentService.signDocuments(documents);
    }
    @GetMapping("/documents/find")
    public List<Document> findDocuments() {
        return documentService.findDocuments();
    }

}
