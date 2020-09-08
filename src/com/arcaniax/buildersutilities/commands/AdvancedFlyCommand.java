package com.arcaniax.buildersutilities.commands;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import com.arcaniax.buildersutilities.listeners.PlayerMoveListener;
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
