package de.mschramm.neverdie.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private static Connector instance;

    private Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connector getInstance() {
        if (Connector.instance == null) {
            Connector.instance = new Connector();
        }
        return Connector.instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:neverdie.db");
    }

}
