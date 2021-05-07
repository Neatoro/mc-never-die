package de.mschramm.neverdie.quests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Quest {

    private static class QuestReward {

        public ItemStack[] reward;
        public Value value;
        public String name;

        public QuestReward(ItemStack[] reward, Value value) {
            this(reward, value, reward[0].getType().toString());
        }

        public QuestReward(ItemStack[] reward, Value value, String name) {
            this.reward = reward;
            this.value = value;
            this.name = name;
        }

    };

    private static Map<Value, ItemStack[]> possibleInputs = Map.of(
        Value.COMMON, new ItemStack[] {
            new ItemStack(Material.LAPIS_LAZULI, 16),
            new ItemStack(Material.GOLD_INGOT, 8),
            new ItemStack(Material.REDSTONE, 32),
            new ItemStack(Material.HAY_BLOCK, 8),
            new ItemStack(Material.BOOK, 8),
            new ItemStack(Material.APPLE, 16),
            new ItemStack(Material.COOKED_SALMON, 16)
        },
        Value.UNCOMMON, new ItemStack[] {
            new ItemStack(Material.LAPIS_LAZULI, 32),
            new ItemStack(Material.GOLD_INGOT, 16),
            new ItemStack(Material.DIAMOND, 1),
            new ItemStack(Material.CAKE, 1),
            new ItemStack(Material.HAY_BLOCK, 16),
            new ItemStack(Material.BOOK, 16),
            new ItemStack(Material.BOOKSHELF, 4),
            new ItemStack(Material.SLIME_BALL, 8)
        },
        Value.RARE, new ItemStack[] {
            new ItemStack(Material.LAPIS_LAZULI, 64),
            new ItemStack(Material.GOLD_INGOT, 32),
            new ItemStack(Material.DIAMOND, 4),
            new ItemStack(Material.HAY_BLOCK, 64),
            new ItemStack(Material.BOOKSHELF, 16),
            new ItemStack(Material.EMERALD_BLOCK, 4),
            new ItemStack(Material.ENDER_PEARL, 4),
            new ItemStack(Material.SLIME_BALL, 32)
        },
        Value.MYTHIC, new ItemStack[] {
            new ItemStack(Material.DIAMOND, 16),
            new ItemStack(Material.EMERALD_BLOCK, 16),
            new ItemStack(Material.ENDER_PEARL, 16)
        }
    );

    private static Map<Rarity, QuestReward[]> rewards = Map.of(
        Rarity.COMMON, new QuestReward[] {
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.SOUL_SAND, 4) },
                Value.COMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.NETHERRACK, 64) },
                Value.COMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.JUNGLE_SAPLING, 2) },
                Value.COMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.ACACIA_SAPLING, 2) },
                Value.COMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.BLAZE_ROD, 1) },
                Value.UNCOMMON
            ),
            new QuestReward(
                new ItemStack[] {
                    new ItemStack(Material.BREWING_STAND, 1),
                    new ItemStack(Material.BLAZE_POWDER, 1),
                    new ItemStack(Material.NETHER_WART, 8)
                },
                Value.RARE,
                "BREWING_KIT"
            )
        },
        Rarity.UNCOMMON, new QuestReward[] {
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.BAMBOO, 1) },
                Value.COMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.WARPED_NYLIUM, 8) },
                Value.UNCOMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.CRIMSON_NYLIUM, 8) },
                Value.UNCOMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.GLOWSTONE, 16) },
                Value.UNCOMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.GHAST_TEAR, 1) },
                Value.UNCOMMON
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.QUARTZ, 32) },
                Value.RARE
            )
        },
        Rarity.RARE, new QuestReward[] {
            new QuestReward(
                new ItemStack[] {
                    new ItemStack(Material.END_STONE, 16),
                    new ItemStack(Material.CHORUS_FLOWER, 4),
                    new ItemStack(Material.OBSIDIAN, 8)
                },
                Value.RARE,
                "END_KIT"
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.TRIDENT, 1) },
                Value.MYTHIC
            )
        },
        Rarity.MYTHIC, new QuestReward[] {
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.SHULKER_SPAWN_EGG, 2) },
                Value.MYTHIC
            ),
            new QuestReward(
                new ItemStack[] { new ItemStack(Material.NETHER_STAR, 1) },
                Value.MYTHIC
            )
        }
    );

    private ItemStack input;
    private QuestReward reward;

    private List<UUID> players;

    private Quest(ItemStack input, QuestReward reward) {
        this.players = new ArrayList<>();
        this.reward = reward;
        this.input = input;
    }

    public static Quest generateQuest() {
        Random random = new Random();
        int maxWeights = 90 + 60 + 35 + 15;
        int rarityIndex = random.nextInt(maxWeights + 1);
        Rarity rarity = Rarity.MYTHIC;
        if (rarityIndex < 90) {
            rarity = Rarity.COMMON;
        } else if (rarityIndex < 150) {
            rarity = Rarity.UNCOMMON;
        } else if (rarityIndex < 185) {
            rarity = Rarity.RARE;
        }

        QuestReward[] possibleRewards = Quest.rewards.get(rarity);
        int rewardIndex = random.nextInt(possibleRewards.length);

        QuestReward reward = possibleRewards[rewardIndex];
        ItemStack[] possibleInputs = Quest.possibleInputs.get(reward.value);
        int inputIndex = random.nextInt(possibleInputs.length);

        return new Quest(possibleInputs[inputIndex], reward);
    }

    public boolean hasCompleted(Player player) {
        return this.players.contains(player.getUniqueId());
    }

    public void completed(Player player) {
        this.players.add(player.getUniqueId());
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack[] getReward() {
        return this.reward.reward;
    }

    public String getRewardName() {
        return this.reward.name;
    }

}
