package de.mschramm.neverdie.quests;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.NeverDiePlugin;

public class QuestManager {

    private static QuestManager instance;

    private Location questLocation;

    private QuestManager() {
        Bukkit.getServer().getPluginManager().registerEvents(
            new BeaconProtection(),
            NeverDiePlugin.getPlugin(NeverDiePlugin.class)
        );
    }

    public static QuestManager getInstance() {
        if (QuestManager.instance == null) {
            QuestManager.instance = new QuestManager();
        }
        return QuestManager.instance;
    }

    public void setupQuest() {
        Random random = new Random();

        World world = Bukkit.getWorld("game");
        WorldBorder border = world.getWorldBorder();
        Location center = border.getCenter();

        int minX = (int) (center.getX() - border.getSize() / 2);
        int maxX = (int) (center.getX() + border.getSize() / 2);
        int minZ = (int) (center.getZ() - border.getSize() / 2);
        int maxZ = (int) (center.getZ() + border.getSize() / 2);

        int x = random.nextInt(maxX - minX) + minX;
        int z = random.nextInt(maxZ - minZ) + minZ;
        double y = world.getHighestBlockYAt(x, z);

        this.questLocation = new Location(world, x, y + 2, z);
        this.questLocation.setY(
            this.getHighestBlock() + 2
        );
        this.buildBeacon();

        Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "New Quest! Press Tab for more information!");
    }

    public void updateQuestDisplay() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.addQuestDisplay(player);
        }
    }

    public void addQuestDisplay(Player player) {
        String questInfo = String.format(
            "Quest\nLocation: (%d, %d, %d)",
            (int) this.questLocation.getX(),
            (int) this.questLocation.getY(),
            (int) this.questLocation.getZ()
        );
        player.setPlayerListHeader(questInfo);
    }

    private void buildBeacon() {
        Location[] base = this.getBaseLocations();
        for (Location location : base) {
            location.getBlock().setType(Material.EMERALD_BLOCK);
        }
        this.questLocation.getBlock().setType(Material.BEACON);
    }

    public void endQuest() {
        Location[] base = this.getBaseLocations();
        for (Location location : base) {
            location.getBlock().setType(Material.AIR);
        }
        this.questLocation.getBlock().setType(Material.AIR);
    }

    private int getHighestBlock() {
        Location[] base = this.getBaseLocations();
        World world = this.questLocation.getWorld();
        int maxY = world.getHighestBlockYAt(
            (int) this.questLocation.getX(),
            (int) this.questLocation.getZ()
        );
        for (Location location : base) {
            int y = world.getHighestBlockYAt(
                (int) location.getX(),
                (int) location.getZ()
            );
            maxY = Math.max(maxY, y);
        }
        return maxY;
    }

    private Location[] getBaseLocations() {
        World world = this.questLocation.getWorld();
        double x = this.questLocation.getX();
        double y = this.questLocation.getY();
        double z = this.questLocation.getZ();

        return new Location[] {
            new Location(world, x, y - 1, z),
            new Location(world, x - 1, y - 1, z),
            new Location(world, x + 1, y - 1, z),
            new Location(world, x + 1, y - 1, z - 1),
            new Location(world, x - 1, y - 1, z - 1),
            new Location(world, x + 1, y - 1, z + 1),
            new Location(world, x - 1, y - 1, z + 1),
            new Location(world, x, y - 1, z + 1),
            new Location(world, x, y - 1, z - 1)
        };
    }

    public boolean isPartOfBeacon(Location location) {
        return location.distanceSquared(this.questLocation) <= 3;
    }

}
