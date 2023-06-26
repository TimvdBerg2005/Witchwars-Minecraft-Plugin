package me.tim.witchwars;

import me.tim.witchwars.commands.LobbyCommand;
import me.tim.witchwars.commands.PreLobbyCommand;
import me.tim.witchwars.commands.StartCommand;
import me.tim.witchwars.config.ConfigurationManager;
import me.tim.witchwars.events.PlayerJoinListener;
import me.tim.witchwars.events.OnPotionDrinkEvent;
import me.tim.witchwars.inventories.*;
import me.tim.witchwars.listeners.*;
import me.tim.witchwars.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Witchwars extends JavaPlugin {
    private GameManager gameManager;
    private ConfigurationManager data;
    private GuiManager guiManager;
    public static Witchwars instance;

    @Override
    public void onEnable() {
        instance = this;
        this.data = new ConfigurationManager(gameManager, this);
        this.guiManager = new GuiManager();
        this.gameManager = new GameManager(this, data, guiManager);
        gameManager.fillTeams();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(gameManager, this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerOpenShopListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new DamageWitchCancelListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new CompassClickListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new WitchKillListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new BloodTokenPickupListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerKillListener(gameManager, gameManager.getKillManager()), this);
        getServer().getPluginManager().registerEvents(new OnPotionDrinkEvent(), this);
        getServer().getPluginManager().registerEvents(new TeamDamageListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new WitchPotionListener(), this);
        getServer().getPluginManager().registerEvents(new OnChatListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);
        getServer().getPluginManager().registerEvents(new MainShopInventory(gameManager), this);
        getServer().getPluginManager().registerEvents(new OnCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new BlocksInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new UtilityInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new ToolsInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new WeaponsInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new ArmorInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new FoodInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new ShardsInventory(gameManager, guiManager), this);
        getServer().getPluginManager().registerEvents(new BowInventory(gameManager, guiManager), this);
        getCommand("start").setExecutor(new StartCommand(gameManager));
        getCommand("lobby").setExecutor(new LobbyCommand(gameManager));
        getCommand("prelobby").setExecutor(new PreLobbyCommand(gameManager));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        gameManager.removePlacedBlocks();
        gameManager.cleanUp();
    }
}
