package me.tim.witchwars.tasks;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private GameManager gameManager;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 10;

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + timeLeft + " seconds until game starts!");

    }
}
