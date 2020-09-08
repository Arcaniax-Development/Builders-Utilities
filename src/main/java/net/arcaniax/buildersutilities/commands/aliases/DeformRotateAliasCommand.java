package net.arcaniax.buildersutilities.commands.aliases;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeformRotateAliasCommand implements ICommand {
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
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//derot [axis] [degrees]");
            return;
        }

        int degrees;
        try {
            degrees = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//derot [axis] [degrees]");
            return;
        }
//            Old:
//            float radian = (float) (((float) degrees / (float) 360) * 2 * Math.PI);

        //Use the degree to radian conversion number: "0.0174533" radians per degree
        float radian = degrees * radiansPerDegree;

        if (args[0].equalsIgnoreCase("x")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(y,z," + radian + ")");
        } else if (args[0].equalsIgnoreCase("y")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(x,z," + radian + ")");
        } else if (args[0].equalsIgnoreCase("z")) {
            Main.getInstance().getServer().dispatchCommand(player, "/deform rotate(x,y," + radian + ")");
        } else {
            player.sendMessage(Main.MSG_PREFIX + ChatColor.RED + "//derot [axis] [degrees]");
        }
    }
}
