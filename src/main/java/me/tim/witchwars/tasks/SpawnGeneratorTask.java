package me.tim.witchwars.tasks;

import me.tim.witchwars.config.ConfigurationManager;
import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import me.tim.witchwars.teams.TeamColor;
import me.tim.witchwars.world.Generator;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import me.tim.witchwars.teams.Team;

public class SpawnGeneratorTask extends BukkitRunnable {

    private GameManager gameManager;
    private ConfigurationManager data;

    public SpawnGeneratorTask(GameManager gameManager, ConfigurationManager data) {
        this.gameManager = gameManager;
        this.data = data;
    }


    @Override
    public void run() {
        if (gameManager.gameState == GameState.ACTIVE) {
            for (Team team : gameManager.getTeams()) {
                for (Generator generator : team.getIslandGenerators()) {
                    generator.spawn();
                }
            }
        }
    }
}
