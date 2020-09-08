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

package net.arcaniax.buildersutilities.commands;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.NoClipManager;
import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.commands.system.ICommand;
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
