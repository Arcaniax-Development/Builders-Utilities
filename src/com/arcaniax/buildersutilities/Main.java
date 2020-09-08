package com.arcaniax.buildersutilities;

import com.arcaniax.buildersutilities.commands.system.CommandForwarder;
import com.arcaniax.buildersutilities.listeners.*;
import com.arcaniax.buildersutilities.menus.inv.InventoryManager;
import com.arcaniax.buildersutilities.utils.CustomConfig;
import com.arcaniax.buildersutilities.utils.Metrics;
import com.arcaniax.buildersutilities.utils.NmsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static final String MSG_PREFIX = ChatColor.DARK_AQUA + "BuildersUtils> " + ChatColor.AQUA;
    public static final String MSG_ERROR = ChatColor.DARK_RED + "Error: " + ChatColor.RED;
    private static Main instance;

    private Metrics metrics;
    private Settings settings;
    private NoClipManager noClipManager;



    private InventoryManager inventoryManager;
    private NmsManager nmsManager;

    @Override
    public void onEnable() {
        instance = this;

        this.settings = new Settings(new CustomConfig(this, "config.yml"));
        this.noClipManager = new NoClipManager();
        this.nmsManager = new NmsManager();
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.init();

        this.metrics = new Metrics(this, /*plugin id*/ 5168);

        this.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPhysicsListener(), this);
        this.getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
        this.getServer().getPluginManager().registerEvents(new IronTrapdoorListener(), this);
        this.getServer().getPluginManager().registerEvents(new LeafDecayListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitAndJoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new TerracottaInteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new WeatherChangeListener(), this);


        CommandForwarder executor = new CommandForwarder();
        this.getCommand("bu").setExecutor(executor);
        this.getCommand("buildersutils").setExecutor(executor);
        this.getCommand("builders").setExecutor(executor);
        this.getCommand("utils").setExecutor(executor);
        this.getCommand("util").setExecutor(executor);

        CommandExecutor commandExecutor = new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                String[] commandArgs = new String[strings.length + 1];
                commandArgs[0] = s;
                for (int i = 0; i < strings.length; i++) {
                    commandArgs[i + 1] = strings[i];
                }
                executor.onCommand(commandSender, command, s, commandArgs);
                return true;
            }
        };

        getCommand("banner").setExecutor(commandExecutor);
        getCommand("armorcolor").setExecutor(commandExecutor);
        getCommand("blocks").setExecutor(commandExecutor);
        getCommand("n").setExecutor(commandExecutor);
        getCommand("nc").setExecutor(commandExecutor);
        getCommand("/1").setExecutor(commandExecutor);
        getCommand("/2").setExecutor(commandExecutor);
        getCommand("/cuboid").setExecutor(commandExecutor);
        getCommand("/convex").setExecutor(commandExecutor);
        getCommand("/s").setExecutor(commandExecutor);
        getCommand("/r").setExecutor(commandExecutor);
        getCommand("/f").setExecutor(commandExecutor);
        getCommand("/pa").setExecutor(commandExecutor);
        getCommand("/c").setExecutor(commandExecutor);
        getCommand("ws").setExecutor(commandExecutor);
        getCommand("fs").setExecutor(commandExecutor);
        getCommand("af").setExecutor(commandExecutor);
        getCommand("advfly").setExecutor(commandExecutor);
        getCommand("/derot").setExecutor(commandExecutor);
        getCommand("/scale").setExecutor(commandExecutor);
        getCommand("/twist").setExecutor(commandExecutor);
        getCommand("butil").setExecutor(commandExecutor);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Settings getSettings() {
        return settings;
    }

    public NoClipManager getNoClipManager() {
        return noClipManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public NmsManager getNmsManager() {
        return nmsManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
