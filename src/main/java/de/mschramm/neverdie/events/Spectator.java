package de.mschramm.neverdie.events;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;

public class Spectator implements Listener {

    @EventHandler
    public void onPlayerUpdateLife(PlayerLifesUpdatedEvent event) {
        PlayerEntity entity = event.getEntity();
        if (entity.getLifes() == 0) {
            Utils.playSound(Sound.ENTITY_WOLF_HOWL, SoundCategory.MASTER, 0.5f, 0.9f);
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }

}
