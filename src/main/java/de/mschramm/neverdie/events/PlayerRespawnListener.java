package de.mschramm.neverdie.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        System.out.println("RESPAWN PLAYER");
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("game");

        if (player.getBedSpawnLocation() == null) {
            event.setRespawnLocation(world.getSpawnLocation());
        } else {
            Location bed = player.getBedSpawnLocation();
            Location bedInGameWorld = bed.clone();
            bedInGameWorld.setWorld(world);
            event.setRespawnLocation(bedInGameWorld);
        }
    }

}
