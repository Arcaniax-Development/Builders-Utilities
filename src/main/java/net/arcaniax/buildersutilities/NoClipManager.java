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

package net.arcaniax.buildersutilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoClipManager {

    public static Set<UUID> noClipPlayerIds;

    public NoClipManager() {
        noClipPlayerIds = new HashSet<>();
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (UUID name : noClipPlayerIds) {
            Player player = Bukkit.getPlayer(name);
            if (player != null && player.isOnline()) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    boolean noClip;
                    if (player.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR && player
                            .isSneaking()) {
                        noClip = true;
                    } else {
                        noClip = isNoClip(player);
                    }
                    if (noClip) {
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                } else if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    boolean noClip;
                    if (player.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR) {
                        noClip = true;
                    } else {
                        noClip = isNoClip(player);
                    }
                    if (!noClip) {
                        player.setGameMode(GameMode.CREATIVE);
                    }
                }
            }
        }
    }

    private boolean isNoClip(Player player) {
        boolean noClip = false;
        if (player.getLocation().add(+0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(-0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 0, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 0, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(+0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(-0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 1, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 1, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, +1.9, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        }
        return noClip;
    }
}
