package com.nekoding;

import com.nekoding.models.Comment;
import com.nekoding.repositories.CommentRepository;
import com.nekoding.repositories.CommentRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CommentRepositoryTest {

    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentRepository = new CommentRepositoryImpl();
    }

    @Test
    void testCreateComment() {
        Comment comment = new Comment("enggar", "hello world");
        commentRepository.create(comment);

        System.out.println(comment.getId());
        Assertions.assertNotNull(comment.getId());
    }

    @Test
    void testFindCommentById() {
        Comment comment = commentRepository.findById("89f01a75-48a2-4a2f-96eb-e9a11b9aa9ba");
        Assertions.assertNotNull(comment);

        System.out.println(comment.getId());
        System.out.println(comment.getName());
        System.out.println(comment.getComment());
    }

    @Test
    void testFindAllComment() {
        List<Comment> comments = commentRepository.getAll();
        Assertions.assertEquals(1, comments.size());

        comments.forEach(comment -> {
            System.out.println(comment.getId());
        });
    }
}
