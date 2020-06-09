package com.buildersrefuge.utilities.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
    public Items() {
    }

    public ItemStack create(Material mat, short data, int amount, String name, String lore) {
        ItemStack is = new ItemStack(mat);
        is.setAmount(amount);
        ItemMeta meta = is.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.equals("")) {
            meta.setDisplayName(name.replace("&", "§"));
        }
        is.setItemMeta(meta);
        is.setDurability(data);
        return is;
    }

    public ItemStack color(ItemStack is, int r, int g, int b) {
        LeatherArmorMeta lam = (LeatherArmorMeta) is.getItemMeta();
        Color c = Color.fromRGB(r, g, b);
        lam.setColor(c);
        is.setItemMeta(lam);
        return is;
    }

    public ItemStack createHead(String data, int amount, String name, String lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        if (!lore.equals("")) {
            String[] loreListArray = lore.split("__");
            List<String> loreList = new ArrayList<>();
            for (String s : loreListArray) {
                loreList.add(s.replace("&", "§"));
            }
            meta.setLore(loreList);
        }
        if (!name.equals("")) {
            meta.setDisplayName(name.replace("&", "§"));
        }
        item.setItemMeta(meta);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", data));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e) {
        }
        item.setItemMeta(headMeta);
        return item;
    }
}
