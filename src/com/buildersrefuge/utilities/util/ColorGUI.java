package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class ColorGUI {

    public ColorGUI() {
    }

    public Inventory generateInv() {
        Items i = new Items();
        String lore = "&7__&7Left click to increase__&7Right click to decrease__&7Shift click to change by 5";
        Inventory inv = Bukkit.createInventory(new UtilitiesInventoryHolder(InventoryTypeEnum.COLOR), 54, "§1Armor Color Creator");
        for (int x = 0; x < 54; x++) {
            inv.setItem(x, i.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", ""));
        }
        inv.setItem(10, i.create(Material.LEATHER_HELMET, (short) 0, 1, "&aClick to get helmet", ""));
        inv.setItem(19, i.create(Material.LEATHER_CHESTPLATE, (short) 0, 1, "&aClick to get chestplate", ""));
        inv.setItem(28, i.create(Material.LEATHER_LEGGINGS, (short) 0, 1, "&aClick to get leggings", ""));
        inv.setItem(37, i.create(Material.LEATHER_BOOTS, (short) 0, 1, "&aClick to get boots", ""));
        inv.setItem(22, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEzMWRlOGU5NTFmZGQ3YjlhM2QyMzlkN2NjM2FhM2U4NjU1YTMzNmI5OTliOWVkYmI0ZmIzMjljYmQ4NyJ9fX0=",
                1, "&cClick to randomize", ""));
        inv.setItem(23, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMzY2VjNjg3NjlmZTljOTcxMjkxZWRiN2VmOTZhNGUzYjYwNDYyY2ZkNWZiNWJhYTFjYmIzYTcxNTEzZTdiIn19fQ==",
                1, "&aClick to randomize", ""));
        inv.setItem(24, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjMyOGYzNzhmMjhhOTg3MjIyNmY1Y2UwNGQ2ZTFkZmExMTE2MTg1ODdmNDhkZmExZmU4MmQwNDMyMTZhNWNmIn19fQ==",
                1, "&bClick to randomize", ""));
        inv.setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                10, "&cRed", lore));
        inv.setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                10, "&aGreen", lore));
        inv.setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                10, "&bBlue", lore));
        updateInv(inv);
        return inv;
    }

    public void updateInv(Inventory inv) {
        Items i = new Items();
        float multiplier = 255f / 20f;
        int r = (int) (((float) inv.getItem(31).getAmount()) * multiplier);
        int g = (int) (((float) inv.getItem(32).getAmount()) * multiplier);
        int b = (int) (((float) inv.getItem(33).getAmount()) * multiplier);
        inv.setItem(10, i.color(inv.getItem(10), r, g, b));
        inv.setItem(19, i.color(inv.getItem(19), r, g, b));
        inv.setItem(28, i.color(inv.getItem(28), r, g, b));
        inv.setItem(37, i.color(inv.getItem(37), r, g, b));
    }
}
