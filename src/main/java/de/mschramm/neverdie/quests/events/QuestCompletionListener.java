package de.mschramm.neverdie.quests.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.mschramm.neverdie.quests.Quest;
import de.mschramm.neverdie.quests.QuestManager;
import de.mschramm.neverdie.quests.beacon.BeaconConstruct;
import de.mschramm.neverdie.quests.states.FirstPlayerCompletedState;

public class QuestCompletionListener implements Listener {

    private Quest quest;
    private BeaconConstruct beacon;

    public QuestCompletionListener(Quest quest, BeaconConstruct beacon) {
        this.quest = quest;
        this.beacon = beacon;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (event.getRightClicked() == this.beacon.getMerchant()) {
            ItemStack stack = event.getPlayer().getInventory().getItem(event.getHand());
            ItemStack input = this.quest.getInput();

            if (input.getType() == stack.getType() && stack.getAmount() >= input.getAmount() && !this.quest.hasCompleted(player)) {
                stack.setAmount(stack.getAmount() - input.getAmount());
                for (ItemStack reward : this.quest.getReward()) {
                    player.getWorld().dropItem(player.getLocation(), reward);
                }
                this.quest.completed(player);

                if (this.quest.getPlayerCount() == 1) {
                    QuestManager
                        .getInstance()
                        .getState()
                        .updateQuestState(new FirstPlayerCompletedState(this.beacon, this));
                }
            }
            event.setCancelled(true);
        }
    }

}
