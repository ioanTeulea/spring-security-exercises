package com.example.project.entities;

public class Document {
    private String owner;

    public Document() {}
    public Document(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
