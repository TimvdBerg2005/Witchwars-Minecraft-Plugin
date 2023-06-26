package me.tim.witchwars.tasks;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import me.tim.witchwars.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateAlivePlayersTask extends BukkitRunnable {

    private GameManager gameManager;

    public UpdateAlivePlayersTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getTeamManager().getAliveTeams().size() == 1) {
            gameManager.setGameState(GameState.WON);
            cancel();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SPECTATOR) return;
            if (player.getLocation().getY() <= 40) {
                Team team = gameManager.getPlayerTeam(player);
                if (team.isWitchAlive()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4, 200));
                    Location teleportLocation = new Location(player.getWorld(), 1, 175, -1);
                    player.teleport(teleportLocation);
                    player.getInventory().clear();
                    Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), () -> {
                        player.teleport(gameManager.getPlayerTeam(player).getRespawnLocation());
                        player.setFoodLevel(20);
                        player.setHealth(20);
                    }, 100L);
                    Bukkit.broadcastMessage(team.getChatColor() + player.getName() + "" + ChatColor.GRAY +
                            " fell into the void.");
                } else {
                    Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Final Kill: " + team.getChatColor() +
                            player.getName() + "" + ChatColor.GRAY +
                            " fell into the void.");
                    player.setGameMode(GameMode.SPECTATOR);
                    Location teleportLocation = new Location(player.getWorld(), 1, 175, -1);
                    player.teleport(teleportLocation);
                    cancel();
                }
            }
        }
    }
}
