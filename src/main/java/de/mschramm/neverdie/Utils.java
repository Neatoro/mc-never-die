package de.mschramm.neverdie;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

}
