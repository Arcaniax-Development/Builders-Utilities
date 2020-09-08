package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Settings;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysicsListener implements Listener {
    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        Bukkit.broadcastMessage(e.getSourceBlock().getType().name());
        Bukkit.broadcastMessage(e.getChangedType().name());
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
            }
        } else {
            if (Settings.disablePhysics) {
                e.setCancelled(true);
            }
        }

    }
}
