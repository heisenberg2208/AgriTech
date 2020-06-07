package com.example.pankaj.farmguide;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Note {

    private String documentId;
    private String title;
    private String description;
    private int priority;
    private List<String> tags;

    public Note()
    {

    }

    public Note(String title, String description, int priority, List<String> tags) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

