package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShardsInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory shardsInventory;
    private String inventoryName = "WitchWars - Shards";

    public ShardsInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        shardsInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillShardsMenu();
    }

    public void fillShardsMenu() {
        shardsInventory.setItem(10, guiManager.createItems(Material.ELYTRA, "Elytra", 5, " Broken Shards", 1));

    }


    public void openShardsInventory(Player player) {
        player.openInventory(shardsInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBrokenShards(player, 5, Material.ELYTRA, 1);
        }
}
}


