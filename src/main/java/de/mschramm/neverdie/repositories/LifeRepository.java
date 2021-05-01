package de.mschramm.neverdie.repositories;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import de.mschramm.neverdie.database.managers.PlayerEntityManager;
import de.mschramm.neverdie.entities.PlayerEntity;

public class LifeRepository {

    private static LifeRepository instance;

    private PlayerEntityManager entityManager;

    private LifeRepository() {
        this.entityManager = new PlayerEntityManager();
    }

    public static LifeRepository getInstance() {
        if (LifeRepository.instance == null) {
            LifeRepository.instance = new LifeRepository();
        }
        return LifeRepository.instance;
    }

    private PlayerEntity getPlayerEntity(Player player) throws SQLException {
        PlayerEntity entity = this.entityManager.getByKey(player.getUniqueId());
        return entity;
    }

    public int getLifesForPlayer(Player player) {
        try {
            PlayerEntity entity = this.getPlayerEntity(player);
            return entity.getLifes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isPlayerRegistered(Player player) {
        try {
            PlayerEntity entity = this.getPlayerEntity(player);
            return entity != null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addNewPlayer(Player player) {
        PlayerEntity playerEntity = new PlayerEntity(player);
        try {
            this.entityManager.save(playerEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reduceLifeFromPlayer(Player player) {
        try {
            PlayerEntity entity = this.getPlayerEntity(player);
            entity.setLifes(entity.getLifes() - 1);
            this.entityManager.save(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
