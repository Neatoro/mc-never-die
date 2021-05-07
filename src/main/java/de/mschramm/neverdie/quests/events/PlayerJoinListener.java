package de.mschramm.neverdie.quests.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.mschramm.neverdie.quests.QuestManager;

public class PlayerJoinListener implements Listener {

    public void onPlayerJoin(PlayerJoinEvent event) {
        QuestManager.getInstance()
            .getState()
            .updateTabDisplay(event.getPlayer());
    }

}
