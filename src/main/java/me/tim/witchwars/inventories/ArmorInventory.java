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

public class ArmorInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory armorInventory;
    private String inventoryName = "WitchWars - Armor";

    public ArmorInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        armorInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillArmorMenu();
    }

    public void fillArmorMenu() {
        armorInventory.setItem(10, guiManager.createItems(Material.SHIELD, "Shield", 32, " Blood Tokens", 1));
        armorInventory.setItem(11, guiManager.createItems(Material.LEATHER_HELMET, "Leather Helmet", 1, " Blood Tokens", 1));
        armorInventory.setItem(12, guiManager.createItems(Material.LEATHER_CHESTPLATE, "Leather Chestplate", 1, " Blood Tokens", 1));
        armorInventory.setItem(13, guiManager.createItems(Material.LEATHER_LEGGINGS, "Leather Leggings", 1, " Blood Tokens", 1));
        armorInventory.setItem(14, guiManager.createItems(Material.LEATHER_BOOTS, "Leather Boots", 1, " Blood Tokens", 1));
        armorInventory.setItem(15, guiManager.createItems(Material.CHAINMAIL_HELMET, "Chain Helmet", 12, " Blood Tokens", 1));
        armorInventory.setItem(16, guiManager.createItems(Material.CHAINMAIL_CHESTPLATE, "Chain Chestplatae", 12, " Blood Tokens", 1));
        armorInventory.setItem(19, guiManager.createItems(Material.CHAINMAIL_LEGGINGS, "Chain Leggings", 12, " Blood Tokens", 1));
        armorInventory.setItem(20, guiManager.createItems(Material.CHAINMAIL_BOOTS, "Chain Boots", 12, " Blood Tokens", 1));
        armorInventory.setItem(21, guiManager.createItems(Material.IRON_HELMET, "Iron Helmet", 5, " Shards", 1));
        armorInventory.setItem(22, guiManager.createItems(Material.IRON_CHESTPLATE, "Iron Chestplate", 5, " Shards", 1));
        armorInventory.setItem(23, guiManager.createItems(Material.IRON_LEGGINGS, "Iron Leggings", 5, " Shards", 1));
        armorInventory.setItem(24, guiManager.createItems(Material.IRON_BOOTS, "Iron Boots", 5, " Shards", 1));
    }


    public void openArmorInventory(Player player) {
        player.openInventory(armorInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (gameManager.getTeams().size() == 0) return;
        if (gameManager.getPlayerTeam(player) == null) return;
        Team team = gameManager.getPlayerTeam(player);
        ItemStack leatherHelmet = team.getLeatherArmorPiece(Material.LEATHER_HELMET);
        ItemStack leatherChestplate = team.getLeatherArmorPiece(Material.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = team.getLeatherArmorPiece(Material.LEATHER_LEGGINGS);
        ItemStack leatherBoots = team.getLeatherArmorPiece(Material.LEATHER_BOOTS);
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 32, Material.SHIELD, 1);
        }
        if (event.getRawSlot() == 11) {
            guiManager.checkForBloodTokensWithItemStack(player, 1, leatherHelmet);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBloodTokensWithItemStack(player, 1, leatherChestplate);
        }
        if (event.getRawSlot() == 13) {
            guiManager.checkForBloodTokensWithItemStack(player, 1, leatherLeggings);
        }
        if (event.getRawSlot() == 14) {
            guiManager.checkForBloodTokensWithItemStack(player, 1, leatherBoots);
        }
        if (event.getRawSlot() == 15) {
            guiManager.checkForBloodTokens(player, 12, Material.CHAINMAIL_HELMET, 1);
        }
        if (event.getRawSlot() == 16) {
            guiManager.checkForBloodTokens(player, 12, Material.CHAINMAIL_CHESTPLATE, 1);
        }
        if (event.getRawSlot() == 19) {
            guiManager.checkForBloodTokens(player, 12, Material.CHAINMAIL_LEGGINGS, 1);
        }
        if (event.getRawSlot() == 20) {
            guiManager.checkForBloodTokens(player, 12, Material.CHAINMAIL_BOOTS, 1);
        }
        if (event.getRawSlot() == 21) {
            guiManager.checkForBrokenShards(player, 5, Material.IRON_HELMET, 1);
        }
        if (event.getRawSlot() == 22) {
            guiManager.checkForBrokenShards(player, 5, Material.IRON_CHESTPLATE, 1);
        }
        if (event.getRawSlot() == 23) {
            guiManager.checkForBrokenShards(player, 5, Material.IRON_LEGGINGS, 1);
        }
        if (event.getRawSlot() == 24) {
            guiManager.checkForBrokenShards(player, 5, Material.IRON_BOOTS, 1);
        }
    }

}


