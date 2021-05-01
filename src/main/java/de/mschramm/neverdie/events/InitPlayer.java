package de.mschramm.neverdie.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.mschramm.neverdie.repositories.LifeRepository;

public class InitPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LifeRepository repository = LifeRepository.getInstance();
        if (!repository.isPlayerRegistered(player)) {
            repository.addNewPlayer(player);
        }
    }

}
