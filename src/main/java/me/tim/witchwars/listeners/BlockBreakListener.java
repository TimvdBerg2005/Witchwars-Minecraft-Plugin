package me.tim.witchwars.listeners;

import fr.mrmicky.fastboard.FastBoard;
import me.tim.witchwars.Witchwars;
import me.tim.witchwars.manager.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private GameManager gameManager;
    private Witchwars plugin;

    public BlockBreakListener(GameManager gameManager, Witchwars plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        if (!gameManager.getBlockManager().canBreak(event.getBlock())) {
            event.setCancelled(true);
        }
    }
}


