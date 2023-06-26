package me.tim.witchwars.config;

import me.tim.witchwars.Witchwars;
import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.teams.TeamColor;

import java.util.Arrays;

public class ColorConfigurator {

    private final Witchwars plugin;
    private final GameManager gameManager;
    private final ConfigurationManager data;

    public ColorConfigurator(Witchwars plugin, GameManager gameManager, ConfigurationManager data) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.data = data;
        data.reloadConfig();
    }

    public void configureColors() {
        for (TeamColor color : TeamColor.values()) {
            String colorName = color.name();

            // Set witch location
            for (int i = 1; i <= 3; i++) { // iterate over x, y, and z coordinates
                String coordName = "witchwars." + colorName + ".witch." + "coord" + i;
                if (!data.getConfig().contains(coordName)) {
                    data.getConfig().set(coordName, 1);
                    data.saveConfig();
                }
            }

            // Set generator locations
            for (int i = 1; i <= 2; i++) {
                for (int j = 1; j <= 3; j++) { // iterate over x, y, and z coordinates
                    String coordName = "witchwars." + colorName + ".islandgenerator" + i + "." + "coord" + j;
                    if (!data.getConfig().contains(coordName)) {
                        data.getConfig().set(coordName, 1);
                        data.saveConfig();
                    }
                }

                // Set yaw and pitch angles for generator
                String yawName = "witchwars." + colorName + ".islandgenerator" + i + ".yaw";
                String pitchName = "witchwars." + colorName + ".islandgenerator" + i + ".pitch";
                if (!data.getConfig().contains(yawName)) {
                    data.getConfig().set(yawName, 0);
                    data.saveConfig();
                }
                if (!data.getConfig().contains(pitchName)) {
                    data.getConfig().set(pitchName, 0);
                    data.saveConfig();
                }
            }
        }

        // Set shard generator locations
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 3; j++) { // iterate over x, y, and z coordinates
                String coordName = "witchwars.shardgenerator" + i + "." + "coord" + j;
                if (!data.getConfig().contains(coordName)) {
                    data.getConfig().set(coordName, 1);
                    data.saveConfig();
                }
            }

            // Set yaw and pitch angles for shard generator
            String yawName = "witchwars.shardgenerator" + i + ".yaw";
            String pitchName = "witchwars.shardgenerator" + i + ".pitch";
            if (!data.getConfig().contains(yawName)) {
                data.getConfig().set(yawName, 0);
                data.saveConfig();
            }
            if (!data.getConfig().contains(pitchName)) {
                data.getConfig().set(pitchName, 0);
                data.saveConfig();
            }
        }
    }
}
