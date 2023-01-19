package com.progmatic.jdbc;


import java.sql.*;
import java.util.Properties;

public class DBEngine implements AutoCloseable {

    private Connection connection;

    public DBEngine() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        String url = String.format(
                "jdbc:mysql://%s:%s/%s",
                System.getenv("host"),
                System.getenv("port"),
                System.getenv("Database")
        );

        Properties prop = new Properties(2);
        prop.put("user", System.getenv("user"));
        prop.put("password", System.getenv("password"));

        try {
            this.connection = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            System.out.println("HIBA a kapcsolat letrehozasa soran \n\t" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public boolean isClosed() throws SQLException {
        return connection == null || connection.isClosed();
    }
}
