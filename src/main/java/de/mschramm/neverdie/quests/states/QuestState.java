package de.mschramm.neverdie.quests.states;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.mschramm.neverdie.quests.QuestManager;

public abstract class QuestState {

    private String tabContent;

    public abstract void onEnter();

    public abstract void onLeave();

    public void updateQuestState(QuestState newState) {
        QuestState currentState = QuestManager.getInstance().getState();
        QuestManager.getInstance().changeState(newState);
        currentState.onLeave();
        newState.tabContent = currentState.tabContent;
        newState.onEnter();
    }

    protected void setTabContent(String tabContent) {
        this.tabContent = tabContent;
    }

    public void updateAllTabDisplays() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.updateTabDisplay(player);
        }
    }

    public void updateTabDisplay(Player player) {
        player.setPlayerListHeader(this.tabContent);
    }
}
