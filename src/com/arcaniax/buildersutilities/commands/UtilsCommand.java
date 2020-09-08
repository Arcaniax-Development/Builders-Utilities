package com.arcaniax.buildersutilities.commands;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import com.arcaniax.buildersutilities.menus.Menus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilsCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.gui")) {
            if (Settings.sendErrorMessages) {
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        Menus.TOGGLE_MENU.open(player);
    }
}
