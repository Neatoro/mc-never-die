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

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.events.PlayerRespawnListener;
import de.mschramm.neverdie.quests.QuestManager;
import de.mschramm.neverdie.repositories.LifeRepository;
import de.mschramm.neverdie.timer.Timer;

public class StartCommand implements CommandExecutor {

    private static class StartCommandArguments {

        public double spawnX;
        public double spawnZ;
        public long seed;

        private StartCommandArguments(double spawnX, double spawnZ, long seed) {
            this.spawnX = spawnX;
            this.spawnZ = spawnZ;
            this.seed = seed;
        }

        public static StartCommandArguments parse(String[] args) {
            if (args.length == 3) {
                try {
                    Long seed = Long.parseLong(args[0]);
                    double x = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);

                    return new StartCommandArguments(
                        x,
                        z,
                        seed
                    );
                } catch (NumberFormatException exception) {
                    return null;
                }
            }

            return null;
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StartCommandArguments arguments = StartCommandArguments.parse(args);
        if (arguments == null) {
            return false;
        }

        WorldCreator worldCreator = new WorldCreator("game");
        worldCreator.seed(arguments.seed);
        World world = worldCreator.createWorld();
        WorldBorder border = world.getWorldBorder();

        double x = arguments.spawnX;
        double z = arguments.spawnZ;
        Location spawn = new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z);
        world.setSpawnLocation(spawn);
        border.setSize(700);
        border.setCenter(spawn);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.getInventory().clear();

            LifeRepository.getInstance().resetPlayerLifes(player);

            player.teleport(spawn);
        }

        Bukkit.getServer().dispatchCommand(sender, "spreadplayers 0 0 200 300 false @a");
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), NeverDiePlugin.getPlugin());

        Timer timer = new Timer();
        timer.start();

        QuestManager.getInstance();

        return true;
    }

}
