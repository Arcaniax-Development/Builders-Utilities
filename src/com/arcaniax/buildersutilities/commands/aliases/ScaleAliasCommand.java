package com.arcaniax.buildersutilities.commands.aliases;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScaleAliasCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.aliases")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        if (args.length != 1) {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//scale [size]");
            return;
        }


        double size;
        try {
            size = Double.parseDouble(args[0]);
        } catch (Exception e) {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//scale [size]");
            return;
        }

        Main.getInstance().getServer().dispatchCommand(player, "/deform x/=" + size + ";y/=" + size + ";z/=" + size);
    }
}
