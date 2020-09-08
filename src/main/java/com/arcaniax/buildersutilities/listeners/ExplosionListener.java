package com.arcaniax.buildersutilities.listeners;

import com.arcaniax.buildersutilities.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (Settings.disableExplosions) {
            event.setCancelled(true);
        }
    }


}
