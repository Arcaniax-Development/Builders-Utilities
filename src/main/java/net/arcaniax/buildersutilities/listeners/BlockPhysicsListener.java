/*
 * Builder's Utilities is a collection of a lot of tiny features that help with building.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Settings;
import net.arcaniax.buildersutilities.utils.LogManagerCompat;
import org.apache.logging.log4j.Logger;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {

    private static final Logger logger = LogManagerCompat.getLogger();

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        if (e.getSourceBlock().getType().equals(Material.AIR)) {
            if (e.getChangedType().equals(Material.AIR)) {
                if (e.getBlock().getLocation().getBlockY() > 0) {
                    if (e
                            .getBlock()
                            .getLocation()
                            .add(0, -1, 0)
                            .getBlock()
                            .getType()
                            .name()
                            .toLowerCase()
                            .contains("grass_block")) {
                        return;
                    }
                }
            }
        }
        if (e.getSourceBlock().getType().name().toLowerCase().contains("snow")) {
            if (e.getBlock().getLocation().getBlockY() > 0) {
                if (e.getBlock().getLocation().add(0, -1, 0).getBlock().getType().name().toLowerCase().contains("grass_block")) {
                    return;
                }
            }
        }
        String blockName = e.getChangedType().name().toLowerCase();
        try {
            if (blockName.contains("chest") ||
                    blockName.contains("stair") ||
                    blockName.contains("fence") ||
                    blockName.contains("pane") ||
                    blockName.contains("wall") ||
                    blockName.contains("bar") ||
                       blockName.contains("door")) {
                return;
            }
        } catch (Exception ex) {
            return;
        }
        if (!Settings.disableRedstone) {
            if (blockName.contains("redstone") ||
                    blockName.contains("daylight") ||
                    blockName.contains("diode") ||
                    blockName.contains("repeater") ||
                    blockName.contains("comparator") ||
                    blockName.contains("door") ||
                    blockName.contains("target") ||
                    blockName.contains("structure") ||
                    blockName.contains("jukebox") ||
                    blockName.contains("crafter") ||
                    blockName.contains("powered_rail") ||
                    blockName.contains("detector_rail") ||
                    blockName.contains("activator_rail") ||
                    blockName.contains("hopper") ||
                    blockName.contains("note") ||
                    blockName.contains("lever") ||
                    blockName.contains("button") ||
                    blockName.contains("command") ||
                    blockName.contains("tripwire") ||
                    blockName.contains("plate") ||
                    blockName.contains("string") ||
                    blockName.contains("piston") ||
                    blockName.contains("observer")) {
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
