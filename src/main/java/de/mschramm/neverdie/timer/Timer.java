package de.mschramm.neverdie.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.mschramm.neverdie.NeverDiePlugin;

public class Timer extends Thread {

    private class MoveListener implements Listener {

        @EventHandler
        public void handleMove(PlayerMoveEvent event) {
            event.setCancelled(true);
        }

    }

    private MoveListener listener;

    public Timer() {
        this.listener = new MoveListener();
    }

    public void run() {
        Bukkit.getServer()
            .getPluginManager()
            .registerEvents(
                this.listener,
                NeverDiePlugin.getPlugin(NeverDiePlugin.class)
            );

        try {
            Thread.sleep(2000);
            this.broadcastTitle("3...");
            Thread.sleep(2000);
            this.broadcastTitle("2...");
            Thread.sleep(2000);
            this.broadcastTitle("1...");
            Thread.sleep(2000);
            this.broadcastTitle(ChatColor.GREEN + "Go!" + ChatColor.WHITE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            HandlerList.unregisterAll(this.listener);
        }
    }

    private void broadcastTitle(String title) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendTitle(title, null, 20, 70, 20);
        }
    }

}
