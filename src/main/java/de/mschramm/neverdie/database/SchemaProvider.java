package de.mschramm.neverdie.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SchemaProvider {

    public static void initializeSchema() throws SQLException {
        Connection connection = Connector.getInstance().getConnection();

        PreparedStatement statement = connection.prepareStatement(
            "CREATE TABLE IF NOT EXISTS players (id VARCHAR(255) PRIMARY KEY, lifes INT)"
        );

        statement.execute();
    }

}
