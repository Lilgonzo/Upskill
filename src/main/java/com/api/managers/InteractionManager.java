package com.api.managers;

import com.api.entities.Interaction;
import com.api.entities.Profile;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class InteractionManager {

    /**
     * Retrieves matches
     * @param securityContext the user
     * @return matches
     * @throws Exception e
     */
    public List<Interaction> getMatches(SecurityContext securityContext) throws Exception {
        String sql =
                "select username from interactions i " +
                        "join profile on userID=i.toUserId " +
                        "where " +
                        "(fromUserId=? and `like`=1) ";
// todo
        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, securityContext.getUserPrincipal().getName());
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();

            List<Interaction> interactions = new LinkedList<>();
            Interaction interaction = new Interaction();
            Profile profile;
            if (resultSet.next()) {
                profile = new Profile();
                profile.setUsername(resultSet.getString("username"));
                interaction.setToUser(profile);
                interactions.add(interaction);
            }

            return interactions;
        }
    }

    /**
     * Likes/Dislikes user based on the value passed by interaction object didLike.
     * @param securityContext the user
     * @param interaction the like and the user interacting with
     * @return update interaction
     * @throws Exception e
     */
    public Interaction likeUser(SecurityContext securityContext, Interaction interaction) throws Exception {
        String userId = securityContext.getUserPrincipal().getName();
        String sql =
            "insert into interactions set " +
                "`like`=" + interaction.getLike() + ", toUserId=" + interaction.getToUser().getUserID() + ", fromUserId=" + userId + " " +
                "on duplicate key update " +
                "`like`=" + interaction.getLike() + ", toUserId=" + interaction.getToUser().getUserID() + ", fromUserId=" + userId;

        try (
                Connection connection = DbComm.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                interaction.setLike(resultSet.getInt("like"));
            }

            return interaction;
        }
    }
}
