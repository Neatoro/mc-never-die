package de.mschramm.neverdie.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.mschramm.neverdie.entities.PlayerEntity;
import de.mschramm.neverdie.events.custom.PlayerLifesUpdatedEvent;

public class Displays implements Listener {

    @EventHandler
    public void onPlayerUpdatedLifes(PlayerLifesUpdatedEvent event) {
        Player player = event.getPlayer();
        PlayerEntity entity = event.getEntity();

        int lifes = entity.getLifes();
        if (lifes > 0) {
            this.registerTeams(player.getScoreboard());
            player.getScoreboard().getTeam("" + lifes).addEntry(player.getName());
        } else if (player.getScoreboard().getEntryTeam(player.getName()) != null) {
            player.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
    }

    private void registerTeams(Scoreboard scoreboard) {
        this.registerTeam(scoreboard, "1", ChatColor.RED);
        this.registerTeam(scoreboard, "2", ChatColor.YELLOW);
        this.registerTeam(scoreboard, "3", ChatColor.GREEN);
    }

    private Team registerTeam(Scoreboard scoreboard, String name, ChatColor color) {
        Team team = scoreboard.getTeam(name);
        if (team == null) {
            team = scoreboard.registerNewTeam(name);
        }
        team.setColor(color);
        team.setAllowFriendlyFire(true);
        return team;
    }

}
