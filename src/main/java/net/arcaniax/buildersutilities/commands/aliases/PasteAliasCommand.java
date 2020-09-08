package net.arcaniax.buildersutilities.commands.aliases;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.entity.Player;

public class PasteAliasCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.aliases")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        StringBuilder builder = new StringBuilder();
        if (args.length != 0) {
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
        }

        Main.getInstance().getServer().dispatchCommand(player, "/paste " + builder.toString());
    }
}
