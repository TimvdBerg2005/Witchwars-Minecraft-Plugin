package me.tim.witchwars.manager;

import me.tim.witchwars.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KillManager {

    private GameManager gameManager;

    public KillManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void checkForBloodTokens(Player player, Player killer) {
        ItemStack bloodToken = new ItemStack(Material.NETHER_WART, 1);
        ItemMeta meta = bloodToken.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Blood Token");
        bloodToken.setItemMeta(meta);

        if (player.getInventory().containsAtLeast(bloodToken, 1)) {
            int bloodTokensCount = 0;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == Material.NETHER_WART) {
                    bloodTokensCount += item.getAmount();
                    item.setAmount(0);
                }
            }

            ItemStack giveBloodTokens = new ItemStack(Material.NETHER_WART, bloodTokensCount);
            ItemMeta itemMeta = giveBloodTokens.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RED + "Blood Token");
            giveBloodTokens.setItemMeta(itemMeta);

            killer.getInventory().addItem(giveBloodTokens);
            killer.sendMessage(ChatColor.RED + "+ " + bloodTokensCount + " Blood Tokens");
        }
    }

    public void checkForBrokenShards(Player player, Player killer) {
        ItemStack brokenShard = new ItemStack(Material.PRISMARINE_SHARD, 1);
        ItemMeta shardMeta = brokenShard.getItemMeta();
        shardMeta.setDisplayName(ChatColor.DARK_AQUA + "Broken Shard");
        brokenShard.setItemMeta(shardMeta);

        if (player.getInventory().containsAtLeast(brokenShard, 1)) {
            int brokenShardsCount = 0;
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.getType() == Material.PRISMARINE_SHARD) {
                    brokenShardsCount += item.getAmount();
                    item.setAmount(0);
                }
            }

            ItemStack giveBrokenShards = new ItemStack(Material.PRISMARINE_SHARD, brokenShardsCount);
            ItemMeta itemMeta = giveBrokenShards.getItemMeta();
            itemMeta.setDisplayName(ChatColor.DARK_AQUA + "Broken Shard");
            giveBrokenShards.setItemMeta(itemMeta);

            killer.getInventory().addItem(giveBrokenShards);
            killer.sendMessage(ChatColor.DARK_AQUA + "+ " + brokenShardsCount + " Broken Shards");
        }
    }
}
