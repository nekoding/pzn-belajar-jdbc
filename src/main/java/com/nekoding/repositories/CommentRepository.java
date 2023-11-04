package com.nekoding.repositories;

import com.nekoding.models.Comment;

import java.util.List;

public interface CommentRepository {

    public Comment findById(String id);

    public void create(Comment comment);

    public List<Comment> getAll();
}
