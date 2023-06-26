package me.tim.witchwars.teams;

import me.tim.witchwars.config.ConfigurationManager;
import me.tim.witchwars.manager.GameManager;
import me.tim.witchwars.world.Generator;
import me.tim.witchwars.world.GeneratorType;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

public class Team {

    private TeamColor teamColor;
    private ArrayList<Player> players = new ArrayList<>();
    private ConfigurationManager data;
    private Witch witch;
    private GameManager gameManager;
    private BossBar bossBar;
    private ChatColor teamChatColor;
    private PiglinBrute shop;
    private Location respawnLocation;
    private ArrayList<Generator> islandGenerators = new ArrayList<>();

    public Team(TeamColor teamColor, ConfigurationManager data, GameManager gameManager) {
        this.teamColor = teamColor;
        this.data = data;
        this.gameManager = gameManager;
        teamChatColor = getChatColor();
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public String getName() {
        return getTeamColor().formattedName();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isMember(Player player) {
        return players.contains(player);
    }

    public void removePlayerFromTeam(Player player) {
        if (players.contains(player)) {
            players.remove(player);
        }
    }

    public void clearPlayers() {
        players.clear();
    }

    public void spawnShop() {
        Location shopLocation;
        if (data.getConfig().contains("witchwars." + teamColor + ".shop")) {
            double x = data.getConfig().getDouble("witchwars." + teamColor + ".shop.coord1");
            double y = data.getConfig().getDouble("witchwars." + teamColor + ".shop.coord2");
            double z = data.getConfig().getDouble("witchwars." + teamColor + ".shop.coord3");
            float yaw = (float) data.getConfig().getDouble("witchwars." + teamColor + ".shop.yaw");
            float pitch = (float) data.getConfig().getDouble("witchwars." + teamColor + ".shop.pitch");
            shopLocation = new Location(gameManager.getWorld(), x, y, z, yaw, pitch);
            shop = (PiglinBrute) gameManager.getWorld().spawnEntity(shopLocation, EntityType.PIGLIN_BRUTE);
            shop.setCollidable(false);
            shop.setAI(false);
            shop.setInvulnerable(true);
            shop.setCustomName(ChatColor.AQUA + "" + ChatColor.BOLD + "WitchWars Shop");
            shop.setCustomNameVisible(true);
            shop.setPersistent(true);
            shop.setRemoveWhenFarAway(false);
        }
    }

    public boolean isWitchAlive() {
        if (witch == null || !(players.size() >= 1) || !witch.isValid() || witch.isDead()) {
            return false;
        }
        if (witch.isValid() && !witch.isDead()) {
            return true;
        }
        return false;
    }

    public void removeWitch() {
        if (witch == null) return;
        if (!witch.isDead()) {
            witch.remove();
            System.out.println("Witch removed!");
        }
    }

    public void removeShop() {
        if (shop == null) return;
        if (!shop.isDead()) {
            shop.remove();
            System.out.println("Shop removed!");
        }
    }

    public void setBossBar() {
        if (witch == null || witch.getHealth() <= 0) {
            bossBar.removeAll();
            return;
        }
        bossBar.setTitle(teamChatColor + "Witch health: " + teamColor.formattedName() + " -> " + getWitchHealthForDisplay());
    }

    public String getWitchHealthForDisplay() {
        double health = witch.getHealth();
        String formatted = String.format("%.1f", health);
        return formatted;
    }

    public boolean addPlayerToTeam(Player player) {
        if (players.size() >= 2) {
            player.sendMessage(ChatColor.RED + "This team is already full.");
            return false;
        }
        players.add(player);
        return true;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void updateWitchHealth() {
        if (witch.getHealth() <= 0 || witch.isDead() || !(witch.isValid()) || witch == null) {
            return;
        }
        witch.setCustomName(teamChatColor + "Witch " + teamColor.formattedName() + ": -> " + getWitchHealthForDisplay());
    }

    public Location getRespawnLocation() {
        if (data.getConfig().contains("witchwars." + teamColor + ".respawn")) {
            double x = data.getConfig().getDouble("witchwars." + teamColor + ".respawn.coord1");
            double y = data.getConfig().getDouble("witchwars." + teamColor + ".respawn.coord2");
            double z = data.getConfig().getDouble("witchwars." + teamColor + ".respawn.coord3");
            float yaw = (float) data.getConfig().getDouble("witchwars." + teamColor + ".respawn.yaw");
            float pitch = (float) data.getConfig().getDouble("witchwars." + teamColor + ".respawn.pitch");
            respawnLocation = new Location(gameManager.getWorld(), x, y, z, yaw, pitch);
        }
        return respawnLocation;
    }


    public ChatColor getChatColor() {
        switch (teamColor) {
            case RED:
                return ChatColor.RED;
            case BLUE:
                return ChatColor.BLUE;
            case GREEN:
                return ChatColor.GREEN;
            case YELLOW:
                return ChatColor.YELLOW;
            case AQUA:
                return ChatColor.AQUA;
            case WHITE:
                return ChatColor.WHITE;
            case PINK:
                return ChatColor.LIGHT_PURPLE;
            case GRAY:
                return ChatColor.GRAY;
            default:
                return ChatColor.BOLD;
        }
    }

    public Color getLeatherArmorColor() {
        switch (teamColor) {
            case RED:
                return Color.fromRGB(255, 0, 0);
            case BLUE:
                return Color.fromRGB(0, 0, 255);
            case GREEN:
                return  Color.fromRGB(0, 255, 0);
            case YELLOW:
                return Color.fromRGB(255, 255, 0);
            case AQUA:
                return Color.fromRGB(0, 255, 255);
            case WHITE:
                return Color.fromRGB(255, 255, 255);
            case PINK:
                return Color.fromRGB(255, 192, 203);
            case GRAY:
                return Color.fromRGB(128, 128, 128);
            default:
                return Color.BLACK;
        }
    }

    public ItemStack getLeatherArmorPiece(Material leatherArmor) {
        ItemStack itemStack = new ItemStack(leatherArmor);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        armorMeta.setColor(getLeatherArmorColor());
        itemStack.setItemMeta(armorMeta);
        return itemStack;
    }

    public Material getWoolColor() {
        switch (teamColor) {
            case RED:
                return Material.RED_WOOL;
            case BLUE:
                return Material.BLUE_WOOL;
            case GREEN:
                return Material.GREEN_WOOL;
            case YELLOW:
                return Material.YELLOW_WOOL;
            case AQUA:
                return Material.LIGHT_BLUE_WOOL;
            case WHITE:
                return Material.WHITE_WOOL;
            case PINK:
                return Material.PINK_WOOL;
            case GRAY:
                return Material.GRAY_WOOL;
            default:
                return Material.BLACK_WOOL;
        }
    }

    public void addPlayerToBossBar() {
        for (Player player : players) {
        bossBar.addPlayer(player);
        }
    }

    public void spawnWitch() {
        Location witchLocation;
        if (data.getConfig().contains("witchwars." + teamColor + ".witch")) {
            double x = data.getConfig().getDouble("witchwars." + teamColor + ".witch.coord1");
            double y = data.getConfig().getDouble("witchwars." + teamColor + ".witch.coord2");
            double z = data.getConfig().getDouble("witchwars." + teamColor + ".witch.coord3");
            float yaw = (float) data.getConfig().getDouble("witchwars." + teamColor + ".witch.yaw");
            float pitch = (float) data.getConfig().getDouble("witchwars." + teamColor + ".witch.pitch");
            witchLocation = new Location(gameManager.getWorld(), x, y, z, yaw, pitch);
                witch = (Witch) gameManager.getWorld().spawnEntity(witchLocation, EntityType.WITCH);
                witch.setCollidable(false);
                witch.setAI(false);
                AttributeInstance healthAttribute = witch.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                healthAttribute.setBaseValue(50.0);
                witch.setHealth(50.0);
                AttributeInstance knockbackResistanceAttribute = witch.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
                knockbackResistanceAttribute.setBaseValue(1.0);
                bossBar = Bukkit.getServer().createBossBar("Witch health: " + teamColor + "-> " +
                        getWitchHealthForDisplay(), BarColor.WHITE, BarStyle.SOLID);
                witch.setCustomNameVisible(true);
                witch.setPersistent(true);
                witch.setRemoveWhenFarAway(false);
                setBossBar();
        }
    }

    public String getWitchEmoji() {
        if (isWitchAlive()) {
            return "&a✔";
        }
        if (players.size() == 0) {
            return "&c✖";
        }
        return "&a" + alivePlayerCount();
    }

    public void createGenerators() {
        for (int i = 1; i < 3; i++) {
            double x = data.getConfig().getDouble("witchwars." + teamColor + ".islandgenerator" + i + ".coord1");
            double y = data.getConfig().getDouble("witchwars." + teamColor + ".islandgenerator" + i + ".coord2");
            double z = data.getConfig().getDouble("witchwars." + teamColor + ".islandgenerator" + i + ".coord3");
            float yaw = (float) data.getConfig().getDouble("witchwars." + teamColor + ".islandgenerator" + i + ".yaw");
            float pitch = (float) data.getConfig().getDouble("witchwars." + teamColor + ".islandgenerator" + i + ".pitch");
            Location location = new Location(gameManager.getWorld(), x, y, z, yaw, pitch);
            Generator generator = new Generator(location, GeneratorType.BLOOD_TOKEN, teamColor);
            islandGenerators.add(generator);
        }
    }

    public Witch getWitch() {
        return witch;
    }


    public void clearGenerator() {
        islandGenerators.clear();
    }

    public ArrayList<Generator> getIslandGenerators() {
        return islandGenerators;
    }

    public int alivePlayerCount() {
    return players.stream().filter(player -> player.getGameMode() != GameMode.SPECTATOR).toArray().length;
    }
}
