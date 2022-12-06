package com.api.managers;

import com.api.entities.Rating;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.Statement;

public class RatingManager {

    /**
     * Updates rating
     * @param securityContext the user rating
     * @param rating the rating
     * @throws Exception e
     */
    public void updateRating(SecurityContext securityContext, Rating rating) throws Exception {
        String userId = securityContext.getUserPrincipal().getName();
        String sql =
                "insert into rating " +
                    "set rating=" + rating.getRating() + ", " +
                    "toUserID= " + rating.getToUser().getUserID() + ", " +
                    "fromUserID=" + userId + " " +
                        "on duplicate key update " +
                        "rating=" + rating.getRating() + ", " +
                        "toUserID=" + rating.getToUser().getUserID() + ", " +
                        "fromUserID=" + userId;


        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }
}