package de.mschramm.neverdie.quests.states;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.beacon.BeaconConstruct;
import de.mschramm.neverdie.quests.events.QuestCompletionListener;

public class WarningTimeState extends QuestState {

    private BeaconConstruct beacon;
    private QuestCompletionListener listener;

    private BukkitTask exitTask;

    public WarningTimeState(BeaconConstruct beacon, QuestCompletionListener listener) {
        this.beacon = beacon;
        this.listener = listener;
    }

    @Override
    public void onEnter() {
        Utils.broadcast(ChatColor.GOLD + "Gef\u00E4llt euch meine Quest nicht? Ich gebe euch noch 5 Minuten bevor ich einpacke!");
        this.exitTask = Bukkit.getScheduler().runTaskLater(
            NeverDiePlugin.getPlugin(),
            () -> {
                this.updateQuestState(new ExitState(this.beacon, this.listener));
            },
            5 * 60 * 20
        );
    }

    @Override
    public void onLeave() {
        this.exitTask.cancel();
    }

    @Override
    public void cleanUp() {
        this.beacon.destroyBeacon();
    }
}
