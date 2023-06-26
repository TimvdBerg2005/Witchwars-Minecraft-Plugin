package me.tim.witchwars.tasks;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import me.tim.witchwars.teams.Team;
import me.tim.witchwars.teams.TeamColor;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateWitchHealthTask extends BukkitRunnable {
    private GameManager gameManager;


    public UpdateWitchHealthTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        for (Team team : gameManager.getTeams()) {
            if (team.getPlayers().size() >= 1) {
                team.setBossBar();
                team.updateWitchHealth();
            }
        }
    }
}
