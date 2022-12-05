package com.api.managers;

import com.api.entities.Interaction;
import com.api.entities.Profile;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
