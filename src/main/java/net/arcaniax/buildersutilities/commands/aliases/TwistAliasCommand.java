package net.arcaniax.buildersutilities.commands.aliases;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TwistAliasCommand implements ICommand {
    private final float radiansPerDegree = 0.0174533f;

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.aliases")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        if (args.length != 2) {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//twist [axis] [degrees]");
            return;
        }

        int degrees;
        try {
            degrees = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//twist [axis] [degrees]");
            return;
        }

        float radian = degrees * radiansPerDegree;

        if (args[0].equalsIgnoreCase("x")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(y,z," + radian / 2 + "*(x+1))");
        } else if (args[0].equalsIgnoreCase("y")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(x,z," + radian / 2 + "*(y+1))");
        } else if (args[0].equalsIgnoreCase("z")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(x,y," + radian / 2 + "*(z+1))");
        } else {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//twist [axis] [degrees]");
        }
    }
}
