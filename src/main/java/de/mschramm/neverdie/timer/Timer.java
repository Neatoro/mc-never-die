package de.mschramm.neverdie.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.QuestManager;

public class Timer extends Thread {

    private class MoveListener implements Listener {

        @EventHandler
        public void handleMove(PlayerMoveEvent event) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
                event.setCancelled(true);
            }
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
            Thread.sleep(1500);
            this.broadcastTitle("3...");
            Thread.sleep(1500);
            this.broadcastTitle("2...");
            Thread.sleep(1500);
            this.broadcastTitle("1...");
            Thread.sleep(1500);
            this.broadcastTitle(ChatColor.GREEN + "Go!" + ChatColor.WHITE);

            Bukkit.getScheduler().runTaskLater(
                NeverDiePlugin.getPlugin(NeverDiePlugin.class),
                () -> {
                    Utils.broadcast(ChatColor.GOLD + "Seid gegrueßt! Ich heisse Bob, kommt zu mir und ich biete euch exotische Waren im Tauschhandel an! Haltet nach dem Lichtstrahl Ausschau!");
                    QuestManager.getInstance().setupQuest();
                },
                5 * 60 * 20
            );

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
