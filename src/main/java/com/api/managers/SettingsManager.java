package com.api.managers;

import com.api.entities.Settings;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.Statement;

public class SettingsManager {

    /**
     * Updates settings
     * @param securityContext the users settings
     * @param settings the settings to set
     * @throws Exception e
     */
    public void updateSettings(SecurityContext securityContext, Settings settings) throws Exception {
        String userId = securityContext.getUserPrincipal().getName();
        String sql =
                "insert into settings " +
                        "set age=" + settings.getAge() + " , userId=" + userId + " " +
                        "on duplicate key update " +
                        "age=" + settings.getAge();

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
