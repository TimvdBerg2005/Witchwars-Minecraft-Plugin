package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ToolsInventory implements Listener {

    private GameManager gameManager;
    private GuiManager guiManager;
    private Inventory toolsInventory;
    private String inventoryName = "WitchWars - Tools";

    public ToolsInventory(GameManager gameManager, GuiManager guiManager) {
        this.gameManager = gameManager;
        this.guiManager = guiManager;
        toolsInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillToolsMenu();
    }

    public void fillToolsMenu() {
        toolsInventory.setItem(10, guiManager.createItems(Material.IRON_PICKAXE, "Iron Pickaxe", 10, " Blood Tokens", 1));
        toolsInventory.setItem(11, guiManager.createEfficiencyPickaxe(Material.IRON_PICKAXE, "Iron Pickaxe", 1, 20, " Blood Tokens"));
        toolsInventory.setItem(12, guiManager.createItems(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", 38, " Blood Tokens", 1));
        toolsInventory.setItem(13, guiManager.createEfficiencyPickaxe(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", 1, 5, " Broken Shards"));
        toolsInventory.setItem(14, guiManager.createEfficiencyPickaxe(Material.DIAMOND_PICKAXE, "Diamond Pickaxe", 3, 10, " Broken Shards"));
    }



    public void opentoolsInventory(Player player) {
        player.openInventory(toolsInventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            guiManager.checkForBloodTokens(player, 10, Material.IRON_PICKAXE, 1);
        }
        if (event.getRawSlot() == 11) {
            ItemStack ironPickaxeEff1 = new ItemStack(Material.IRON_PICKAXE);
            ironPickaxeEff1.addEnchantment(Enchantment.DIG_SPEED, 1);
            guiManager.checkForBloodTokensWithItemStack(player, 20, ironPickaxeEff1);
        }
        if (event.getRawSlot() == 12) {
            guiManager.checkForBloodTokens(player, 38, Material.DIAMOND_PICKAXE, 1);
        }
        if (event.getRawSlot() == 13) {
            ItemStack diamondPickaxeEff1 = new ItemStack(Material.DIAMOND_PICKAXE);
            diamondPickaxeEff1.addEnchantment(Enchantment.DIG_SPEED, 1);

            guiManager.checkForBrokenShardsWithItemStack(player, 5, diamondPickaxeEff1);
        }
        if (event.getRawSlot() == 14) {
            ItemStack diamondPickaxeEff3 = new ItemStack(Material.DIAMOND_PICKAXE);
            diamondPickaxeEff3.addEnchantment(Enchantment.DIG_SPEED, 3);
            guiManager.checkForBrokenShardsWithItemStack(player, 10, diamondPickaxeEff3);
        }
    }
}


