package de.mschramm.neverdie.repositories;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AttackRepository {

    private static final int TIMER = 10 * 60 * 1000;
    private static AttackRepository instance;

    private Map<UUID, Map<UUID, Long>> attacks;

    private AttackRepository() {
        this.attacks = new HashMap<>();
    }

    public static AttackRepository getInstance() {
        if (AttackRepository.instance == null) {
            AttackRepository.instance = new AttackRepository();
        }
        return AttackRepository.instance;
    }

    public void addPlayer(Player player) {
        this.attacks.put(player.getUniqueId(), new HashMap<>());
    }

    public void removePlayer(Player player) {
        this.attacks.remove(player.getUniqueId());
    }

    public void canAttack(Player attacker, Player attacked) {
        Map<UUID, Long> uuids = this.attacks.get(attacker.getUniqueId());
        uuids.put(attacked.getUniqueId(), System.currentTimeMillis() + TIMER);
    }

    public boolean isAttackable(Player attacker, Player attacked) {
        Map<UUID, Long> uuids = this.attacks.get(attacker.getUniqueId());
        return uuids.containsKey(attacked.getUniqueId()) && uuids.get(attacked.getUniqueId()) > System.currentTimeMillis();
    }

}
