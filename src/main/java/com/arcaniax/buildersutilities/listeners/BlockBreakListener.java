package com.arcaniax.buildersutilities.listeners;

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

import java.util.*;

public class BlockBreakListener implements Listener {
    public static Set<UUID> slabIds;

    public BlockBreakListener(){
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
