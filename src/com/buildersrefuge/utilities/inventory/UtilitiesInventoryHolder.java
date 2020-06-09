package com.buildersrefuge.utilities.inventory;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class UtilitiesInventoryHolder implements InventoryHolder {

    private InventoryTypeEnum type;
    private Inventory inv;

    public UtilitiesInventoryHolder(InventoryTypeEnum type){
        this.type = type;
        inv = Main.main.getServer().createInventory(this, 54);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public InventoryTypeEnum getType(){
        return type;
    }
}

