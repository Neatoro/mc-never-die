package de.mschramm.neverdie.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;

public class Spectator implements Listener {

    @EventHandler
    public void onPlayerUpdateLife(PlayerLifesUpdatedEvent event) {
        PlayerEntity entity = event.getEntity();
        if (entity.getLifes() == 0) {
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

}
