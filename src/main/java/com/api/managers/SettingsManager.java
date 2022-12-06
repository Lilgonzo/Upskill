package com.api.managers;

import com.api.entities.Settings;
import com.api.utils.DbComm;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SettingsManager {

    /**
     * Updates settings
     * @param securityContext the users settings
     * @param settings the settings to set
     * @throws Exception e
     */
    public void updateSettings(SecurityContext securityContext, Settings settings) throws Exception {
        String sql =
                "update settings " +
                        "set age=? where userID=" + securityContext.getUserPrincipal().getName();

        try (
                Connection connection = DbComm.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, settings.getAge());
            preparedStatement.executeUpdate();

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }
}
