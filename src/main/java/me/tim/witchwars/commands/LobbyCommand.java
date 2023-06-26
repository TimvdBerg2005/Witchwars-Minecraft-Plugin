package me.tim.witchwars.commands;

import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    private GameManager gameManager;
    public LobbyCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "Only admins are allowed to use this command.");
            return true;
        }

        gameManager.setGameState(GameState.LOBBY);
        return false;
    }

}
