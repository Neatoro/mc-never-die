package de.mschramm.neverdie.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.mschramm.neverdie.repositories.AttackRepository;
import de.mschramm.neverdie.repositories.LifeRepository;

public class Damage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getDamager() instanceof Player) {
                this.processDamage((Player) event.getDamager(), player, event);
            }

            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();

                if (arrow.getShooter() instanceof Player) {
                    this.processDamage((Player) arrow.getShooter(), player, event);
                }
            }

            if (event.getDamager() instanceof Trident) {
                Trident trident = (Trident) event.getDamager();
                if (trident.getShooter() instanceof Player) {
                    this.processDamage((Player) trident.getShooter(), player, event);
                }
            }

            if (event.getDamager() instanceof ThrownPotion) {
                ThrownPotion potion = (ThrownPotion) event.getDamager();
                if (potion.getShooter() instanceof Player) {
                    this.processDamage((Player) potion.getShooter(), player, event);
                }
            }
        }
    }

    private void processDamage(Player damager, Player player, EntityDamageByEntityEvent event) {
        int lifesOfDamager = LifeRepository.getInstance().getLifesForPlayer(damager);
        int lifesOfPlayer = LifeRepository.getInstance().getLifesForPlayer(player);

        AttackRepository repository = AttackRepository.getInstance();

        if (lifesOfDamager > 1 && !repository.isAttackable(damager, player)) {
            event.setDamage(0);
        } else if (lifesOfPlayer > 1) {
            AttackRepository.getInstance().canAttack(player, damager);
        }
    }

}
