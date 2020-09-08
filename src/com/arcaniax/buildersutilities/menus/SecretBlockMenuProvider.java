package com.arcaniax.buildersutilities.menus;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.menus.inv.ClickableItem;
import com.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import com.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import com.arcaniax.buildersutilities.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SecretBlockMenuProvider implements InventoryProvider {
    private static ItemStack SPAWNER = Items.create(Material.SPAWNER, (short) 0, 1, ChatColor.DARK_AQUA + "Spawner Cage", "");
    private static ItemStack BARRIER = Items.create(Material.BARRIER, (short) 0, 1, ChatColor.DARK_AQUA + "Barrier", "");
    private static ItemStack DRAGON_EGG = Items.create(Material.DRAGON_EGG, (short) 0, 1, ChatColor.DARK_AQUA + "Dragon Egg", "");
    private static ItemStack GRASS_PATH = Items.create(Material.GRASS_PATH, (short) 0, 1, ChatColor.DARK_AQUA + "Grass Path", "");

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0, 0, ClickableItem.of(SPAWNER, inventoryClickEvent -> player.getInventory().addItem(new ItemStack(Material.SPAWNER))));
        contents.set(0, 1, ClickableItem.of(BARRIER, inventoryClickEvent -> player.getInventory().addItem(new ItemStack(Material.BARRIER))));
        contents.set(0, 2, ClickableItem.of(DRAGON_EGG, inventoryClickEvent -> player.getInventory().addItem(new ItemStack(Material.DRAGON_EGG))));
        if (Main.getInstance().getNmsManager().isAtLeastVersion(1, 9, 0)) {
            contents.set(0, 3, ClickableItem.of(GRASS_PATH, inventoryClickEvent -> player.getInventory().addItem(new ItemStack(Material.GRASS_PATH))));
        }
    }


}
