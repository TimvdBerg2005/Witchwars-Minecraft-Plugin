package me.tim.witchwars.manager;

import fr.mrmicky.fastboard.FastBoard;
import me.tim.witchwars.Witchwars;
import me.tim.witchwars.config.ColorConfigurator;
import me.tim.witchwars.config.ConfigurationManager;
import me.tim.witchwars.inventories.GuiManager;
import me.tim.witchwars.tasks.*;
import me.tim.witchwars.teams.Team;
import me.tim.witchwars.teams.TeamColor;
import me.tim.witchwars.teams.TeamManager;
import me.tim.witchwars.world.Generator;
import me.tim.witchwars.world.GeneratorType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GameManager {

    private final Witchwars plugin;
    private ConfigurationManager data;
    public GameState gameState = GameState.PRELOBBY;

    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private String scoreBoardEmoji;
    private ArrayList<Generator> shardGenerators = new ArrayList<>();
    private ArrayList<Block> placedBlocks = new ArrayList<>();
    private ArrayList<Team> teams;
    private int playingTeams;

    private final BlockManager blockManager;
    private final PlayerManager playerManager;
    private final TeamManager teamManager;
    private final KillManager killManager;
    private final ColorConfigurator colorConfigurator;
    private GuiManager guiManager;

    private GameStartCountdownTask gameStartCountdownTask;
    private SpawnGeneratorTask spawnGeneratorTask;
    private UpdateWitchHealthTask updateWitchHealthTask;
    private SpawnShardGeneratorTask spawnShardGeneratorTask;
    private UpdateAlivePlayersTask updateAlivePlayersTask;
    private UpdateScoreBoardTask updateScoreBoardTask;

    public GameManager(Witchwars plugin, ConfigurationManager data, GuiManager guiManager) {
        this.plugin = plugin;
        this.data = data;
        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this);
        this.teamManager = new TeamManager(this);
        this.killManager = new KillManager(this);
        this.guiManager = guiManager;
        teams = getTeams();
        this.colorConfigurator = new ColorConfigurator(plugin, this, data);
        colorConfigurator.configureColors();
        this.updateScoreBoardTask = new UpdateScoreBoardTask(this, data);
        this.updateScoreBoardTask.runTaskTimer(plugin, 0, 20);
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        if (this.gameState == gameState) return;
        this.gameState = gameState;
        switch (gameState) {
            case PRELOBBY:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Returning to the hub!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setPlayerListName(player.getName());
                    player.getInventory().clear();
                    player.teleport(new Location(player.getWorld(), -365.5, 215, -72.5, 0.0f, 0.0f));
                    player.getActivePotionEffects().clear();
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setHealth(20);
                    player.setFoodLevel(20);
                }
                for (Team team : teams) {
                    team.clearPlayers();
                }
                if (!placedBlocks.isEmpty()) {
                    removePlacedBlocks();
                }
                deleteDroppedItems();
                break;
            case LOBBY:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Returning to the lobby!");
                if (!placedBlocks.isEmpty()) {
                    removePlacedBlocks();
                }
                deleteDroppedItems();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setPlayerListName(player.getName());
                    player.getInventory().clear();
                    player.getInventory().addItem(new ItemStack(Material.COMPASS));
                        player.teleport(new Location(player.getWorld(), 1, 175, 1));
                        player.setGameMode(GameMode.SURVIVAL);
                    player.setHealth(20);
                    player.setFoodLevel(20);
                }
                for (Team team : teams) {
                    team.clearPlayers();
                }

                break;
            case STARTING:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Starting!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().remove(new ItemStack(Material.NETHER_STAR));
                }
                disableGenerators();

                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin,0, 20);

                break;
            case ACTIVE:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Good luck & Have fun!");
                for (Team team : teams) {
                    if (team.getPlayers().size() >= 1) {
                        team.spawnWitch();
                        team.addPlayerToBossBar();
                        team.spawnShop();
                        team.createGenerators();
                        for (Player player : team.getPlayers()) {
                            player.setPlayerListName(team.getChatColor() + player.getName());
                            player.teleport(team.getRespawnLocation());
                            player.setHealth(20);
                            player.setFoodLevel(20);
                        }
                        playingTeams++;
                    }
                }
                if (playingTeams <= 1) {
                    Bukkit.broadcastMessage("Not enough teams!");
                    setGameState(GameState.PRELOBBY);
                    for (Team team : teams) {
                        team.clearPlayers();
                    }
                    break;
                }
                createMiddleGenerators();

                this.updateAlivePlayersTask = new UpdateAlivePlayersTask(this);
                this.updateAlivePlayersTask.runTaskTimer(plugin,0,20);
                this.updateWitchHealthTask = new UpdateWitchHealthTask(this);
                this.updateWitchHealthTask.runTaskTimer(plugin, 0, 20);
                activateGenerators();
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                break;
            case WON:
                disableGenerators();
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Game end!");
                disableGenerators();
                for (Team team : teams) {
                    team.clearPlayers();
                    team.removeWitch();
                    team.removeShop();
                    team.clearGenerator();
                    if (team.getBossBar() != null) {
                        team.getBossBar().removeAll();
                    }
                }
                shardGenerators.clear();
                if (this.updateWitchHealthTask != null) this.updateWitchHealthTask.cancel();
                if (this.updateAlivePlayersTask != null) this.updateAlivePlayersTask.cancel();

                for (Team team : teams) {
                    if (team.getPlayers() != null) {
                        team.clearPlayers();
                    }
                }

                setGameState(GameState.PRELOBBY);

                break;
        }
    }

    public void cleanUp() {

    }

    public KillManager getKillManager() {
        return killManager;
    }

    public World getWorld() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            World world = player.getWorld();
            return world;
        }
        return null;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ArrayList<Block> getPlacedBlocks() {
        return placedBlocks;
    }

    public void removePlacedBlocks() {
        for (Block block : placedBlocks) {
            block.setType(Material.AIR);
        }

        placedBlocks.clear();
    }

    public void changeScoreBoardToStart() {
        for (FastBoard board : boards.values()) {
            updateBoard(board, "",
                    ChatColor.BOLD + "Waiting for players.",
                    getLobbyScoreBoardTeamList(teams.get(0)),
                    getLobbyScoreBoardTeamList(teams.get(1)),
                    getLobbyScoreBoardTeamList(teams.get(2)),
                    getLobbyScoreBoardTeamList(teams.get(3)),
                    getLobbyScoreBoardTeamList(teams.get(4)),
                    getLobbyScoreBoardTeamList(teams.get(5)),
                    getLobbyScoreBoardTeamList(teams.get(6)),
                    getLobbyScoreBoardTeamList(teams.get(7)),
                    "",
                    ChatColor.GRAY + "play.nexuscraft.nl");
        }
    }

    public void deleteDroppedItems() {
        for (Entity entity : getWorld().getEntities()) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public Map<UUID, FastBoard> getScoreboard() {
        return boards;
    }

    public void updateScoreboard() {
        for (FastBoard board : boards.values()) {
            updateBoard(board, "",
                    getScoreBoardText(teams.get(0)),
                    getScoreBoardText(teams.get(1)),
                    getScoreBoardText(teams.get(2)),
                    getScoreBoardText(teams.get(3)),
                    getScoreBoardText(teams.get(4)),
                    getScoreBoardText(teams.get(5)),
                    getScoreBoardText(teams.get(6)),
                    getScoreBoardText(teams.get(7)),
                    "",
                    ChatColor.GRAY + "play.nexuscraft.nl");

        }
    }

    public String getLobbyScoreBoardTeamList(Team team) {
        String size = String.valueOf(team.getPlayers().size());
        String total = ChatColor.BOLD + "" + team.getChatColor() + team.getName() + ": " + size;
        return total;
    }

    public String getScoreBoardText(Team team){
        String name = team.getName();
        String emoji = team.getWitchEmoji();
        String total = emoji + " " + name;
        return total;
    }

    public void updateBoard(FastBoard board, String ... lines) {
        for (int a = 0; a < lines.length; a++) {
            lines[a] = ChatColor.translateAlternateColorCodes('&', lines[a]);
        }

        board.updateLines(lines);
    }


    public void disableGenerators() {
        for (Team team : teams) {
        for (Generator generator : team.getIslandGenerators()) {
            generator.disableGenerator();
        }
        }
        for (Generator generator : shardGenerators) {
            generator.disableGenerator();
        }
        if (this.spawnGeneratorTask != null) this.spawnGeneratorTask.cancel();
        if (this.spawnShardGeneratorTask != null) this.spawnShardGeneratorTask.cancel();
        System.out.println("Disabled generators!");

    }

    public void activateGenerators() {
        for (Team team : teams) {
            for (Generator generator : team.getIslandGenerators()) {
                generator.activateGenerator();
                System.out.println("Enabled island generators!");
            }
            for (Generator generator : shardGenerators) {
                generator.activateGenerator();
                System.out.println("Enabled shard generators!");
            }
        }
            this.spawnGeneratorTask = new SpawnGeneratorTask(this, data);
            this.spawnGeneratorTask.runTaskTimer(plugin, 0, 40);
            this.spawnShardGeneratorTask = new SpawnShardGeneratorTask(this, data);
            this.spawnShardGeneratorTask.runTaskTimer(plugin, 0, 200);
        }


    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Team getPlayerTeam(Player player) {
        for (Team team : getTeams()) {
            if (team.isMember(player)) {
                return team;
            }
        }
        return null;
    }

    public Witchwars getPlugin() {
        return this.plugin;
    }

    public ArrayList<Team> getTeams() {
        return teamManager.getTeams();
    }

    public void fillTeams() {
        teams.add(new Team(TeamColor.RED, data, this));
        teams.add(new Team(TeamColor.BLUE, data, this));
        teams.add(new Team(TeamColor.GREEN, data, this));
        teams.add(new Team(TeamColor.YELLOW, data, this));
        teams.add(new Team(TeamColor.AQUA, data, this));
        teams.add(new Team(TeamColor.WHITE, data, this));
        teams.add(new Team(TeamColor.PINK, data, this));
        teams.add(new Team(TeamColor.GRAY, data, this));
    }

    public void createMiddleGenerators() {
        Location generatorLocation;
        World world = getWorld();
        for (int i = 1; i < 3; i++) {
            if (data.getConfig().contains("witchwars.shardgenerator" + i)) {
                double x = data.getConfig().getDouble("witchwars.shardgenerator" + i + ".coord1");
                double y = data.getConfig().getDouble("witchwars.shardgenerator" + i + ".coord2");
                double z = data.getConfig().getDouble("witchwars.shardgenerator" + i + ".coord3");
                float yaw = (float) data.getConfig().getDouble("witchwars.shardgenerator" + i + ".yaw");
                float pitch = (float) data.getConfig().getDouble("witchwars.shardgenerator" + i + ".pitch");
                generatorLocation = new Location(world, x, y, z, yaw, pitch);
                Generator generator = new Generator(generatorLocation, GeneratorType.BROKEN_SHARD);
                shardGenerators.add(generator);
            }
        }
    }

    public ArrayList<Generator> getShardGenerators() {
        return shardGenerators;
    }

}
