package com.buildersrefuge.utilities.cmd;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.ColorGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ColorHandler implements Listener, CommandExecutor {
    public static Main plugin;

    public ColorHandler(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player p = (Player) sender;
            if (p.hasPermission("builders.util.color")) {
                ColorGUI g = new ColorGUI();
                p.openInventory(g.generateInv());
                return true;
            }
        return false;
    }

}