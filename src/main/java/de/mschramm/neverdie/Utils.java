package de.mschramm.neverdie;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class Utils {

    public static void broadcast(String message) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public static void playSound(Sound sound, SoundCategory category, float volume, float pitch) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, category, volume, pitch);
        }
    }

}
