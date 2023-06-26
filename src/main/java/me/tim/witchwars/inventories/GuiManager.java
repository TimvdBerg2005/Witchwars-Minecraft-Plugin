package me.tim.witchwars.inventories;

import me.tim.witchwars.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {


    public GuiManager() {
    }

    public void checkForBloodTokens(Player player, int tokenAmount, Material material, int materialAmount) {
        ItemStack bloodToken = new ItemStack(Material.NETHER_WART, tokenAmount);
        ItemMeta bloodTokenMeta = bloodToken.getItemMeta();
        bloodTokenMeta.setDisplayName(ChatColor.RED + "Blood Token");
        bloodToken.setItemMeta(bloodTokenMeta);
        if (player.getInventory().containsAtLeast(bloodToken, tokenAmount)) {
            player.getInventory().removeItem(bloodToken);
            player.getInventory().addItem(new ItemStack(material, materialAmount));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough Blood Tokens!");
        }
    }

    public void checkForBloodTokensWithItemStack(Player player, int tokenAmount, ItemStack itemStack) {
        ItemStack bloodToken = new ItemStack(Material.NETHER_WART, tokenAmount);
        ItemMeta bloodTokenMeta = bloodToken.getItemMeta();
        bloodTokenMeta.setDisplayName(ChatColor.RED + "Blood Token");
        bloodToken.setItemMeta(bloodTokenMeta);

        if (player.getInventory().containsAtLeast(bloodToken, tokenAmount)) {
            player.getInventory().removeItem(bloodToken);
            player.getInventory().addItem(itemStack);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough Blood Tokens!");
        }
    }

    public void checkForBrokenShardsWithItemStack(Player player, int tokenAmount, ItemStack itemStack) {
        ItemStack brokenShard = new ItemStack(Material.PRISMARINE_SHARD, tokenAmount);
        ItemMeta brokenShardMeta = brokenShard.getItemMeta();
        brokenShardMeta.setDisplayName(ChatColor.DARK_AQUA + "Broken Shard");
        brokenShard.setItemMeta(brokenShardMeta);
        if (brokenShardMeta.getDisplayName().equals(ChatColor.DARK_AQUA + "Broken Shard")) {
            if (player.getInventory().containsAtLeast(brokenShard, tokenAmount)) {
                player.getInventory().removeItem(brokenShard);
                player.getInventory().addItem(itemStack);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have enough Broken Shards!");
            }
        }
    }

    public void checkForBrokenShards(Player player, int tokenAmount, Material material, int materialAmount) {
        ItemStack brokenShard = new ItemStack(Material.PRISMARINE_SHARD, tokenAmount);
        ItemMeta brokenShardMeta = brokenShard.getItemMeta();
        brokenShardMeta.setDisplayName(ChatColor.DARK_AQUA + "Broken Shard");
        brokenShard.setItemMeta(brokenShardMeta);
        if (brokenShardMeta.getDisplayName().equals(ChatColor.DARK_AQUA + "Broken Shard")) {
            if (player.getInventory().containsAtLeast(brokenShard, tokenAmount)) {
                player.getInventory().removeItem(brokenShard);
                player.getInventory().addItem(new ItemStack(material, materialAmount));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have enough Broken Shards!!");
            }
        }
    }

    public ItemStack createItems(Material material, String name, int price, String materialName, int amount) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Kost:");
        lore.add(ChatColor.GREEN + "" + price + materialName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack createEfficiencyPickaxe(Material material, String name, int efficiencyLevel, int price, String materialName) {
        ItemStack pickaxe = new ItemStack(material);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();

        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, efficiencyLevel, true);

        pickaxeMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Kost:");
        lore.add(ChatColor.GREEN + "" + price + materialName);
        pickaxeMeta.setLore(lore);
        pickaxe.setItemMeta(pickaxeMeta);

        return pickaxe;
    }

    public ItemStack createEnchantedBow(Material material, String name, int enchantmentLevel, int price, String materialName, Enchantment enchantment) {
        ItemStack bow = new ItemStack(material);
        ItemMeta bowMeta = bow.getItemMeta();

        bowMeta.addEnchant(enchantment, enchantmentLevel, true);

        bowMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Kost:");
        lore.add(ChatColor.GREEN + "" + price + materialName);
        bowMeta.setLore(lore);
        bow.setItemMeta(bowMeta);

        return bow;
    }


}
