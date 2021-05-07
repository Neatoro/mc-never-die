package de.mschramm.neverdie.quests.states;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.beacon.BeaconConstruct;
import de.mschramm.neverdie.quests.events.QuestCompletionListener;

public class FirstPlayerCompletedState extends QuestState {

    private BeaconConstruct beacon;
    private QuestCompletionListener questCompletionListener;

    private BukkitTask exitTask;

    public FirstPlayerCompletedState(BeaconConstruct beacon, QuestCompletionListener listener) {
        this.beacon = beacon;
        this.questCompletionListener = listener;
    }

    @Override
    public void onEnter() {
        Utils.broadcast(ChatColor.GOLD + "Jemand hat die Quest erf\u00FCllt. Ihr habt noch 5 Minuten, um sie ebenfalls abzugeben!");
        this.exitTask = Bukkit.getScheduler().runTaskLater(
            NeverDiePlugin.getPlugin(),
            () -> {
                this.updateQuestState(new ExitState(this.beacon, this.questCompletionListener));
            },
            1 * 60 * 20
        );
    }

    @Override
    public void onLeave() {
        this.exitTask.cancel();
    }

}
