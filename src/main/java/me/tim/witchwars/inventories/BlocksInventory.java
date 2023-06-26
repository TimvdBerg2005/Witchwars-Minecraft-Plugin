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

public class BlocksInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory blockInventory;
    private String inventoryName = "WitchWars - Blocks";

    public BlocksInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        blockInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillBlocksMenu();
    }

    public void fillBlocksMenu() {
        blockInventory.setItem(10, guiManager.createItems(Material.WHITE_WOOL, "Wool (4x)", 2, " Blood Tokens", 4));
        blockInventory.setItem(11, guiManager.createItems(Material.WHITE_WOOL, "Wool (16x)", 8, " Blood Tokens", 16));
        blockInventory.setItem(12, guiManager.createItems(Material.WHITE_WOOL, "Wool (64x)", 32, " Blood Tokens", 64));
        blockInventory.setItem(14, guiManager.createItems(Material.OAK_PLANKS, "Wood (16x)", 24, " Blood Tokens", 16));
        blockInventory.setItem(15, guiManager.createItems(Material.SANDSTONE, "Sandstone (16x)", 16, " Blood Tokens", 16));
        blockInventory.setItem(16, guiManager.createItems(Material.OBSIDIAN, "Obsidian", 1, " Broken Shards", 1));
    }


    public void openBlockInventory(Player player) {
        player.openInventory(blockInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (gameManager.getTeams().size() == 0) return;
        if (gameManager.getPlayerTeam(player) == null) return;
        Team team = gameManager.getPlayerTeam(player);
        Material wool = team.getWoolColor();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 2, wool, 4);
        }
        if (event.getRawSlot() == 11) {
            guiManager.checkForBloodTokens(player, 8, wool, 16);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBloodTokens(player, 32, wool, 64);
        }
        if (event.getRawSlot() == 14) {
            guiManager.checkForBloodTokens(player, 24, Material.OAK_PLANKS, 16);
        }
        if (event.getRawSlot() == 15) {
            guiManager.checkForBloodTokens(player, 16, Material.SANDSTONE, 16);
        }

        if (event.getRawSlot() == 16) {
            guiManager.checkForBrokenShards(player, 1, Material.OBSIDIAN, 1);
        }
    }

}
