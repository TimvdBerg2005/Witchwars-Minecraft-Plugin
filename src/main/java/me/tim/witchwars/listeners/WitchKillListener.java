package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class WitchKillListener implements Listener {

    private GameManager gameManager;

    public WitchKillListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onWitchDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Witch) {
            for (Team team : gameManager.getTeams()) {
                if (event.getEntity() == team.getWitch()) {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "Team " + team.getChatColor() + team.getName() + "'s" +
                            ChatColor.GRAY + " Witch has been killed.");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                    }
                    event.getDrops().clear();
                }
            }
        }
    }
}
