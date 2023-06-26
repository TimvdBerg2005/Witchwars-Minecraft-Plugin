package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageWitchCancelListener implements Listener {

    private GameManager gameManager;

    public DamageWitchCancelListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onWitchDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Witch) {
            Player player = (Player) event.getDamager();
            Team team = gameManager.getPlayerTeam(player);
            if (event.getEntity() == team.getWitch()) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You can't damage your own witch.");
            }

            for (Team teams : gameManager.getTeams()) {
                if (!teams.isWitchAlive()) return;
                if (event.getEntity() == teams.getWitch()) {
                    for (Player players : teams.getPlayers()) {
                        players.playSound(player.getLocation(), Sound.ENTITY_GHAST_HURT, 1.0f, 5.0f);
                    }
                }
            }
        }
    }
}
