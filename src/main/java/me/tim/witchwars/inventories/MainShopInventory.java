package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
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

public class MainShopInventory implements Listener {

    private GameManager gameManager;
    private Inventory shopInventory;
    private String inventoryName = "WitchWars - Shop";

    public MainShopInventory(GameManager gameManager) {
        this.gameManager = gameManager;
        shopInventory = Bukkit.createInventory(null, 4 * 9, inventoryName);
        fillShopMenu();
    }

    public Inventory getShopInventory() {
        return shopInventory;
    }

    public void fillShopMenu() {
        shopInventory.setItem(10, createItems(Material.BOW, "Bows"));
        shopInventory.setItem(12, createItems(Material.STONE_PICKAXE, "Pickaxes"));
        shopInventory.setItem(14, createItems(Material.LEATHER_CHESTPLATE, "Armor"));
        shopInventory.setItem(16, createItems(Material.IRON_SWORD, "Weapons"));
        shopInventory.setItem(19, createItems(Material.CAKE, "Food"));
        shopInventory.setItem(21, createItems(Material.CHEST, "Utility"));
        shopInventory.setItem(23, createItems(Material.PRISMARINE_SHARD, "Shards"));
        shopInventory.setItem(25, createItems(Material.WHITE_WOOL, "Blocks"));
    }

    public ItemStack createItems(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void openShopInventory(Player player) {
        player.openInventory(shopInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle() != inventoryName) return;
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        event.setCancelled(true);
        if (event.getRawSlot() == 10) {
            player.closeInventory();
            BowInventory bowInventory = new BowInventory(gameManager, gameManager.getGuiManager());
            bowInventory.openBowInventory(player);
        }
        if (event.getRawSlot() == 12) {
            player.closeInventory();
            ToolsInventory toolsInventory = new ToolsInventory(gameManager, gameManager.getGuiManager());
            toolsInventory.opentoolsInventory(player);
        }
        if (event.getRawSlot() == 14) {
            player.closeInventory();
            ArmorInventory armorInventory = new ArmorInventory(gameManager, gameManager.getGuiManager());
            armorInventory.openArmorInventory(player);
        }
        if (event.getRawSlot() == 16) {
            player.closeInventory();
            WeaponsInventory weaponsInventory = new WeaponsInventory(gameManager, gameManager.getGuiManager());
            weaponsInventory.openWeaponsInventory(player);
        }
        if (event.getRawSlot() == 19) {
            player.closeInventory();
            FoodInventory foodInventory = new FoodInventory(gameManager, gameManager.getGuiManager());
            foodInventory.openFoodInventory(player);
        }
        if (event.getRawSlot() == 21) {
            player.closeInventory();
            UtilityInventory utilityInventory = new UtilityInventory(gameManager, gameManager.getGuiManager());
            utilityInventory.openUtilityInventory(player);
        }
        if (event.getRawSlot() == 23) {
            player.closeInventory();
            ShardsInventory shardsInventory = new ShardsInventory(gameManager, gameManager.getGuiManager());
            shardsInventory.openShardsInventory(player);
        }
        if (event.getRawSlot() == 25) {
            player.closeInventory();
            BlocksInventory blocksInventory = new BlocksInventory(gameManager, gameManager.getGuiManager());
            blocksInventory.openBlockInventory(player);
        }
    }
}
