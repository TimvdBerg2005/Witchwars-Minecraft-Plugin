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

public class FoodInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory foodInventory;
    private String inventoryName = "WitchWars - Food";

    public FoodInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        foodInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillFoodMenu();
    }

    public void fillFoodMenu() {
        foodInventory.setItem(10, guiManager.createItems(Material.COOKED_CHICKEN, "Chicken (3x)", 3, " Blood Tokens", 3));
        foodInventory.setItem(11, guiManager.createItems(Material.COOKIE, "Cookie (12x)", 3, " Blood Tokens", 12));
        foodInventory.setItem(12, guiManager.createItems(Material.CAKE, "Cake (1x)", 6, " Blood Tokens", 1));
        foodInventory.setItem(13, guiManager.createItems(Material.APPLE, "Apple (3x)", 3, " Blood Tokens", 3));
        foodInventory.setItem(14, guiManager.createItems(Material.GOLDEN_APPLE, "Golden Apple (1x)", 20, " Blood Tokens", 1));
    }


    public void openFoodInventory(Player player) {
        player.openInventory(foodInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        System.out.println(event.getWhoClicked().getName());
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 3, Material.COOKED_CHICKEN, 3);
        }
        if (event.getRawSlot() == 11) {
            guiManager.checkForBloodTokens(player, 5, Material.COOKIE, 12);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBloodTokens(player, 6, Material.CAKE, 1);
        }
        if (event.getRawSlot() == 13) {
            guiManager.checkForBloodTokens(player, 3, Material.APPLE, 3);
        }
        if (event.getRawSlot() == 14) {
            guiManager.checkForBloodTokens(player, 20, Material.GOLDEN_APPLE, 1);
        }
    }

}

