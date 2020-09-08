package com.arcaniax.buildersutilities.commands;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.Settings;
import com.arcaniax.buildersutilities.commands.system.CommandForwarder;
import com.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.entity.Player;

public class HelpCommand implements ICommand {

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("builders.help")) {
            if(Settings.sendErrorMessages){
                player.sendMessage(Main.MSG_ERROR + "You do not have access to this command.");
            }
            return;
        }
    }

}
