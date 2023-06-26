package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponsInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory weaponsInventory;
    private String inventoryName = "WitchWars - Weapons";

    public WeaponsInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        weaponsInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillWeaponsMenu();
    }

    public void fillWeaponsMenu() {
        weaponsInventory.setItem(10, guiManager.createItems(Material.WOODEN_SWORD, "Wooden Sword", 1, " Blood Tokens", 1));
        weaponsInventory.setItem(11, guiManager.createItems(Material.WOODEN_AXE, "Wooden Axe", 10, " Blood Tokens", 1));
        weaponsInventory.setItem(12, guiManager.createItems(Material.STONE_SWORD, "Stone Sword", 12, " Blood Tokens", 1));
        weaponsInventory.setItem(13, guiManager.createItems(Material.STONE_AXE, "Stone Axe", 40, " Blood Tokens", 1));
        weaponsInventory.setItem(14, guiManager.createItems(Material.IRON_SWORD, "Iron Sword", 2, " Shards", 1));
        weaponsInventory.setItem(15, guiManager.createItems(Material.IRON_AXE, "Iron Axe", 6, " Shards", 1));
        weaponsInventory.setItem(16, guiManager.createItems(Material.DIAMOND_SWORD, "Diamond Sword", 8, " Shards", 1));
        weaponsInventory.setItem(19, guiManager.createItems(Material.DIAMOND_AXE, "Diamond Axe", 15, " Shards", 1));
    }


    public void openWeaponsInventory(Player player) {
        player.openInventory(weaponsInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 1, Material.WOODEN_SWORD, 1);
        }
        if (event.getRawSlot() == 11) {
            guiManager.checkForBloodTokens(player, 10, Material.WOODEN_AXE, 1);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBloodTokens(player, 12, Material.STONE_SWORD, 1);
        }
        if (event.getRawSlot() == 13) {
            guiManager.checkForBloodTokens(player, 40, Material.STONE_AXE, 1);
        }
        if (event.getRawSlot() == 14) {
            guiManager.checkForBrokenShards(player, 2, Material.IRON_SWORD, 1);
        }
        if (event.getRawSlot() == 15) {
            guiManager.checkForBrokenShards(player, 6, Material.IRON_AXE, 1);
        }
        if (event.getRawSlot() == 16) {
            guiManager.checkForBrokenShards(player, 8, Material.DIAMOND_SWORD, 1);
        }
        if (event.getRawSlot() == 19) {
            guiManager.checkForBrokenShards(player, 15, Material.DIAMOND_AXE, 1);
        }
    }

}

