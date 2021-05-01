package de.mschramm.neverdie.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.mschramm.neverdie.repositories.LifeRepository;

public class Damage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                this.mayAvoidDamage((Player) event.getDamager(), event);
            }

            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();

                if (arrow.getShooter() instanceof Player) {
                    this.mayAvoidDamage((Player) arrow.getShooter(), event);
                }
            }

            if (event.getDamager() instanceof Trident) {
                Trident trident = (Trident) event.getDamager();
                if (trident.getShooter() instanceof Player) {
                    this.mayAvoidDamage((Player) trident.getShooter(), event);
                }
            }

            if (event.getDamager() instanceof ThrownPotion) {
                ThrownPotion potion = (ThrownPotion) event.getDamager();
                if (potion.getShooter() instanceof Player) {
                    this.mayAvoidDamage((Player) potion.getShooter(), event);
                }
            }
        }
    }

    private void mayAvoidDamage(Player damager, EntityDamageByEntityEvent event) {
        int lifesOfDamager = LifeRepository.getInstance().getLifesForPlayer(damager);
        if (lifesOfDamager > 1) {
            event.setDamage(0);
        }
    }

}
