package de.mschramm.neverdie.quests.states;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.quests.Quest;

public class NoQuestState extends QuestState {

    private BukkitTask createQuestTask;

    @Override
    public void onEnter() {
        this.createQuestTask = Bukkit.getScheduler().runTaskLater(
            NeverDiePlugin.getPlugin(),
            () -> {
                Quest quest = Quest.generateQuest();
                this.updateQuestState(new RunningQuestState(quest));
            },
            10 * 20
        );
    }

    @Override
    public void onLeave() {
        this.createQuestTask.cancel();
    }

}
