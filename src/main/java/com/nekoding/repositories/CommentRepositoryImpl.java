package com.nekoding.repositories;

import com.nekoding.models.Comment;
import com.nekoding.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepository{
    @Override
    public Comment findById(String id) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = "SELECT * FROM comments WHERE id = ? LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setId(resultSet.getString("id"));
                        comment.setName(resultSet.getString("name"));
                        comment.setComment(resultSet.getString("comment"));

                        return comment;
                    }
                }
            }

            return null;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void create(Comment comment) {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = "INSERT INTO comments(name, comment) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                statement.setString(1, comment.getName());
                statement.setString(2, comment.getComment());
                statement.executeUpdate();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                   if (resultSet.next()) {
                       comment.setId(resultSet.getString("id"));
                   }
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Connection connection = ConnectionUtil.getDataSource().getConnection()) {
            String sql = "SELECT * FROM comments";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    ArrayList<Comment> comments = new ArrayList<>();

                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        comment.setId(resultSet.getString("id"));
                        comment.setName(resultSet.getString("name"));
                        comment.setComment(resultSet.getString("comment"));

                        comments.add(comment);
                    }

                    return comments;
                }
            }

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
