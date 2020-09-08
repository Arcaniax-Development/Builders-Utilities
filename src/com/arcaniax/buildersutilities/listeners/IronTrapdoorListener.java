package com.arcaniax.buildersutilities.listeners;

import com.arcaniax.buildersutilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

public class IronTrapdoorListener implements Listener {
    public static Set<UUID> ironTrapdoorIds;

    public IronTrapdoorListener() {
        ironTrapdoorIds = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void ironTrapDoorInteract(final PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (ironTrapdoorIds.contains(e.getPlayer().getUniqueId())) {
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
            return;
        }
        if (Main.getInstance().getNmsManager().isAtLeastVersion(1, 9, 0)) {
            if (!e.getHand().equals(EquipmentSlot.HAND)) {
                return;
            }
        }
        if (e.getPlayer().isSneaking()) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Block b = e.getClickedBlock();
            TrapDoor trapDoor = (TrapDoor) b.getBlockData();
            trapDoor.setOpen(!trapDoor.isOpen());
            b.setBlockData(trapDoor);
        }, 0L);
        e.setCancelled(true);
    }

}
