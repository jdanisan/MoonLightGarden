package com.example.moonlightgarden.models;

public class foodItem {
    private String name;
    private String description;
    private String imageUrl;

    public foodItem() {} // Constructor vacío requerido por Firebase

    public foodItem(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}
