package de.mschramm.neverdie.quests.states;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;

import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.beacon.BeaconConstruct;
import de.mschramm.neverdie.quests.events.QuestCompletionListener;

public class ExitState extends QuestState {

    private BeaconConstruct beacon;
    private QuestCompletionListener questCompletionListener;

    public ExitState(BeaconConstruct beacon, QuestCompletionListener listener) {
        this.beacon = beacon;
        this.questCompletionListener = listener;
    }

    @Override
    public void onEnter() {
        Utils.broadcast(ChatColor.GOLD + "Zeit abgelaufen! Ich ziehe davon, aber wir werden uns wiedersehen!");

        this.beacon.destroyBeacon();
        HandlerList.unregisterAll(this.questCompletionListener);
        this.updateQuestState(new NoQuestState());

        this.updateAllTabDisplays();
    }

    @Override
    public void onLeave() {
    }

    @Override
    public void cleanUp() {
        this.beacon.destroyBeacon();
    }

}
