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
import org.bukkit.event.block.BlockPhysicsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockPhysicsListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger("BU/" + BlockPhysicsListener.class.getSimpleName());

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        try {
            if (e.getChangedType().name().toLowerCase().contains("chest") ||
                    e.getChangedType().name().toLowerCase().contains("fence") ||
                    e.getChangedType().name().toLowerCase().contains("grass_block") ||
                    e.getChangedType().name().toLowerCase().contains("snow") ||
                    e.getBlock().getType().name().toLowerCase().contains("snow") ||
                    e.getBlock().getType().name().toLowerCase().contains("grass_block") ||
                    e.getChangedType().name().toLowerCase().contains("pane") ||
                    e.getChangedType().name().toLowerCase().contains("wall") ||
                    e.getChangedType().name().toLowerCase().contains("bar")) {
                return;
            }
        } catch (Exception ex) {
            return;
        }
        if (!Settings.disableRedstone) {
            if (e.getChangedType().name().toLowerCase().contains("redstone") ||
                    e.getChangedType().name().toLowerCase().contains("daylight") ||
                    e.getChangedType().name().toLowerCase().contains("diode") ||
                    e.getChangedType().name().toLowerCase().contains("note") ||
                    e.getChangedType().name().toLowerCase().contains("lever") ||
                    e.getChangedType().name().toLowerCase().contains("button") ||
                    e.getChangedType().name().toLowerCase().contains("command") ||
                    e.getChangedType().name().toLowerCase().contains("tripwire") ||
                    e.getChangedType().name().toLowerCase().contains("plate") ||
                    e.getChangedType().name().toLowerCase().contains("string") ||
                    e.getChangedType().name().toLowerCase().contains("piston") ||
                    e.getChangedType().name().toLowerCase().contains("observer")) {
                if (!e.getBlock().getType().name().contains("air")) {
                    return;
                }
            }
        }

        if (e.getChangedType().hasGravity()) {
            if (Settings.disableGravityPhysics) {
                e.setCancelled(true);
                if (Settings.sendDebugMessages) {
                    logger.info(
                        "Gravity physics were cancelled because disable-gravity-physics: true");
                }

            }
        } else {
            if (Settings.disablePhysics) {
                e.setCancelled(true);
                if (Settings.sendDebugMessages) {
                    logger.info(
                        "Physics were cancelled because disable-physics: true");
                }
            }
        }

    }
}
