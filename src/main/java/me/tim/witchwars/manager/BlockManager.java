package me.tim.witchwars.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class BlockManager {

    private GameManager gameManager;

    public BlockManager(GameManager gameManager) {
    this.gameManager = gameManager;


        allowedToBreak.add(Material.SNOW);
        allowedToBreak.add(Material.RED_WOOL);
        allowedToBreak.add(Material.BLUE_WOOL);
        allowedToBreak.add(Material.GREEN_WOOL);
        allowedToBreak.add(Material.YELLOW_WOOL);
        allowedToBreak.add(Material.LIGHT_BLUE_WOOL);
        allowedToBreak.add(Material.WHITE_WOOL);
        allowedToBreak.add(Material.PINK_WOOL);
        allowedToBreak.add(Material.GRAY_WOOL);
        allowedToBreak.add(Material.STONE);
        allowedToBreak.add(Material.REDSTONE_BLOCK);
        allowedToBreak.add(Material.GOLD_BLOCK);
        allowedToBreak.add(Material.SANDSTONE);
        allowedToBreak.add(Material.OBSIDIAN);
        allowedToBreak.add(Material.GLASS);
        allowedToBreak.add(Material.OAK_PLANKS);
        allowedToBreak.add(Material.CHEST);
        allowedToBreak.add(Material.COBWEB);

    }

    private Set<Material> allowedToBreak = new HashSet<>();

    public boolean canBreak(Block block) {
        return allowedToBreak.contains(block.getType());
    }
}
