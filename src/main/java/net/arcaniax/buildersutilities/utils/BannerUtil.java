/*
 * Builder's Utilities is a collection of a lot of tiny features that help with building.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.buildersutilities.utils;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BannerUtil {

    public static final HashMap<UUID, ItemStack> currentBanner = new HashMap<>();
    public static final HashMap<UUID, DyeColor> selectedColor = new HashMap<>();

    public static final List<DyeColor> allColors = new ArrayList<>();
    public static final List<PatternType> allPatterns = new ArrayList<>();
    private static final Random random = new Random();

    public static void addPatterns() {
        String version = Bukkit.getVersion(); // #getMinecraftVersion does not exist on Spigot; lame!
        boolean experimental = version.contains("1.20.5") || version.contains("1.20.6");

        for (PatternType pt : PatternType.values()) {
            if (patternAllowed(pt, experimental)) {
                allPatterns.add(pt);
            }
        }
    }

    private static boolean patternAllowed(PatternType pt, boolean experimental) {
        if (pt.equals(PatternType.BASE)) {
            return false;
        }

        // flow and guster patterns are experimental in 1.20.5/6.
        if (experimental) {
            String id = pt.getIdentifier();
            if (id.equalsIgnoreCase("flw") || id.equalsIgnoreCase("gus")) {
                return false;
            }
        }

        return true;
    }

    public static void addColors() {
        allColors.add(DyeColor.BLACK);
        allColors.add(DyeColor.RED);
        allColors.add(DyeColor.GREEN);
        allColors.add(DyeColor.BROWN);
        allColors.add(DyeColor.BLUE);
        allColors.add(DyeColor.PURPLE);
        allColors.add(DyeColor.CYAN);
        allColors.add(DyeColor.LIGHT_GRAY);
        allColors.add(DyeColor.GRAY);
        allColors.add(DyeColor.PINK);
        allColors.add(DyeColor.LIME);
        allColors.add(DyeColor.YELLOW);
        allColors.add(DyeColor.LIGHT_BLUE);
        allColors.add(DyeColor.MAGENTA);
        allColors.add(DyeColor.ORANGE);
        allColors.add(DyeColor.WHITE);
    }

    public static ItemStack createBanner(String name, DyeColor base, String lore, List<Pattern> patterns) {
        ItemStack item = Items.create(
                XMaterial.matchXMaterial(base.toString() + "_BANNER").get().parseMaterial(),
                (short) 0,
                1,
                name,
                ""
        );
        BannerMeta meta = (BannerMeta) item.getItemMeta();

        meta.setPatterns(patterns);
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createDye(String name, DyeColor base, String lore) {
        ItemStack item = Items.create(
                XMaterial.matchXMaterial(base.toString() + "_DYE").get().parseMaterial(),
                (short) 0,
                1,
                name,
                ""
        );
        ItemMeta meta = item.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBanner(String name, DyeColor base, String lore) {
        XMaterial.matchXMaterial(base.toString() + "_BANNER").get().parseMaterial();

        ItemStack item = Items.create(
                XMaterial.matchXMaterial(base + "_BANNER").get().parseMaterial(),
                (short) 0,
                1,
                name,
                ""
        );
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createBanner(String name, int amount, DyeColor base, String lore, Pattern pat) {
        ItemStack item = Items.create(
                XMaterial.matchXMaterial(base.toString() + "_BANNER").get().parseMaterial(),
                (short) 0,
                1,
                name,
                ""
        );
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        meta.addPattern(pat);
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addPattern(ItemStack i, Pattern pat) {
        if (i.getType().toString().contains("BANNER")) {
            BannerMeta meta = (BannerMeta) i.getItemMeta();
            List<Pattern> patterns = meta.getPatterns();
            patterns.add(pat);
            meta.setPatterns(patterns);
            i.setItemMeta(meta);
            return i;
        }
        return null;
    }

    public static List<Pattern> getPatterns(ItemStack i) {
        if (i.getType().toString().contains("BANNER")) {
            BannerMeta meta = (BannerMeta) i.getItemMeta();
            List<Pattern> patterns = meta.getPatterns();
            return patterns;
        }
        return new ArrayList<>();
    }

    public static DyeColor getRandomDye() {
        return allColors.get(random.nextInt(allColors.size()));
    }

    public static PatternType getRandomPattern() {
        return allPatterns.get(random.nextInt(allPatterns.size()));
    }

    public static DyeColor getOppositeBaseColor(DyeColor dyeColor) {
        switch (dyeColor) {
            case RED:
            case BLUE:
            case CYAN:
            case GRAY:
            case BLACK:
            case BROWN:
            case GREEN:
            case PURPLE:
                return DyeColor.WHITE;
            case PINK:
            case WHITE:
            case ORANGE:
            case YELLOW:
            case MAGENTA:
            case LIGHT_BLUE:
            case LIGHT_GRAY:
            case LIME:
            default:
                return DyeColor.BLACK;
        }
    }

}

