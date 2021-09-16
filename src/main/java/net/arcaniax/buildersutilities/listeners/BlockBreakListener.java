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

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    public static Set<UUID> slabIds;

    public BlockBreakListener() {
        slabIds = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (slabIds.contains(e.getPlayer().getUniqueId())) {
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!e.getPlayer().hasPermission("builders.util.slabs")) {
            return;
        }
        Material type = e.getPlayer().getInventory().getItemInMainHand().getType();
        if (type.toString().toLowerCase().contains("slab")) {
            if (e.isCancelled()) {
                return;
            }
            if (e.getBlock().getType().toString().toLowerCase().contains("slab")) {
                if (isTop(e.getPlayer(), e.getBlock())) {
                    Slab blockdata = (Slab) e.getBlock().getBlockData();
                    if (blockdata.getType().equals(Slab.Type.DOUBLE)) {
                        blockdata.setType(Slab.Type.BOTTOM);
                        e.getBlock().setBlockData(blockdata, true);
                        e.setCancelled(true);
                    }
                } else {
                    Slab blockdata = (Slab) e.getBlock().getBlockData();
                    if (blockdata.getType().equals(Slab.Type.DOUBLE)) {
                        blockdata.setType(Slab.Type.TOP);
                        e.getBlock().setBlockData(blockdata, true);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isTop(Player player, Block block) {
        Location start = player.getEyeLocation().clone();
        while ((!start.getBlock().equals(block)) && start.distance(player.getEyeLocation()) < 6) {
            start.add(player.getLocation().getDirection().multiply(0.05));
        }
        return start.getY() % 1 > 0.5;
    }

}
