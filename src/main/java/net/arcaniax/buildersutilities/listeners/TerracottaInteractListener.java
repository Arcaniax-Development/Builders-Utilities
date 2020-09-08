package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TerracottaInteractListener implements Listener {
    public static Set<UUID> terracottaIds;

    public TerracottaInteractListener() {
        terracottaIds = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void GlazedTerracottaInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (terracottaIds.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!event.getClickedBlock().getType().name().contains("GLAZED")) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        Material type = event.getPlayer().getInventory().getItemInHand().getType();
        if (!(type.equals(Material.AIR))) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            Block block = event.getClickedBlock();
            Directional directional = (Directional) block.getBlockData();
            if (directional.getFacing().equals(BlockFace.NORTH)) {
                directional.setFacing(BlockFace.EAST);
            } else if (directional.getFacing().equals(BlockFace.EAST)) {
                directional.setFacing(BlockFace.SOUTH);
            } else if (directional.getFacing().equals(BlockFace.SOUTH)) {
                directional.setFacing(BlockFace.WEST);
            } else if (directional.getFacing().equals(BlockFace.WEST)) {
                directional.setFacing(BlockFace.NORTH);
            }
            block.setBlockData(directional);
        }, 0L);
        event.setCancelled(true);
    }

}
