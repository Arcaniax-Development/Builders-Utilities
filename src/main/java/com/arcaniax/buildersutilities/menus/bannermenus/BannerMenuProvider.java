package com.arcaniax.buildersutilities.menus.bannermenus;

import com.arcaniax.buildersutilities.menus.inv.ClickableItem;
import com.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import com.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
//import com.arcaniax.buildersutilities.utils.BannerUtil;
import com.arcaniax.buildersutilities.utils.Items;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BannerMenuProvider implements InventoryProvider {
    private static final ItemStack grayPane = Items.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", "");
    private static final ItemStack randomizeHead = Items.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19", 1, "&7Click to randomise", "");
//    private static final ItemStack currentColor = BannerUtil.createBanner("&a", 1, DyeColor.WHITE, "");
    private static final ItemStack closeButton = Items.create(Material.BARRIER, (short) 0, 1, "&cClick to close", "");

    @Override
    public void init(Player player, InventoryContents contents) {
//        for (int x = 0; x < 54; x++) {
//            contents.set(x / 9, x % 9, ClickableItem.empty(grayPane));
//        }
//        contents.set(0, 1, ClickableItem.of(randomizeHead, NULL));
//        contents.set(0, 4, ClickableItem.empty(currentColor));
//        contents.set(0, 7, ClickableItem.of(closeButton, inventoryClickEvent -> contents.inventory().close(player)));
//        for (int x = 19; x < 27; x++) {
//            ItemStack banner = BannerUtil.createBanner("&3" + BannerUtil.allColors.get(x - 19).toString().toLowerCase().replace("_", " "), 1, BannerUtil.allColors.get(x - 19), "&7__&7click to select");
//            contents.set(x / 9, x % 9, ClickableItem.of(banner, NULL));
//        }
//        for (int x = 28; x < 36; x++) {
//            ItemStack banner = BannerUtil.createBanner("&3" + BannerUtil.allColors.get(x - 28 + 8).toString().toLowerCase().replace("_", " "), 1, BannerUtil.allColors.get(x - 28 + 8), "&7__&7click to select");
//            contents.set(x / 9, x % 9, ClickableItem.of(banner, NULL));
//        }
    }
}
