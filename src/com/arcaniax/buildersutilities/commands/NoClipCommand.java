package com.arcaniax.buildersutilities.commands;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.NoClipManager;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class NoClipCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.noclip")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        if (NoClipManager.noClipPlayerIds.contains(player.getUniqueId())) {
            NoClipManager.noClipPlayerIds.remove(player.getUniqueId());
            player.sendMessage(Main.MSG_PREFIX + "NoClip " + ChatColor.RED + ChatColor.ITALIC + "Disabled");
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.setGameMode(GameMode.CREATIVE);
            }
        } else {
            NoClipManager.noClipPlayerIds.add(player.getUniqueId());
            player.sendMessage(Main.MSG_PREFIX + "NoClip " + ChatColor.GREEN + ChatColor.ITALIC + "Enabled");
        }
    }
}
