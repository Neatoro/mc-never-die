package de.mschramm.neverdie.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;

public class Displays implements Listener {

    @EventHandler
    public void onPlayerUpdatedLifes(PlayerLifesUpdatedEvent event) {
        Player player = event.getPlayer();
        PlayerEntity entity = event.getEntity();

        int lifes = entity.getLifes();
        ChatColor color;
        switch (lifes) {
            case 1:
                color = ChatColor.RED;
                break;
            case 2:
                color = ChatColor.YELLOW;
                break;
            case 3:
                color = ChatColor.GREEN;
                break;
            default:
                color = ChatColor.WHITE;
                break;
        }

        this.updatePlayerDisplays(player, color);
    }

    private void updatePlayerDisplays(Player player, ChatColor color) {
        player.setDisplayName(color + player.getName() + ChatColor.WHITE);
        player.setPlayerListName(color + player.getName() + ChatColor.WHITE);
    }

}
