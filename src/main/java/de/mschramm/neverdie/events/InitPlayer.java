package de.mschramm.neverdie.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;
import de.mschramm.neverdie.repositories.PlayerRepository;

public class InitPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerRepository playerRepository = PlayerRepository.getInstance();

        if (!playerRepository.isPlayerRegistered(player)) {
            playerRepository.addNewPlayer(player);
        }

        PlayerEntity entity = playerRepository.getPlayerEntity(player);
        PlayerLifesUpdatedEvent updatedEvent = new PlayerLifesUpdatedEvent(player, entity);
        Bukkit.getPluginManager().callEvent(updatedEvent);
    }

}
