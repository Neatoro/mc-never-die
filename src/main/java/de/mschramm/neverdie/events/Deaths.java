package de.mschramm.neverdie.events;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.repositories.LifeRepository;

public class Deaths implements Listener {

    @EventHandler
    public void onPlayerDeaths(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            LifeRepository repository = LifeRepository.getInstance();
            repository.reduceLifeFromPlayer(player);
            Utils.playSound(Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.MASTER, 0.4f, 1.1f);
        }
    }

}
