package me.tim.witchwars.events;

import fr.mrmicky.fastboard.FastBoard;
import me.tim.witchwars.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    private GameManager gameManager;

    public PlayerJoinListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);

        String title = "     &5&lWitch Wars   ";
        board.updateTitle(ChatColor.translateAlternateColorCodes('&', title));

        gameManager.getScoreboard().put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FastBoard board = gameManager.getScoreboard().remove(player.getUniqueId());

        if (board != null)  {
            board.delete();
        }
    }

}
