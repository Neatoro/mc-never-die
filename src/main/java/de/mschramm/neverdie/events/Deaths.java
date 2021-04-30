package de.mschramm.neverdie.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import de.mschramm.neverdie.repository.LifeRepository;

public class Deaths implements Listener {

    @EventHandler
    public void onPlayerDeaths(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            LifeRepository repository = LifeRepository.getInstance();
            repository.reduceLifeFromPlayer(player);
        }
    }

}
