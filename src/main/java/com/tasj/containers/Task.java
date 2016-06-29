package com.tasj.containers;

public class Task {
    private String description;
    private boolean done;
    private String title;
    private String uri;

    public Task(String title, String description, String uri, boolean done) {
        this.title = title;
        this.description = description;
        this.uri = uri;
        this.done = done;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(String title) {
        this.title = title;
    }

    public Task() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

