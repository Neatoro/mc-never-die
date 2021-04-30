package de.mschramm.neverdie.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class LifeRepository {

    private static LifeRepository instance;

    private Map<UUID, Integer> lifes = new HashMap<>();

    private LifeRepository() {
    }

    public static LifeRepository getInstance() {
        if (LifeRepository.instance == null) {
            LifeRepository.instance = new LifeRepository();
        }
        return LifeRepository.instance;
    }

    public int getLifesForPlayer(Player player) {
        return this.lifes.get(player.getUniqueId());
    }

    public boolean isPlayerRegistered(Player player) {
        return this.lifes.containsKey(player.getUniqueId());
    }

    public void addNewPlayer(Player player) {
        this.lifes.put(player.getUniqueId(), 3);
    }

    public void reduceLifeFromPlayer(Player player) {
        int currentLifes = this.lifes.get(player.getUniqueId());
        this.lifes.put(player.getUniqueId(), currentLifes - 1);
    }

}
