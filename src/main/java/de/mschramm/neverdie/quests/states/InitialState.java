package de.mschramm.neverdie.quests.states;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.Quest;

public class InitialState extends QuestState {

    private BukkitTask changeToEmptyQuest;

    @Override
    public void onEnter() {
        this.changeToEmptyQuest = Bukkit.getScheduler().runTaskLater(
            NeverDiePlugin.getPlugin(NeverDiePlugin.class),
            () -> {
                Utils.broadcast(ChatColor.GOLD + "Seid gegr\u00FC\u00DFt! Ich hei\u00DFe Herbert, kommt zu mir und ich biete euch exotische Waren im Tauschhandel an! Haltet nach dem Lichtstrahl Ausschau!");
                Quest quest = Quest.generateQuest();
                this.updateQuestState(new RunningQuestState(quest));
            },
            25 * 60 * 20
        );
    }

    @Override
    public void onLeave() {
        this.changeToEmptyQuest.cancel();
    }

}
