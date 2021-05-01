package de.mschramm.neverdie.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.mschramm.neverdie.entities.PlayerEntity;

public class PlayerLifesUpdatedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private PlayerEntity entity;

    public PlayerLifesUpdatedEvent(Player player, PlayerEntity entity) {
        this.player = player;
        this.entity = entity;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerEntity getEntity() {
        return this.entity;
    }

}
