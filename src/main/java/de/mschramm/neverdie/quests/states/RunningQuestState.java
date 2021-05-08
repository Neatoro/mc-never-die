package de.mschramm.neverdie.quests.states;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import de.mschramm.neverdie.NeverDiePlugin;
import de.mschramm.neverdie.Utils;
import de.mschramm.neverdie.quests.Quest;
import de.mschramm.neverdie.quests.beacon.BeaconConstruct;
import de.mschramm.neverdie.quests.events.QuestCompletionListener;

public class RunningQuestState extends QuestState {

    private Quest quest;
    private BeaconConstruct beacon;
    private QuestCompletionListener questCompletionListener;

    private BukkitTask timerWarningTask;

    public RunningQuestState(Quest quest) {
        super();
        this.quest = quest;
    }

    @Override
    public void onEnter() {
        ItemStack input = this.quest.getInput();
        String inputDescription = input.getAmount() + "x " + input.getType();

        Utils.broadcast(ChatColor.GOLD + "Ich habe eine neue Quest f\u00FCr euch: Bringt mir " + inputDescription + " und ich gebe euch " + this.quest.getRewardName() + "!");
        this.setTabContent(String.format(
            "Will: %s\nHabe: %s",
            inputDescription,
            this.quest.getRewardName()
        ));
        this.updateAllTabDisplays();

        Utils.playSound(Sound.BLOCK_BELL_USE, SoundCategory.MASTER, 0.6f, 1.2f);

        this.beacon = BeaconConstruct.generateBeacon();
        this.questCompletionListener = new QuestCompletionListener(this.quest, this.beacon);
        Bukkit.getServer().getPluginManager().registerEvents(this.questCompletionListener, NeverDiePlugin.getPlugin());

        Location beaconLocation = this.beacon.getBeaconLocation();
        NeverDiePlugin.getPlugin()
            .getLogger()
            .log(
                Level.INFO,
                String.format(
                    "New Merchant Spawned at (%f, %f, %f)",
                    beaconLocation.getX(),
                    beaconLocation.getY(),
                    beaconLocation.getZ()
                )
            );

        this.timerWarningTask = Bukkit.getScheduler().runTaskLater(
            NeverDiePlugin.getPlugin(),
            () -> this.updateQuestState(new WarningTimeState(this.beacon, this.questCompletionListener)),
            40 * 60 * 20
        );
    }

    @Override
    public void onLeave() {
        this.timerWarningTask.cancel();
    }

    @Override
    public void cleanUp() {
        this.beacon.destroyBeacon();
    }
}
