package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CompassClickListener implements Listener {

    private GameManager gameManager;
    private String inventoryName = "Select a team:";

    public CompassClickListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            if (item.getType() == Material.COMPASS) {
                openTeamSelectionGUI(event.getPlayer());
            }
        }
    }


    private void openTeamSelectionGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, inventoryName);

        for (Team team : gameManager.getTeams()) {
            ItemStack wool = new ItemStack(team.getWoolColor());
            ItemMeta meta = wool.getItemMeta();
            meta.setDisplayName(team.getChatColor() + team.getName());

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Players: ");
            lore.add(team.getChatColor() + "" + team.getPlayers().size());

            meta.setLore(lore);
            wool.setItemMeta(meta);

            gui.addItem(wool);
        }
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getView().getTitle().equals(inventoryName)) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType().toString().contains("WOOL")) {
                for (Team team : gameManager.getTeams()) {
                    if (clickedItem.getType() == team.getWoolColor()) {
                        Player player = (Player) event.getWhoClicked();
                        if (team.isMember(player)) {
                            player.sendMessage(ChatColor.YELLOW + "You are already on the " + team.getName() + " team!");
                            player.closeInventory();
                            return;
                        } else {
                            Team currentTeam = gameManager.getPlayerTeam(player);
                            if (currentTeam != null) {
                                currentTeam.removePlayerFromTeam(player);
                            }

                            team.addPlayerToTeam(player);
                            player.sendMessage(ChatColor.GREEN + "You have joined the " + team.getName() + " team!");
                            player.closeInventory();
                            return;
                        }
                    }
                }
            }
        }
    }
}
