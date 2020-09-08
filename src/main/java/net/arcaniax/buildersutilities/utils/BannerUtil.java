/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
//package net.arcaniax.buildersutilities.utils;
//
//import net.arcaniax.buildersutilities.Main;
//import org.bukkit.ChatColor;
//import org.bukkit.DyeColor;
//import org.bukkit.block.banner.Pattern;
//import org.bukkit.block.banner.PatternType;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.BannerMeta;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class BannerUtil {
//    public static final List<DyeColor> allColors = new ArrayList<>();
//    public static final List<PatternType> allPatterns = new ArrayList<>();
//    private static final Random random = new Random();
//
//    {
//        addColors();
//        addPatterns();
//    }
//
//    private void addPatterns() {
//        for (PatternType pt : PatternType.values()) {
//            if (!pt.equals(PatternType.BASE)) {
//                allPatterns.add(pt);
//            }
//        }
//    }
//
//    private void addColors() {
//        allColors.add(DyeColor.BLACK);
//        allColors.add(DyeColor.RED);
//        allColors.add(DyeColor.GREEN);
//        allColors.add(DyeColor.BROWN);
//        allColors.add(DyeColor.BLUE);
//        allColors.add(DyeColor.PURPLE);
//        allColors.add(DyeColor.CYAN);
//        allColors.add(DyeColor.LIGHT_GRAY);
//        allColors.add(DyeColor.GRAY);
//        allColors.add(DyeColor.PINK);
//        allColors.add(DyeColor.LIME);
//        allColors.add(DyeColor.YELLOW);
//        allColors.add(DyeColor.LIGHT_BLUE);
//        allColors.add(DyeColor.MAGENTA);
//        allColors.add(DyeColor.ORANGE);
//        allColors.add(DyeColor.WHITE);
//    }
//
//    public static ItemStack createBanner(String name, DyeColor base, String lore, List<Pattern> patterns) {
//        ItemStack item = Items.create(XMaterial.fromString(base.toString() + "_BANNER").parseMaterial(), (short) 0, 1, name, "");
//        BannerMeta meta = (BannerMeta) item.getItemMeta();
//        if (Main.getInstance().getNmsManager().isAtLeastVersion(1, 11, 0)) {
//            item.setDurability((short) allColors.indexOf(base));
//        } else {
//            meta.setBaseColor(base);
//        }
//
//        meta.setPatterns(patterns);
//        if (!lore.equals("")) {
//            String[] loreListArray = lore.split("__");
//            List<String> loreList = new ArrayList<>();
//            for (String s : loreListArray) {
//                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
//            }
//            meta.setLore(loreList);
//        }
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    public static ItemStack createBanner(String name, int amount, DyeColor base, String lore) {
//        //TODO this might be how we fix it:
//        XMaterial.matchXMaterial(base.toString() + "_BANNER").get().parseMaterial();
//
//        ItemStack item = Items.create(XMaterial.fromString(base.toString() + "_BANNER").parseMaterial(), (short) 0, 1, name, "");
//        BannerMeta meta = (BannerMeta) item.getItemMeta();
//        if (!lore.equals("")) {
//            String[] loreListArray = lore.split("__");
//            List<String> loreList = new ArrayList<>();
//            for (String s : loreListArray) {
//                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
//            }
//            meta.setLore(loreList);
//        }
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    public static ItemStack createBanner(String name, int amount, DyeColor base, String lore, Pattern pat) {
//        ItemStack item = Items.create(XMaterial.fromString(base.toString() + "_BANNER").parseMaterial(), (short) 0, 1, name, "");
//        BannerMeta meta = (BannerMeta) item.getItemMeta();
//        meta.addPattern(pat);
//        if (!lore.equals("")) {
//            String[] loreListArray = lore.split("__");
//            List<String> loreList = new ArrayList<>();
//            for (String s : loreListArray) {
//                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
//            }
//            meta.setLore(loreList);
//        }
//        item.setItemMeta(meta);
//        return item;
//    }
//
//    public static ItemStack addPattern(ItemStack i, Pattern pat) {
//        if (i.getType().toString().contains("BANNER")) {
//            BannerMeta meta = (BannerMeta) i.getItemMeta();
//            List<Pattern> patterns = meta.getPatterns();
//            patterns.add(pat);
//            meta.setPatterns(patterns);
//            i.setItemMeta(meta);
//            return i;
//        }
//        return null;
//    }
//
//    public static DyeColor getBaseColor(ItemStack i) {
//        if (i.getType().toString().contains("BANNER")) {
//            BannerMeta meta = (BannerMeta) i.getItemMeta();
//            if (Main.getInstance().getNmsManager().isAtLeastVersion(1, 11, 0)) {
//                return allColors.get(i.getDurability());
//            } else {
//                return meta.getBaseColor();
//            }
//
//        }
//        return null;
//    }
//
//    public static PatternType getPatternType(ItemStack i) {
//        if (i.getType().toString().contains("BANNER")) {
//            BannerMeta meta = (BannerMeta) i.getItemMeta();
//            return meta.getPattern(0).getPattern();
//        }
//        return null;
//    }
//
//    public static DyeColor getColorFromBanner(ItemStack i) {
//        if (i.getType().toString().contains("BANNER")) {
//            BannerMeta meta = (BannerMeta) i.getItemMeta();
//            return meta.getPattern(0).getColor();
//        }
//        return null;
//    }
//
//    public static DyeColor getRandomDye() {
//        return allColors.get(random.nextInt(allColors.size()));
//    }
//
//    public static PatternType getRandomPattern() {
//        return allPatterns.get(random.nextInt(allPatterns.size()));
//    }
//
//    DyeColor getDyeColor(ItemStack i) {
//        switch (i.getType()) {
//            case INK_SAC:
//                return DyeColor.BLACK;
//            case ROSE_RED:
//                return DyeColor.RED;
//            case CACTUS_GREEN:
//                return DyeColor.GREEN;
//            case COCOA_BEANS:
//                return DyeColor.BROWN;
//            case LAPIS_LAZULI:
//                return DyeColor.BLUE;
//            case PURPLE_DYE:
//                return DyeColor.PURPLE;
//            case CYAN_DYE:
//                return DyeColor.CYAN;
//            case LIGHT_GRAY_DYE:
//                return DyeColor.LIGHT_GRAY;
//            case GRAY_DYE:
//                return DyeColor.GRAY;
//            case PINK_DYE:
//                return DyeColor.PINK;
//            case LIME_DYE:
//                return DyeColor.LIME;
//            case DANDELION_YELLOW:
//                return DyeColor.YELLOW;
//            case LIGHT_BLUE_DYE:
//                return DyeColor.LIGHT_BLUE;
//            case MAGENTA_DYE:
//                return DyeColor.MAGENTA;
//            case ORANGE_DYE:
//                return DyeColor.ORANGE;
//            case BONE_MEAL:
//                return DyeColor.WHITE;
//            default:
//                return DyeColor.BLACK;
//        }
//    }
//
//
//}
