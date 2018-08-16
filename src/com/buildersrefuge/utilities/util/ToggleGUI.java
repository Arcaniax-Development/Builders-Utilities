package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.object.NoClipManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ToggleGUI {

    public Inventory generateInv(Player p) {
        Items i = new Items();
        Inventory inv = Bukkit.createInventory(null, 27, "§1Builders Utilities");
        ItemStack GRAY_GLASS_PANE = i.create(Material.STAINED_GLASS_PANE, (short) 8, 1, "&7", "");
        for (int x = 0; x < 27; x++) {
            inv.setItem(x, GRAY_GLASS_PANE);
        }
        updateInv(inv, p);
        return inv;
    }

    public void updateInv(Inventory inv, Player p) {
        Items i = new Items();
        ItemStack GREEN_GLASS_PANE = i.create(Material.STAINED_GLASS_PANE, (short) 5, 1, "&7", "");
        ItemStack ORANGE_GLASS_PANE = i.create(Material.STAINED_GLASS_PANE, (short) 1, 1, "&7", "");
        ItemStack RED_GLASS_PANE = i.create(Material.STAINED_GLASS_PANE, (short) 14, 1, "&7", "");
        if (!Main.ironTrapdoorNames.contains(p.getName())) {
            inv.setItem(1, GREEN_GLASS_PANE);
            inv.setItem(10, i.create(Material.IRON_TRAPDOOR, (short) 0, 1, "&6Iron Trapdoor Interaction", "&a&lEnabled__&7__&7Click to toggle"));
            inv.setItem(19, GREEN_GLASS_PANE);
        } else {
            inv.setItem(1, RED_GLASS_PANE);
            inv.setItem(10, i.create(Material.IRON_TRAPDOOR, (short) 0, 1, "&6Iron Trapdoor Interaction", "&c&lDisabled__&7__&7Click to toggle"));
            inv.setItem(19, RED_GLASS_PANE);
        }
        if (!Main.slabNames.contains(p.getName())) {
            inv.setItem(2, GREEN_GLASS_PANE);
            inv.setItem(11, i.create(Material.STEP, (short) 0, 1, "&6Custom Slab Breaking", "&a&lEnabled__&7__&7Click to toggle"));
            inv.setItem(20, GREEN_GLASS_PANE);
        } else {
            inv.setItem(2, RED_GLASS_PANE);
            inv.setItem(11, i.create(Material.STEP, (short) 0, 1, "&6Custom Slab Breaking", "&c&lDisabled__&7__&7Click to toggle"));
            inv.setItem(20, RED_GLASS_PANE);
        }
        if (Main.nmsManager.isAtLeastVersion(1, 12 ,0)){
            if (Main.terracottaNames.contains(p.getName())) {
                inv.setItem(3, GREEN_GLASS_PANE);
                inv.setItem(12, i.create(Material.ORANGE_GLAZED_TERRACOTTA, (short) 0, 1, "&6Glazed Terracotta Rotating", "&a&lEnabled__&7__&7Click to toggle"));
                inv.setItem(21, GREEN_GLASS_PANE);
            } else {
                inv.setItem(3, RED_GLASS_PANE);
                inv.setItem(12, i.create(Material.ORANGE_GLAZED_TERRACOTTA, (short) 0, 1, "&6Glazed Terracotta Rotating", "&c&lDisabled__&7__&7Click to toggle"));
                inv.setItem(21, RED_GLASS_PANE);
            }
        } else {
            inv.setItem(3, ORANGE_GLASS_PANE);
            inv.setItem(12, i.create(Material.STAINED_GLASS, (short) 1, 1, "&6Glazed Terracotta Rotating", "&c&l1.12+ only"));
            inv.setItem(21, ORANGE_GLASS_PANE);
        }

        if (p.hasPermission("builders.util.nightvision")) {
            if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                inv.setItem(5, GREEN_GLASS_PANE);
                inv.setItem(14, i.create(Material.EYE_OF_ENDER, (short) 0, 1, "&6Nightvision", "&a&lEnabled__&7__&7Click to toggle"));
                inv.setItem(23, GREEN_GLASS_PANE);
            } else {
                inv.setItem(5, RED_GLASS_PANE);
                inv.setItem(14, i.create(Material.EYE_OF_ENDER, (short) 0, 1, "&6Nightvision", "&c&lDisabled__&7__&7Click to toggle"));
                inv.setItem(23, RED_GLASS_PANE);
            }
        } else {
            inv.setItem(5, ORANGE_GLASS_PANE);
            inv.setItem(14, i.create(Material.EYE_OF_ENDER, (short) 0, 1, "&6Nightvision", "&c&lNo permission"));
            inv.setItem(23, ORANGE_GLASS_PANE);
        }

        if (p.hasPermission("builders.util.noclip")) {
            if (NoClipManager.noClipPlayerNames.contains(p.getName())) {
                inv.setItem(6, GREEN_GLASS_PANE);
                inv.setItem(15, i.create(Material.COMPASS, (short) 0, 1, "&6NoClip", "&a&lEnabled__&7__&7Click to toggle"));
                inv.setItem(24, GREEN_GLASS_PANE);
            } else {
                inv.setItem(6, RED_GLASS_PANE);
                inv.setItem(15, i.create(Material.COMPASS, (short) 0, 1, "&6NoClip", "&c&lDisabled__&7__&7Click to toggle"));
                inv.setItem(24, RED_GLASS_PANE);
            }
        } else {
            inv.setItem(6, ORANGE_GLASS_PANE);
            inv.setItem(15, i.create(Material.COMPASS, (short) 0, 1, "&6NoClip", "&c&lNo permission"));
            inv.setItem(24, ORANGE_GLASS_PANE);
        }

        if (p.hasPermission("builders.util.advancedfly")) {
            if (com.buildersrefuge.utilities.listeners.PlayerMoveListener.enabledPlayers.contains(p.getName())) {
                inv.setItem(7, GREEN_GLASS_PANE);
                inv.setItem(16, i.create(Material.FEATHER, (short) 0, 1, "&6Advanced Fly", "&a&lEnabled__&7__&7Click to toggle"));
                inv.setItem(25, GREEN_GLASS_PANE);
            } else {
                inv.setItem(7, RED_GLASS_PANE);
                inv.setItem(16, i.create(Material.FEATHER, (short) 0, 1, "&6Advanced Fly", "&c&lDisabled__&7__&7Click to toggle"));
                inv.setItem(25, RED_GLASS_PANE);
            }
        } else {
            inv.setItem(7, ORANGE_GLASS_PANE);
            inv.setItem(16, i.create(Material.FEATHER, (short) 0, 1, "&6Advanced Fly", "&c&lNo permission"));
            inv.setItem(25, ORANGE_GLASS_PANE);
        }
    }
}
