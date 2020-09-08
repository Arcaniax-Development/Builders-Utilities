package net.arcaniax.buildersutilities.menus;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.utils.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SecretBlockMenuProvider implements InventoryProvider {
    private static final ItemStack SPAWNER = Items.create(Material.SPAWNER, (short) 0, 1, ChatColor.DARK_AQUA + "Spawner Cage", "");
    private static final ItemStack BARRIER = Items.create(Material.BARRIER, (short) 0, 1, ChatColor.DARK_AQUA + "Barrier", "");
    private static final ItemStack DRAGON_EGG = Items.create(Material.DRAGON_EGG, (short) 0, 1, ChatColor.DARK_AQUA + "Dragon Egg", "");
    private static final ItemStack GRASS_PATH = Items.create(Material.GRASS_PATH, (short) 0, 1, ChatColor.DARK_AQUA + "Grass Path", "");

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
