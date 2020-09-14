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

package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeleportListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger("BU/" + TeleportListener.class.getSimpleName());

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (!event.getPlayer().hasPermission("builders.util.tpgm3")) {
                event.setCancelled(true);
                if (Settings.sendDebugMessages) {
                    logger.info(
                        "Spectate teleport was cancelled because {} lacks the permission builders.util.tpgm3", event.getPlayer());
                }
            }
        }
    }

}
