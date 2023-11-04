package com.nekoding.models;

public class Comment {

    private String id;
    private String name;
    private String comment;

    public Comment() {
    }

    public Comment(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public Comment(String id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
