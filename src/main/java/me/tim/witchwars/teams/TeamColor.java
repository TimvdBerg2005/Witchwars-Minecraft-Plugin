package me.tim.witchwars.teams;

import org.bukkit.ChatColor;

public enum TeamColor {

    RED,
    BLUE,
    GREEN,
    YELLOW,
    AQUA,
    WHITE,
    PINK,
    GRAY;

    public String formattedName() {
        String caps = this.toString();
        return String.valueOf(caps.charAt(0)).toUpperCase() + caps.substring(1).toLowerCase();
    }

}
