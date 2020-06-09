package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.object.NoClipManager;
import com.buildersrefuge.utilities.util.ToggleGUI;

public class ToggleInventoryListener implements Listener{
    public Main plugin;

    public ToggleInventoryListener(Main main){
        plugin = main;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        int slot;
        try {
            e.getClickedInventory().getHolder();
        } catch (Exception exc) {
            return;
        }
        if (e.getClickedInventory().getHolder() instanceof UtilitiesInventoryHolder){
            UtilitiesInventoryHolder inventoryHolder = (UtilitiesInventoryHolder)e.getClickedInventory().getHolder();
            InventoryTypeEnum typeEnum = inventoryHolder.getType();
            try{
                slot = e.getRawSlot();
            }
            catch(Exception exc){
                return;
            }
            if (typeEnum.equals(InventoryTypeEnum.TOGGLE)) {
                ToggleGUI gui = new ToggleGUI();
                e.setCancelled(true);
                if (slot==1||slot==10||slot==19){
                    if (Main.ironTrapdoorNames.contains(p.getName())){
                        Main.ironTrapdoorNames.remove(p.getName());
                    }
                    else{
                        Main.ironTrapdoorNames.add(p.getName());
                    }
                }
                if (slot==2||slot==11||slot==20){
                    if (Main.slabNames.contains(p.getName())){
                        Main.slabNames.remove(p.getName());
                    }
                    else{
                        Main.slabNames.add(p.getName());
                    }
                }
                if (slot==3||slot==12||slot==21){
                    if (Main.nmsManager.isAtLeastVersion(1, 12 ,0)){
                        if (Main.terracottaNames.contains(p.getName())){
                            Main.terracottaNames.remove(p.getName());
                        }
                        else{
                            Main.terracottaNames.add(p.getName());
                        }
                    }
                }
                if (slot==5||slot==14||slot==23){
                    if (p.hasPermission("builders.util.nightvision")){
                        if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                        else {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
                        }
                    }
                }
                if (slot==6||slot==15||slot==24){
                    if (p.hasPermission("builders.util.noclip")){
                        if(NoClipManager.noClipPlayerNames.contains(p.getName())){
                            NoClipManager.noClipPlayerNames.remove(p.getName());
                            if (p.getGameMode()==GameMode.SPECTATOR){
                                p.setGameMode(GameMode.CREATIVE);
                            }
                        }
                        else {
                            NoClipManager.noClipPlayerNames.add(p.getName());
                        }
                    }
                }
                if (slot==7||slot==16||slot==25){
                    if (p.hasPermission("builders.util.advancedfly")){
                        com.buildersrefuge.utilities.listeners.PlayerMoveListener.togglePlayer(p);
                    }
                }
                gui.updateInv(e.getClickedInventory(), p);
            }
        }
    }
}

