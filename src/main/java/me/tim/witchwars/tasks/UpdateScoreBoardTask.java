package me.tim.witchwars.tasks;

import me.tim.witchwars.config.ConfigurationManager;
import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateScoreBoardTask extends BukkitRunnable {

    private GameManager gameManager;
    private ConfigurationManager data;

    public UpdateScoreBoardTask(GameManager gameManager, ConfigurationManager data) {
        this.gameManager = gameManager;
        this.data = data;
    }

    @Override
    public void run() {
        if (gameManager.gameState == GameState.LOBBY || gameManager.gameState == GameState.PRELOBBY) {
            gameManager.changeScoreBoardToStart();
        } else {
            gameManager.updateScoreboard();
        }
    }
}