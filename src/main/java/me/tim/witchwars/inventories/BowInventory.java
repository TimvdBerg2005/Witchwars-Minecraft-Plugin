package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BowInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory bowInventory;
    private String inventoryName = "WitchWars - Bow";

    public BowInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        bowInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillBowMenu();
    }

    public void fillBowMenu() {
        bowInventory.setItem(10, guiManager.createItems(Material.BOW, "Bow", 12, " Broken Shards", 1));
        bowInventory.setItem(11, guiManager.createEnchantedBow(Material.BOW, "Bow", 2, 18, " Broken Shards", Enchantment.ARROW_DAMAGE));
        bowInventory.setItem(12, guiManager.createEnchantedBow(Material.BOW, "Bow", 1, 18, " Broken Shards", Enchantment.ARROW_KNOCKBACK));
        bowInventory.setItem(13, guiManager.createItems(Material.ARROW, "Arrow (2x)", 2, " Blood Tokens", 2));
        bowInventory.setItem(14, guiManager.createItems(Material.SPECTRAL_ARROW, "Spectral Arrow (2x)", 4, " Blood Tokens", 2));
    }


    public void openBowInventory(Player player) {
        player.openInventory(bowInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        if (event.getRawSlot() == 10) {
            guiManager.checkForBrokenShards(player, 12, Material.BOW, 1);
            event.setCancelled(true);
        }
        if (event.getRawSlot() == 11) {
            ItemStack bow = new ItemStack(Material.BOW);
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
            guiManager.checkForBrokenShardsWithItemStack(player, 18, bow);
            event.setCancelled(true);
        }
        if (event.getRawSlot() == 12) {
            ItemStack bow = new ItemStack(Material.BOW);
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
            guiManager.checkForBrokenShardsWithItemStack(player, 18, bow);
            event.setCancelled(true);
        }
        if (event.getRawSlot() == 13) {
            guiManager.checkForBloodTokens(player, 2, Material.ARROW, 2);
            event.setCancelled(true);
        }
        if (event.getRawSlot() == 14) {
            guiManager.checkForBloodTokens(player, 4, Material.SPECTRAL_ARROW, 2);
            event.setCancelled(true);
        }
    }

}

