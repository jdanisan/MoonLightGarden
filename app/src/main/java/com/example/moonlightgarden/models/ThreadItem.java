package com.example.moonlightgarden.models;

import java.util.HashMap;
import java.util.Map;

public class ThreadItem {

    private String id;
    private String title;
    private String content;
    private String author;
    private long timestamp;

    // Constructor vacío requerido por Firebase
    public ThreadItem() {}

    public ThreadItem(String id, String title, String content, String author, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    // ==== GETTERS y SETTERS ====
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Para guardar en Firebase como MAP
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("content", content);
        map.put("author", author);
        map.put("timestamp", timestamp);
        return map;
    }
}
