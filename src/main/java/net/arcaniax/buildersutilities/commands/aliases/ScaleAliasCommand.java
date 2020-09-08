package net.arcaniax.buildersutilities.commands.aliases;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
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
