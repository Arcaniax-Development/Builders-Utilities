package net.arcaniax.buildersutilities.commands;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
import net.arcaniax.buildersutilities.listeners.PlayerMoveListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdvancedFlyCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.advancedfly")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        if (PlayerMoveListener.togglePlayer(player)) {
            player.sendMessage(Main.MSG_PREFIX + "Advanced Fly " + ChatColor.GREEN + ChatColor.ITALIC + "Enabled");
        } else {
            player.sendMessage(Main.MSG_PREFIX + "Advanced Fly " + ChatColor.RED + ChatColor.ITALIC + "Disabled");
        }
    }
}
