package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;

public class BlockPlaceListener implements Listener {

    private GameManager gameManager;

    public BlockPlaceListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getLocation().getY() >= 150) {
            event.setCancelled(true);
            return;
        }
        Block block = event.getBlock();
        gameManager.getPlacedBlocks().add(block);
    }
}
