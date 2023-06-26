package me.tim.witchwars.listeners;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class TeamDamageListener implements Listener {

    private GameManager gameManager;

    public TeamDamageListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Witch) {
            Player damager = (Player) event.getDamager();
            ItemStack weapon = damager.getInventory().getItemInMainHand();
            if (weapon.getType().name().endsWith("_AXE")) {
                damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAxes don't deal damage. Use them for chopping wood!"));
                event.setCancelled(true);
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();
            if (damager.getLocation().getY() >= 170 && damaged.getLocation().getY() >= 170) event.setCancelled(true);
            if (gameManager.getPlayerTeam(damager) == null)  {
                event.setCancelled(true);
            }
            if (gameManager.getPlayerTeam(damaged) == null) {
                event.setCancelled(true);
            }
            Team hitterTeam = gameManager.getPlayerTeam(damager);
            Team damagedTeam = gameManager.getPlayerTeam(damaged);
            if (hitterTeam == damagedTeam) {
                event.setCancelled(true);
            }
            ItemStack weapon = damager.getInventory().getItemInMainHand();
            if (weapon.getType().name().endsWith("_AXE")) {
                damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lAxes don't deal damage. Use them for chopping wood!"));
                event.setCancelled(true);
            }
        }
    }
}
