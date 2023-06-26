package me.tim.witchwars.listeners;

import me.tim.witchwars.inventories.MainShopInventory;
import me.tim.witchwars.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;


public class PlayerOpenShopListener implements Listener {

    private GameManager gameManager;

    public PlayerOpenShopListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity instanceof PiglinBrute && entity.getCustomName() != null && entity.getCustomName().equals(
                ChatColor.AQUA + "" + ChatColor.BOLD + "WitchWars Shop")) {
            event.setCancelled(true);

            Player player = event.getPlayer();
            MainShopInventory inventory = new MainShopInventory(gameManager);
            inventory.openShopInventory(player);

        }
    }
}
