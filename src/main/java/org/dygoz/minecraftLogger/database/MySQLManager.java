package org.dygoz.minecraftLogger.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    private String host;
    private int port;
    private String database;
    private String user;
    private String password;

    private Connection connection;

    public MySQLManager(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS login_data (" +
                            "login_data VARCHAR(36) PRIMARY KEY" +
                            ")"
            );
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerVerified(String playerName) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT risposta FROM login_data WHERE risposta = ?"
            );
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();
            rs.close();
            ps.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
