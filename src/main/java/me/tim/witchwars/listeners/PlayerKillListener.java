package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.KillManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerKillListener implements Listener {

    private GameManager gameManager;
    private KillManager killManager;

    public PlayerKillListener(GameManager gameManager, KillManager killManager) {
        this.gameManager = gameManager;
        this.killManager = killManager;
    }

    @EventHandler
    public void onPlayerDeathByPlayer(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player killer = (Player) event.getDamager();
            if (gameManager.getPlayerTeam(player) == null || gameManager.getPlayerTeam(killer) == null) return;
            Team playerTeam = gameManager.getPlayerTeam(player);
            Team killerTeam = gameManager.getPlayerTeam(killer);
            ChatColor killerColor = killerTeam.getChatColor();
            ChatColor playerColor = playerTeam.getChatColor();
            if (playerTeam.isWitchAlive()) {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    killManager.checkForBloodTokens(player, killer);
                    killManager.checkForBrokenShards(player, killer);
                    event.setCancelled(true);
                    player.setHealth(player.getMaxHealth());
                    player.teleport(new Location(player.getWorld(), 1, 175, -1));
                    player.setFoodLevel(20);
                    player.getInventory().clear();
                    Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), () -> {
                        player.teleport(gameManager.getPlayerTeam(player).getRespawnLocation());
                        player.setHealth(20);
                    }, 100L);
                    Bukkit.broadcastMessage(playerColor + event.getEntity().getName() + "" + ChatColor.GRAY +
                            " has been slain by " + killerColor + killer.getName());
                }
            } else {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Final Kill: " + playerColor +
                            event.getEntity().getName() + "" + ChatColor.GRAY +
                            " has been slain by " + killerColor + killer.getName());
                    event.setCancelled(true);
                    killManager.checkForBloodTokens(player, killer);
                    killManager.checkForBrokenShards(player, killer);
                    player.getInventory().clear();
                    ((Player) event.getEntity()).setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event instanceof EntityDamageByEntityEvent) return;
            Player player = (Player) event.getEntity();
            Team playerTeam = gameManager.getPlayerTeam(player);
            ChatColor playerColor = playerTeam.getChatColor();
            if (playerTeam.isWitchAlive()) {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    event.setCancelled(true);
                    player.setHealth(player.getMaxHealth());
                    player.getInventory().clear();
                    player.teleport(new Location(player.getWorld(), 1, 175, -1));
                    player.setFoodLevel(20);
                    Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), () -> {
                        player.teleport(gameManager.getPlayerTeam(player).getRespawnLocation());
                        player.setHealth(20);
                    }, 100L);
                    Bukkit.broadcastMessage(playerColor + event.getEntity().getName() + "" + ChatColor.GRAY +
                            " died.");
                }
            } else {
                if (player.getHealth() - event.getFinalDamage() <= 0) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Final Kill: " + playerColor +
                            event.getEntity().getName() + "" + ChatColor.GRAY +
                            " died.");
                    event.setCancelled(true);
                    ((Player) event.getEntity()).setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}
