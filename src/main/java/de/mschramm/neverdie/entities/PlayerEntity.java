package de.mschramm.neverdie.entities;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerEntity {

    public static final int INITAL_LIFES = 3;
    public static final int INITAL_HEALTH = 20;

    private UUID id;
    private int lifes;
    private int health;

    public PlayerEntity(Player player) {
        this(player.getUniqueId(), PlayerEntity.INITAL_LIFES, PlayerEntity.INITAL_HEALTH);
    }

    public PlayerEntity(UUID id, int lifes, int health) {
        this.id = id;
        this.lifes = lifes;
        this.health = health;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getLifes() {
        return this.lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
