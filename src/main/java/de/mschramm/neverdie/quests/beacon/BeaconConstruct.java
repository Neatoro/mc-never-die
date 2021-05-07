package de.mschramm.neverdie.quests.beacon;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.HandlerList;

import de.mschramm.neverdie.NeverDiePlugin;

public class BeaconConstruct {

    private Location beacon;
    private Location[] base;

    private Villager merchant;
    private BeaconProtection protector;

    private BeaconConstruct(Location beacon) {
        this.beacon = beacon;
        this.base = this.getGeneratedBaseLocations();
        int highestBlock = this.getHighestBlock();
        this.beacon.setY(highestBlock + 2);
        this.base = this.getGeneratedBaseLocations();

        this.protector = new BeaconProtection(this);
        Bukkit.getServer().getPluginManager().registerEvents(this.protector, NeverDiePlugin.getPlugin());

        this.merchant = (Villager) this.beacon.getWorld().spawnEntity(
            this.beacon.clone().add(0, 0, 1),
            EntityType.VILLAGER
        );
        this.merchant.setCustomName("Herbert");
        this.merchant.setAI(false);
        this.merchant.setInvulnerable(true);
        this.merchant.setProfession(Profession.SHEPHERD);
        this.merchant.setRecipes(List.of());

        this.buildBeacon();
    }

    public static BeaconConstruct generateBeacon() {
        Location beaconLocation = BeaconConstruct.getRandomBeaconPosition();
        return new BeaconConstruct(beaconLocation);
    }

    private void buildBeacon() {
        for (Location location : this.base) {
            location.getBlock().setType(Material.EMERALD_BLOCK);
        }
        this.beacon.getBlock().setType(Material.BEACON);
    }

    public void destroyBeacon() {
        for (Location location : this.base) {
            location.getBlock().setType(Material.AIR);
        }
        this.beacon.getBlock().setType(Material.AIR);
        this.merchant.remove();
        HandlerList.unregisterAll(this.protector);
    }

    private static Location getRandomBeaconPosition() {
        Random random = new Random();

        World world = Bukkit.getWorld("game");
        WorldBorder border = world.getWorldBorder();
        Location center = border.getCenter();

        int minX = (int) (center.getX() - border.getSize() / 2) + 30;
        int maxX = (int) (center.getX() + border.getSize() / 2) - 30;
        int minZ = (int) (center.getZ() - border.getSize() / 2) + 30;
        int maxZ = (int) (center.getZ() + border.getSize() / 2) - 30;

        int x = random.nextInt(maxX - minX) + minX;
        int z = random.nextInt(maxZ - minZ) + minZ;
        double y = world.getHighestBlockYAt(x, z);

        Location beaconLocation = new Location(world, x, y + 2, z);
        return beaconLocation;
    }

    private Location[] getGeneratedBaseLocations() {
        World world = this.beacon.getWorld();
        double x = this.beacon.getX();
        double y = this.beacon.getY();
        double z = this.beacon.getZ();

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

    private int getHighestBlock() {
        World world = this.beacon.getWorld();
        int maxY = world.getHighestBlockYAt(
            (int) this.beacon.getX(),
            (int) this.beacon.getZ()
        );
        for (Location location : this.base) {
            int y = world.getHighestBlockYAt(
                (int) location.getX(),
                (int) location.getZ()
            );
            maxY = Math.max(maxY, y);
        }
        return maxY;
    }

    public boolean isPartOfBeacon(Location location) {
        return location.distanceSquared(this.beacon) <= 3;
    }

    public boolean isBeacon(Location location) {
        return location.equals(this.beacon);
    }

    public Villager getMerchant() {
        return this.merchant;
    }
}
