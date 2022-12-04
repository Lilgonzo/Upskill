package com.api.managers;

import com.api.entities.Post;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PostManager {

    /**
     * Creates post.
     * @param securityContext the user creating a post
     * @param post the post to create
     */
    public void createPost(SecurityContext securityContext, Post post) throws Exception {
        String sql =
                "insert into posts(userID, title, description) VALUES (?, ?, ?)";

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setInt(1, Integer.parseInt(securityContext.getUserPrincipal().getName()));
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getDescription());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * Deletes post.
     * @param postId - the post to delete
     */
    public void deletePost(Integer postId) throws Exception {
        String sql =
                "delete from posts where postID=" + postId;

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
                ) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
