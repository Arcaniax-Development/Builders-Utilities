package com.arcaniax.buildersutilities.menus.bannermenus;

import com.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import com.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import com.arcaniax.buildersutilities.utils.Items;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BannerColorMenuProvider implements InventoryProvider {
    private static final ItemStack grayPane = Items.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", "");
    private static final ItemStack head = Items.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19", 1, "&7Click to randomise", "");

    @Override
    public void init(Player player, InventoryContents contents) {
//        for (int x = 0; x < 54; x++) {
//            contents.set(x / 9, x % 9, grayPane);
//        }
//        inv1.setItem(1, );
//        if (first) {
//            inv1.setItem(4, bU.createBanner("&aClick to get the banner", 1, DyeColor.valueOf(clicked.getType().toString().replace("_BANNER", "")), ""));
//        } else {
//            inv1.setItem(4, bU.addPattern(inv.getItem(4), new Pattern(bU.getColorFromBanner(clicked), bU.getPatternType(clicked))));
//        }
//        inv1.setItem(7, i.create(Material.BARRIER, (short) 0, 1, "&cClick to close", ""));
//        for (int x = 19; x < 27; x++) {
//            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 19), 1, "&3" + bU.getAllColors().get(x - 19).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
//        }
//        for (int x = 28; x < 36; x++) {
//            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 20), 1, "&3" + bU.getAllColors().get(x - 20).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
//        }
    }
}
