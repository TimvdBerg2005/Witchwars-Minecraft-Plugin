package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatListener implements Listener {

    private GameManager gameManager;

    public OnChatListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (gameManager.getPlayerTeam(player) == null) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD +  "[Lobby] " + ChatColor.RESET + player.getName() + ": " + event.getMessage());
        } else {
            ChatColor chatColor = gameManager.getPlayerTeam(player).getChatColor();
            Team team = gameManager.getPlayerTeam(player);
            event.setCancelled(true);
            Bukkit.broadcastMessage(chatColor + "" + ChatColor.BOLD + "[" + team.getName() + "] " + ChatColor.RESET + player.getName() + ": " + event.getMessage());
        }
    }
}
