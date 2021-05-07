package de.mschramm.neverdie.quests;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import net.md_5.bungee.api.ChatColor;

public class QuestManager implements Listener {

    private static QuestManager instance;

    private Location questLocation;
    private LivingEntity bob;
    private Quest quest;

    private JavaPlugin plugin;
    private BukkitTask timeWarningTask;
    private BukkitTask endQuestTask;
    private BukkitTask playerWarningTask;

    private QuestManager() {
        this.plugin = NeverDiePlugin.getPlugin(NeverDiePlugin.class);
        Bukkit.getServer().getPluginManager().registerEvents(new BeaconProtection(), this.plugin);
        Bukkit.getServer().getPluginManager().registerEvents(this, this.plugin);
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
        this.bob = (LivingEntity) world.spawnEntity(
            this.questLocation.clone().add(0, 0, 1),
            EntityType.VILLAGER
        );
        this.bob.setAI(false);
        this.bob.setCustomName("Bob");
        this.bob.setInvulnerable(true);

        this.quest = Quest.generateQuest();
        this.updateQuestDisplay();

        ItemStack input = this.quest.getInput();
        String inputDescription = input.getAmount() + "x " + input.getType();

        this.timeWarningTask = Bukkit.getScheduler().runTaskLater(
            this.plugin,
            this::printTimeWarning,
            40 * 60 * 20
        );

        this.endQuestTask = Bukkit.getScheduler().runTaskLater(
            this.plugin,
            this::endQuest,
            45 * 60 * 20
        );

        Utils.broadcast(ChatColor.GOLD + "Ich habe eine neue Quest fuer euch: Bringt mir " + inputDescription + " und ich gebe euch " + this.quest.getRewardName() + "!");
    }

    public void updateQuestDisplay() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.addQuestDisplay(player);
        }
    }

    public void addQuestDisplay(Player player) {
        if (quest == null) {
            player.setPlayerListHeader(null);
            return;
        }

        ItemStack input = this.quest.getInput();
        String inputDescription = input.getAmount() + "x " + input.getType();

        String questInfo = String.format(
            "Will: %s\nHabe: %s",
            (int) this.questLocation.getX(),
            (int) this.questLocation.getY(),
            (int) this.questLocation.getZ(),
            inputDescription,
            this.quest.getRewardName()
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
        this.timeWarningTask.cancel();
        this.endQuestTask.cancel();
        this.playerWarningTask.cancel();

        Location[] base = this.getBaseLocations();
        for (Location location : base) {
            location.getBlock().setType(Material.AIR);
        }
        this.questLocation.getBlock().setType(Material.AIR);
        this.bob.remove();
        this.quest = null;
        this.updateQuestDisplay();

        Utils.broadcast(ChatColor.GOLD + "Zeit abgelaufen! Ich ziehe davon, aber wir werden uns wiedersehen!");

        Bukkit.getScheduler().runTaskLater(this.plugin, this::setupQuest, 5 * 60 * 20);
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

    public boolean isBeacon(Location location) {
        return location.equals(questLocation);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() == this.bob) {
            ItemStack stack = event.getPlayer().getInventory().getItem(event.getHand());
            ItemStack input = this.quest.getInput();
            if (input.getType() == stack.getType() && stack.getAmount() >= input.getAmount() && !this.quest.hasCompleted(event.getPlayer())) {
                stack.setAmount(stack.getAmount() - input.getAmount());
                event.getPlayer().getInventory().addItem(this.quest.getReward());
                this.quest.completed(event.getPlayer());

                Utils.broadcast(ChatColor.GOLD + "Jemand hat die Quest erfuellt. Ihr habt noch 5 Minuten, um sie ebenfalls abzugeben!");

                this.timeWarningTask.cancel();
                this.endQuestTask.cancel();

                this.playerWarningTask = Bukkit.getScheduler().runTaskLater(
                    this.plugin,
                    this::endQuest,
                    5 * 60 * 20
                );
            }
            event.setCancelled(true);
        }
    }

    public void printTimeWarning() {
        if (this.quest.getPlayerCount() == 0) {
            Utils.broadcast(ChatColor.GOLD + "Gefaellt euch meine Quest nicht? Ich gebe euch noch 5 Minuten bevor ich einpacke!");
        }
    }

}
