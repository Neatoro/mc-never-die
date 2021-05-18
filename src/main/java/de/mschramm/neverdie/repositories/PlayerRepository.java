package de.mschramm.neverdie.repositories;

import java.sql.SQLException;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.database.managers.PlayerEntityManager;
import de.mschramm.neverdie.entities.PlayerEntity;

public class PlayerRepository {

    private static PlayerRepository instance;

    private PlayerEntityManager entityManager;

    private PlayerRepository() {
        this.entityManager = new PlayerEntityManager();
    }

    public static PlayerRepository getInstance() {
        if (PlayerRepository.instance == null) {
            PlayerRepository.instance = new PlayerRepository();
        }
        return PlayerRepository.instance;
    }

    public PlayerEntity getPlayerEntity(Player player) {
        try {
            return this.entityManager.getByKey(player.getUniqueId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPlayerRegistered(Player player) {
        PlayerEntity entity = this.getPlayerEntity(player);
        return entity != null;
    }

    public void addNewPlayer(Player player) {
        PlayerEntity playerEntity = new PlayerEntity(player);
        try {
            this.entityManager.save(playerEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void punishPlayers() {
        try {
            PlayerEntity[] entities = this.entityManager.getAll();
            for (PlayerEntity entity : entities) {
                entity.setHealth(Math.max(entity.getHealth() - 2, 6));
                this.entityManager.save(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addHealth(Player player) {
        try {
            PlayerEntity entity = this.entityManager.getByKey(player.getUniqueId());
            entity.setHealth(Math.min(entity.getHealth() + 2, PlayerEntity.INITAL_HEALTH));
            this.entityManager.save(entity);

            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(entity.getHealth());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
