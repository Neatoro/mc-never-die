package de.mschramm.neverdie.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.repositories.LifeRepository;
import de.mschramm.neverdie.timer.Timer;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WorldCreator worldCreator = new WorldCreator("game");
        World world = worldCreator.createWorld();
        WorldBorder border = world.getWorldBorder();

        Location spawn = new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
        world.setSpawnLocation(spawn);

        border.setSize(700);
        border.setCenter(spawn);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().clear();
            LifeRepository.getInstance().resetPlayerLifes(player);

            player.teleport(spawn);
        }

        Bukkit.getServer().dispatchCommand(sender, "spreadplayers 0 0 200 300 false @a");

        Timer timer = new Timer();
        timer.start();

        return true;
    }

}
