package com.api.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Establishes connection to database
 */
public final class DbUtil {
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private DbUtil(){}

    // Executes once class to loaded
    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
        DRIVER = resourceBundle.getString("jdbc.driver");
        URL = resourceBundle.getString("jdbc.url");
        USERNAME = resourceBundle.getString("jdbc.username");
        PASSWORD = System.getenv("MYSQL_PASS");
    }

    /**
     * Establishes connection
     *
     * @return connection - the connection object
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}