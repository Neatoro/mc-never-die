package de.mschramm.neverdie.repositories;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.database.managers.PlayerEntityManager;
import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;

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

    private PlayerEntity getPlayerEntity(Player player) {
        PlayerEntity entity = PlayerRepository.getInstance().getPlayerEntity(player);
        return entity;
    }

    public int getLifesForPlayer(Player player) {
        PlayerEntity entity = this.getPlayerEntity(player);
        if (entity != null) {
            return entity.getLifes();
        }
        return -1;
    }

    public void resetPlayerLifes(Player player) {
        PlayerEntity entity = this.getPlayerEntity(player);
        if (entity != null) {
            entity.setLifes(3);
            try {
                this.entityManager.save(entity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            PlayerLifesUpdatedEvent updatedEvent = new PlayerLifesUpdatedEvent(player, entity);
            Bukkit.getPluginManager().callEvent(updatedEvent);
        }
    }

    public void reduceLifeFromPlayer(Player player) {
        try {
            PlayerEntity entity = this.getPlayerEntity(player);
            entity.setLifes(entity.getLifes() - 1);
            this.entityManager.save(entity);

            PlayerLifesUpdatedEvent updatedEvent = new PlayerLifesUpdatedEvent(player, entity);
            Bukkit.getPluginManager().callEvent(updatedEvent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
