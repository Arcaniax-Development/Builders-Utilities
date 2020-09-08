package com.arcaniax.buildersutilities.commands;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import com.arcaniax.buildersutilities.menus.Menus;
import org.bukkit.entity.Player;

public class ColorCommand implements ICommand {
    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.util.color")) {
            if(Settings.sendErrorMessages){
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }

        Menus.COLOR_MENU.open(player);
    }
}
