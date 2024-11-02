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

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Items {

    public static ItemStack create(String materialName, String name, String lore) {
        Material mat = Material.getMaterial(materialName);

        if(mat == null) {
            return null;
        }

        return Items.create(mat, (short) 0, 1, name, lore);
    }

    public static ItemStack create(Material mat, String name, String lore) {
        return Items.create(mat, (short) 0, 1, name, lore);
    }

    public static ItemStack create(Material mat, short data, int amount, String name, String lore) {
        ItemStack is = new ItemStack(mat, amount);
        ItemMeta meta = is.getItemMeta();
        if (!lore.isEmpty()) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        if (!name.isEmpty()) {
            meta.setDisplayName(name.replace('&', ChatColor.COLOR_CHAR));
        }
        is.setItemMeta(meta);
        is.setDurability(data);
        return is;
    }

    public static ItemStack color(ItemStack is, int r, int g, int b) {
        LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
        Color c = Color.fromRGB(r, g, b);
        lam.setColor(c);
        is.setItemMeta(lam);
        return is;
    }


    public static ItemStack createHead(String data, int amount, String name, String lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, amount);
        ItemMeta meta = item.getItemMeta();
        if (!lore.isEmpty()) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace('&', ChatColor.COLOR_CHAR));
            }
            meta.setLore(loreList);
        }
        if (!name.isEmpty()) {
            meta.setDisplayName(name.replace('&', ChatColor.COLOR_CHAR));
        }
        item.setItemMeta(meta);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "BuildersUtils");
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            if (profileField.getType() == GameProfile.class) {
                profileField.set(headMeta, profile);
            } else {
                Class<?> resolvableProfileClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");
                profileField.set(headMeta, resolvableProfileClass.getConstructor(GameProfile.class).newInstance(profile));
            }
        } catch (Exception ignored) {
        }
        item.setItemMeta(headMeta);
        return item;
    }

}
