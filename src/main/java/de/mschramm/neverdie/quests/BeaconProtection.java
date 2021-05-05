package de.mschramm.neverdie.quests;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BeaconProtection implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (QuestManager.getInstance().isPartOfBeacon(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplodes(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (QuestManager.getInstance().isPartOfBeacon(block.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockCanBuildEvent event) {
        if (QuestManager.getInstance().isPartOfBeacon(event.getBlock().getLocation())) {
            event.setBuildable(false);
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (QuestManager.getInstance().isPartOfBeacon(block.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            if (QuestManager.getInstance().isPartOfBeacon(block.getLocation())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteractWithBeacon(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && QuestManager.getInstance().isBeacon(event.getClickedBlock().getLocation())) {
            event.setCancelled(true);
        }
    }
}
