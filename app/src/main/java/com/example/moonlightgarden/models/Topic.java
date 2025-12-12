package com.example.moonlightgarden.models;

public class Topic {

    private String id;
    private String title;
    private String description;

    public Topic() {
        // Constructor vacío requerido por Firebase / adapters
    }

    public Topic(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
