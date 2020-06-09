package com.buildersrefuge.utilities.util;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class SecretBlockGUI {

    public SecretBlockGUI() {
    }

    public Inventory generateStartInv() {
        Items i = new Items();
        Inventory inv = Bukkit.createInventory(new UtilitiesInventoryHolder(InventoryTypeEnum.SECRET), 9, "§1Secret Blocks");
        inv.setItem(0, i.create(Material.SPAWNER, (short) 0, 1, "§3Spawner Cage", ""));
        inv.setItem(1, i.create(Material.BARRIER, (short) 0, 1, "§3Barrier", ""));
        inv.setItem(2, i.create(Material.DRAGON_EGG, (short) 0, 1, "§3Dragon Egg", ""));
        if (Main.nmsManager.isAtLeastVersion(1, 9 ,0)){
            inv.setItem(3, i.create(Material.GRASS_PATH, (short) 0, 1, "§3Grass Path", ""));
        }
        return inv;
    }

}
