package com.buildersrefuge.utilities.listeners;

import com.boydti.fawe.object.FawePlayer;
import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SecretBlocksInventoryListener implements Listener {
    public Main plugin;

    public SecretBlocksInventoryListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        try {
            e.getClickedInventory().getHolder();
        } catch (Exception exc) {
            return;
        }
        if (e.getClickedInventory().getHolder() instanceof UtilitiesInventoryHolder){
            UtilitiesInventoryHolder inventoryHolder = (UtilitiesInventoryHolder)e.getClickedInventory().getHolder();
            InventoryTypeEnum typeEnum = inventoryHolder.getType();
            if (typeEnum.equals(InventoryTypeEnum.SECRET)) {
                e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                e.setCancelled(true);
            }
        }


    }
}

