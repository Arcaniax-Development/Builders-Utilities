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
package net.arcaniax.buildersutilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NoClipManager {

    public static Set<UUID> noClipPlayerIds = new HashSet<>();
    private final JavaPlugin javaPlugin;

    public NoClipManager(
            final @NonNull JavaPlugin javaPlugin
    ) {
        this.javaPlugin = javaPlugin;
    }

    public void start() {
        Bukkit.getScheduler().runTaskTimer(javaPlugin, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (final UUID id : noClipPlayerIds) {
            final Player p = javaPlugin.getServer().getPlayer(id);

            if (p == null || !p.isOnline()) {
                continue;
            }

            final boolean noClip;
            if (p.getGameMode() == GameMode.CREATIVE) {
                if (p.getLocation().add(0, -0.1, 0).getBlock().getType().isSolid() && p.isSneaking()) {
                    noClip = true;
                } else {
                    noClip = this.shouldNoClip(p);
                }

                if (noClip) {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            } else if (p.getGameMode() == GameMode.SPECTATOR) {
                if (p.getLocation().add(0, -0.1, 0).getBlock().getType().isSolid()) {
                    noClip = true;
                } else {
                    noClip = this.shouldNoClip(p);
                }

                if (!noClip) {
                    p.setGameMode(GameMode.CREATIVE);
                }
            }
        }
    }

    private boolean shouldNoClip(final Player player) {
        return player.getLocation().add(+0.4, 0, 0).getBlock().getType().isSolid()
                || player.getLocation().add(-0.4, 0, 0).getBlock().getType().isSolid()
                || player.getLocation().add(0, 0, +0.4).getBlock().getType().isSolid()
                || player.getLocation().add(0, 0, -0.4).getBlock().getType().isSolid()
                || player.getLocation().add(+0.4, 1, 0).getBlock().getType().isSolid()
                || player.getLocation().add(-0.4, 1, 0).getBlock().getType().isSolid()
                || player.getLocation().add(0, 1, +0.4).getBlock().getType().isSolid()
                || player.getLocation().add(0, 1, -0.4).getBlock().getType().isSolid()
                || player.getLocation().add(0, +1.9, 0).getBlock().getType().isSolid();
    }

}
