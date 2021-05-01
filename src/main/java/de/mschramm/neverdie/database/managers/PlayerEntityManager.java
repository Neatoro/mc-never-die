package de.mschramm.neverdie.database.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.mschramm.neverdie.database.Connector;
import de.mschramm.neverdie.entities.PlayerEntity;

public class PlayerEntityManager extends Manager<PlayerEntity, UUID> {

    @Override
    public void save(PlayerEntity entity) throws SQLException {
        Connection connection = Connector.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "INSERT OR REPLACE INTO players VALUES (?, ?)"
        );

        statement.setString(1, entity.getId().toString());
        statement.setInt(2, entity.getLifes());

        statement.executeUpdate();

        connection.close();
    }

    @Override
    public PlayerEntity getByKey(UUID key) throws SQLException {
        Connection connection = Connector.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT id, lifes FROM players WHERE id = ?"
        );
        statement.setString(1, key.toString());

        ResultSet resultSet = statement.executeQuery();
        PlayerEntity entity = null;
        if (resultSet.next()) {
            entity = new PlayerEntity(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getInt("lifes")
            );
        }

        connection.close();
        return entity;
    }

    @Override
    public PlayerEntity[] getAll() throws SQLException {
        List<PlayerEntity> entities = new ArrayList<>();

        Connection connection = Connector.getInstance().getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT id, lifes FROM players");


        while (resultSet.next()) {
            PlayerEntity entity = new PlayerEntity(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getInt("lifes")
            );
            entities.add(entity);
        }

        connection.close();
        return (PlayerEntity[]) entities.toArray();
    }

}
