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

public class UtilityInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory utilityInventory;
    private String inventoryName = "WitchWars - Utility";

    public UtilityInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        utilityInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillUtilityMenu();
    }

    public void fillUtilityMenu() {
        utilityInventory.setItem(10, guiManager.createItems(Material.COBWEB, "Cobweb (2x)", 5, " Blood Tokens", 3));
        utilityInventory.setItem(11, guiManager.createItems(Material.CHEST, "Chest (1x)", 10, " Blood Tokens", 1));
        utilityInventory.setItem(12, guiManager.createItems(Material.ENDER_PEARL, "Ender Pearl (1x)", 4, " Broken Shards", 1));
        utilityInventory.setItem(13, guiManager.createItems(Material.ENCHANTED_GOLDEN_APPLE, "God Apple (1x)", 8, " Broken Shards", 1));
    }


    public void openUtilityInventory(Player player) {
        player.openInventory(utilityInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 5, Material.COBWEB, 2);
        }
        if (event.getRawSlot() == 11) {
            guiManager.checkForBloodTokens(player, 10, Material.CHEST, 1);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBrokenShards(player, 4, Material.ENDER_PEARL, 1);
        }
        if (event.getRawSlot() == 13) {
            guiManager.checkForBrokenShards(player, 8, Material.ENCHANTED_GOLDEN_APPLE, 1);
        }
    }

}

