package com.arcaniax.buildersutilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            //TODO what is this tpgm3 lol
            if (!event.getPlayer().hasPermission("builders.util.tpgm3")) {
                event.setCancelled(true);
            }
        }
    }

}
