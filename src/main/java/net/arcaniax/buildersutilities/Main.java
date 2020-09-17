/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.arcaniax.buildersutilities;

import net.arcaniax.buildersutilities.commands.system.CommandForwarder;
import net.arcaniax.buildersutilities.listeners.*;
import net.arcaniax.buildersutilities.menus.inv.InventoryManager;
import net.arcaniax.buildersutilities.utils.BannerUtil;
import net.arcaniax.buildersutilities.utils.CustomConfig;
import net.arcaniax.buildersutilities.utils.NmsManager;
import org.bukkit.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static final String MSG_PREFIX = ChatColor.DARK_AQUA + "Builders-Utilities > " + ChatColor.AQUA;
    public static final String MSG_NO_PERMISSION = MSG_PREFIX + ChatColor.AQUA + "You do not have access to this command. Lacking permission: " + ChatColor.RED;
    public static final String MSG_ERROR = MSG_PREFIX + ChatColor.DARK_RED + "Error: " + ChatColor.RED;
    private static final int BSTATS_ID = 5168;
    private static Main instance;
    private Settings settings;
    private NoClipManager noClipManager;

    private InventoryManager inventoryManager;
    private NmsManager nmsManager;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.settings = new Settings(new CustomConfig(this, "config.yml"));
        this.noClipManager = new NoClipManager();
        this.nmsManager = new NmsManager();
        this.inventoryManager = new InventoryManager(this);
        this.inventoryManager.init();
        BannerUtil.addColors();
        BannerUtil.addPatterns();

        Metrics metrics = new Metrics(this, BSTATS_ID);

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

        CommandExecutor commandExecutor = new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s,
                                     String[] strings) {
                String[] commandArgs = new String[strings.length + 1];
                commandArgs[0] = s;
                System.arraycopy(strings, 0, commandArgs, 1, strings.length);
                executor.onCommand(commandSender, command, s, commandArgs);
                return true;
            }
        };


        getCommand("butil").setExecutor(commandExecutor);
        getCommand("banner").setExecutor(commandExecutor);
        getCommand("banner").setExecutor(commandExecutor);
        getCommand("armorcolor").setExecutor(commandExecutor);
        getCommand("secretblocks").setExecutor(commandExecutor);
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
}
