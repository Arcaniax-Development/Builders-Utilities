package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeafDecayListener implements Listener {
    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        if (Settings.disableLeavesDecay) {
            event.setCancelled(true);
        }
    }

}
