package de.mschramm.neverdie;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    public static String getFormattedItemName(ItemStack stack) {
        String materialName = stack.getType().toString();
        String[] words = Arrays.stream(materialName.split("_"))
            .map((word) -> word.substring(0, 1) + word.substring(1).toLowerCase())
            .toArray(String[]::new);

        return String.join(" ", words);
    }

}
