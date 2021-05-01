package de.mschramm.neverdie.entities;

import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerEntity {

    public static final int INITAL_LIFES = 3;

    private UUID id;
    private int lifes;

    public PlayerEntity(Player player) {
        this(player.getUniqueId(), PlayerEntity.INITAL_LIFES);
    }

    public PlayerEntity(Player player, int lifes) {
        this(player.getUniqueId(), lifes);
    }

    public PlayerEntity(UUID id, int lifes) {
        this.id = id;
        this.lifes = lifes;
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
}
