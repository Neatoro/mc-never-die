package de.mschramm.neverdie.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;
import de.mschramm.neverdie.quests.QuestManager;
import de.mschramm.neverdie.repositories.AttackRepository;
import de.mschramm.neverdie.repositories.PlayerRepository;

public class InitPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        InitPlayer.initializePlayer(player);
    }

    public static void initializePlayer(Player player) {
        PlayerRepository playerRepository = PlayerRepository.getInstance();

        if (!playerRepository.isPlayerRegistered(player)) {
            playerRepository.addNewPlayer(player);
        }

        PlayerEntity entity = playerRepository.getPlayerEntity(player);
        PlayerLifesUpdatedEvent updatedEvent = new PlayerLifesUpdatedEvent(player, entity);
        Bukkit.getPluginManager().callEvent(updatedEvent);

        AttackRepository.getInstance().addPlayer(player);
        QuestManager.getInstance().addQuestDisplay(player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        AttackRepository.getInstance().removePlayer(event.getPlayer());
    }

}
