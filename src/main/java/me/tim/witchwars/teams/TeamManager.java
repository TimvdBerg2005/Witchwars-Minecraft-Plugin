package me.tim.witchwars.teams;

import me.tim.witchwars.manager.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamManager {

    private ArrayList<Team> teams = new ArrayList<>();

    private GameManager gameManager;

    public TeamManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public List<Team> getAliveTeams() {
        return teams.stream().filter(team -> team.alivePlayerCount() != 0).collect(Collectors.toList());
    }
}
