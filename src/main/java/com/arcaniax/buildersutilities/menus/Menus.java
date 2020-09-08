package com.arcaniax.buildersutilities.menus;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.menus.bannermenus.BannerMenuProvider;
import com.arcaniax.buildersutilities.menus.inv.InventoryListener;
import com.arcaniax.buildersutilities.menus.inv.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class Menus {

    private static final InventoryListener<InventoryCloseEvent> removeGhostItemsListener =
            new InventoryListener<>(InventoryCloseEvent.class, inventoryCloseEvent -> {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    ((Player) inventoryCloseEvent.getPlayer()).updateInventory();
                }, 1L);
            });

    public static final SmartInventory BANNER_MENU = SmartInventory.builder()
            .manager(Main.getInstance().getInventoryManager())
            .id("buildersutilsbanner")
            .provider(new BannerMenuProvider())
            .size(6, 9)
            .listener(removeGhostItemsListener)
            .title(ChatColor.BLUE + "Select a base color")
            .closeable(true)
            .build();

    public static final SmartInventory COLOR_MENU = SmartInventory.builder()
            .manager(Main.getInstance().getInventoryManager())
            .id("buildersutilscolor")
            .provider(new ColorMenuProvider())
            .size(6, 9)
            .listener(removeGhostItemsListener)
            .title(ChatColor.BLUE + "Armor Color Creator")
            .closeable(true)
            .build();

    public static final SmartInventory SECRET_BLOCK_MENU = SmartInventory.builder()
            .manager(Main.getInstance().getInventoryManager())
            .id("buildersutilssecretblock")
            .provider(new SecretBlockMenuProvider())
            .size(1, 9)
            .listener(removeGhostItemsListener)
            .title(ChatColor.BLUE + "Secret Blocks")
            .closeable(true)
            .build();

    public static final SmartInventory TOGGLE_MENU = SmartInventory.builder()
            .manager(Main.getInstance().getInventoryManager())
            .id("buildersutilstoggle")
            .provider(new UtilitiesMenuProvider())
            .size(3, 9)
            .listener(removeGhostItemsListener)
            .title(ChatColor.BLUE + "Builder's Utilities")
            .closeable(true)
            .build();

}
