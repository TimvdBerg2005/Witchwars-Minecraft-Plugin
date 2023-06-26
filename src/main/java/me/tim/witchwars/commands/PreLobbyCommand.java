package me.tim.witchwars.commands;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import me.tim.witchwars.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PreLobbyCommand implements CommandExecutor {

    private GameManager gameManager;
    public PreLobbyCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "Only admins are allowed to use this command.");
            return true;
        }
        gameManager.setGameState(GameState.PRELOBBY);
        return true;
    }

}
