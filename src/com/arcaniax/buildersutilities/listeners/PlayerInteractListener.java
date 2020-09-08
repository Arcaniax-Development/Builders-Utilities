package com.arcaniax.buildersutilities.listeners;

import com.arcaniax.buildersutilities.Settings;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDragonEggTP(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && event.getClickedBlock().getType().equals(Material.DRAGON_EGG)
                && (!event.getPlayer().isSneaking())
                && Settings.preventDragonEggTeleport) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (Settings.disableSoilTrample) {
            if (event.getAction() == Action.PHYSICAL) {
                Block block = event.getClickedBlock();
                if (block != null && block.getType() == Material.FARMLAND) {
                    event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                    event.setCancelled(true);
                }
            }
        }

    }
}
