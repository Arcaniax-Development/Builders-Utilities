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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.*;

public class PlayerMoveListener implements Listener {
    private static final List<String> slower = new ArrayList<>();
    private static final List<String> slower2 = new ArrayList<>();
    public static Set<UUID> enabledPlayers = new HashSet<>();
    private final HashMap<String, Double> lastVelocity = new HashMap<>();

    public static boolean togglePlayer(Player p) {
        if (enabledPlayers.contains(p.getUniqueId())) {
            enabledPlayers.remove(p.getUniqueId());
            return false;
        } else {
            enabledPlayers.add(p.getUniqueId());
            return true;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(final PlayerMoveEvent e) {
        if (e.getPlayer().isFlying()) {
            if (!enabledPlayers.contains(e.getPlayer().getUniqueId())) {
                return;
            }
            Double speed = e.getFrom().clone().add(0, -e.getFrom().getY(), 0).distance(e.getTo().clone().add(0, -e.getTo().getY(), 0));

            if (Math.abs(e.getFrom().getYaw() - e.getTo().getYaw()) > 2.5) {
                return;
            }
            if (Math.abs(e.getFrom().getPitch() - e.getTo().getPitch()) > 2.5) {
                return;
            }
            if (lastVelocity.containsKey(e.getPlayer().getName())) {
                Double lastSpeed = lastVelocity.get(e.getPlayer().getName());
                if (speed * 1.3 < lastSpeed) {
                    if (slower.contains(e.getPlayer().getName())) {
                        if (slower2.contains(e.getPlayer().getName())) {
                            Vector v = e.getPlayer().getVelocity().clone();
                            v.setX(0);
                            v.setZ(0);
                            e.getPlayer().setVelocity(v);
                            lastVelocity.put(e.getPlayer().getName(), 0.0);
                            //No more if slower.contains** as if e.getPlayer().getName() isn't there, it won't get removed (:
                            slower.remove(e.getPlayer().getName());
                            slower2.remove(e.getPlayer().getName());
                        } else {
                            slower2.add(e.getPlayer().getName());
                        }
                    } else {
                        slower.add(e.getPlayer().getName());
                    }
                } else if (speed > lastSpeed) {
                    lastVelocity.put(e.getPlayer().getName(), speed);
                    slower.remove(e.getPlayer().getName());
                    slower2.remove(e.getPlayer().getName());
                }
            } else {
                lastVelocity.put(e.getPlayer().getName(), speed);
                slower.remove(e.getPlayer().getName());
            }
        }
    }
}
