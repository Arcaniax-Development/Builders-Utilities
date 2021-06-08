/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2021 Arcaniax
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

import net.arcaniax.buildersutilities.BuildersUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TerracottaInteractListener implements Listener {
    public static Set<UUID> terracottaIds;

    public TerracottaInteractListener() {
        terracottaIds = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void GlazedTerracottaInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (terracottaIds.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!event.getClickedBlock().getType().name().contains("GLAZED")) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!event.getPlayer().hasPermission("builders.util.terracotta")) {
            return;
        }
        Material type = event.getPlayer().getInventory().getItemInHand().getType();
        if (!(type.equals(Material.AIR))) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(BuildersUtilities.getInstance(), () -> {
            Block block = event.getClickedBlock();
            Directional directional = (Directional) block.getBlockData();
            if (directional.getFacing().equals(BlockFace.NORTH)) {
                directional.setFacing(BlockFace.EAST);
            } else if (directional.getFacing().equals(BlockFace.EAST)) {
                directional.setFacing(BlockFace.SOUTH);
            } else if (directional.getFacing().equals(BlockFace.SOUTH)) {
                directional.setFacing(BlockFace.WEST);
            } else if (directional.getFacing().equals(BlockFace.WEST)) {
                directional.setFacing(BlockFace.NORTH);
            }
            block.setBlockData(directional);
        }, 0L);
        event.setCancelled(true);
    }

}
