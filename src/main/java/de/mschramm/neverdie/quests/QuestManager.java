package de.mschramm.neverdie.quests;

import org.bukkit.Bukkit;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.quests.events.PlayerJoinListener;
import de.mschramm.neverdie.quests.states.InitialState;
import de.mschramm.neverdie.quests.states.QuestState;

public class QuestManager {

    private static QuestManager instance;
    private QuestState state;

    private QuestManager() {
        this.state = new InitialState();
        this.state.onEnter();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), NeverDiePlugin.getPlugin());
    }

    public static QuestManager getInstance() {
        if (QuestManager.instance == null) {
            QuestManager.instance = new QuestManager();
        }
        return QuestManager.instance;
    }

    public QuestState getState() {
        return this.state;
    }

    public void changeState(QuestState newState) {
        this.state = newState;
    }
}
