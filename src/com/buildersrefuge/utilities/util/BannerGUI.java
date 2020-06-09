package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BannerGUI {

    public BannerGUI() {
    }

    public Inventory generateStartInv() {
        Items i = new Items();
        BannerUtil bU = new BannerUtil();
        Inventory inv = Bukkit.createInventory(new UtilitiesInventoryHolder(InventoryTypeEnum.BANNER_BASE), 54, "§1Select a base color");
        for (int x = 0; x < 54; x++) {
            inv.setItem(x, i.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", ""));
        }
        inv.setItem(1, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19", 1, "&7Click to randomise", ""));
        inv.setItem(4, bU.createBanner("&a", 1, DyeColor.WHITE, ""));
        inv.setItem(7, i.create(Material.BARRIER, (short) 0, 1, "&cClick to close", ""));
        for (int x = 19; x < 27; x++) {
            inv.setItem(x, bU.createBanner("&3" + bU.getAllColors().get(x - 19).toString().toLowerCase().replace("_", " "), 1, bU.getAllColors().get(x - 19), "&7__&7click to select"));
        }
        for (int x = 28; x < 36; x++) {
            inv.setItem(x, bU.createBanner("&3" + bU.getAllColors().get(x - 28 + 8).toString().toLowerCase().replace("_", " "), 1, bU.getAllColors().get(x - 28 + 8), "&7__&7click to select"));
        }
        return inv;
    }

    public Inventory generateColorInv(Inventory inv, ItemStack clicked, boolean first) {
        Items i = new Items();
        BannerUtil bU = new BannerUtil();
        Inventory inv1 = Bukkit.createInventory(new UtilitiesInventoryHolder(InventoryTypeEnum.BANNER_COLOR), 54, "§1Select a color");
        for (int x = 0; x < 54; x++) {
            inv1.setItem(x, i.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", ""));
        }
        inv1.setItem(1, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19", 1, "&7Click to randomise", ""));
        if (first) {
            inv1.setItem(4, bU.createBanner("&aClick to get the banner", 1, DyeColor.valueOf(clicked.getType().toString().replace("_BANNER","")), ""));
        } else {
            inv1.setItem(4, bU.addPattern(inv.getItem(4), new Pattern(bU.getColorFromBanner(clicked), bU.getPatternType(clicked))));
        }
        inv1.setItem(7, i.create(Material.BARRIER, (short) 0, 1, "&cClick to close", ""));
        for (int x = 19; x < 27; x++) {
            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 19), 1, "&3" + bU.getAllColors().get(x - 19).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
        }
        for (int x = 28; x < 36; x++) {
            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 20), 1, "&3" + bU.getAllColors().get(x - 20).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
        }
        return inv1;
    }

    public Inventory generatePatternInv(Inventory inv, ItemStack clicked) {
        try{
            Items i = new Items();
            BannerUtil bU = new BannerUtil();
            Inventory inv1 = Bukkit.createInventory(new UtilitiesInventoryHolder(InventoryTypeEnum.BANNER_PATTERN), 54, "§1Select a pattern");
            for (int x = 0; x < 54; x++) {
                inv1.setItem(x, i.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", ""));
            }
            inv1.setItem(1, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19", 1, "&7Click to randomise", ""));
            inv1.setItem(4, inv.getItem(4));
            inv1.setItem(7, i.create(Material.BARRIER, (short) 0, 1, "&cClick to close", ""));
            DyeColor base = DyeColor.WHITE;
            DyeColor click = bU.getDyeColor(clicked);
            if (click.equals(DyeColor.WHITE) || click.equals(DyeColor.LIGHT_GRAY) || click.equals(DyeColor.LIME) || click.equals(DyeColor.LIGHT_BLUE) || click.equals(DyeColor.YELLOW)) {
                base = DyeColor.BLACK;
            }
            for (int x = 9; x < 47; x++) {
                inv1.setItem(x, bU.createBanner("&3" + bU.getAllPatternTypes().get(x - 9).toString().toLowerCase().replace("_", " "), 1, base, "&7__&7click to select", new Pattern(click, bU.getAllPatternTypes().get(x - 9))));
            }
            return inv1;
        }
        catch (Exception e){
            return inv;
        }


    }

}
