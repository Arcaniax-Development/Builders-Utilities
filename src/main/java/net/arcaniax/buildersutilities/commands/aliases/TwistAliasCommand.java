/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.buildersutilities.commands.aliases;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TwistAliasCommand implements ICommand {

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

        float radiansPerDegree = 0.0174533f;
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
