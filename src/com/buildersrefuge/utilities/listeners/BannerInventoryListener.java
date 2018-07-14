package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.util.BannerGUI;
import com.buildersrefuge.utilities.util.BannerUtil;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class BannerInventoryListener implements Listener {
    public Main plugin;

    public BannerInventoryListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int slot;
        Random r = new Random();
        String name;
        try {
            slot = e.getRawSlot();
            name = e.getClickedInventory().getName();
        } catch (Exception exc) {
            return;
        }
        BannerGUI gui = new BannerGUI();
        switch (name) {
            case "§1Select a base color":
                e.setCancelled(true);
                if (slot == 1) {
                    int ran = r.nextInt(16);
                    if (ran > 7) {
                        ran++;
                    }
                    p.openInventory(gui.generateColorInv(e.getClickedInventory(), e.getClickedInventory().getItem(ran + 20), true));
                }
                if (slot == 7) {
                    p.closeInventory();
                }
                if (slot > 18 && slot < 36 && (slot % 9) > 0) {
                    p.openInventory(gui.generateColorInv(e.getClickedInventory(), e.getCurrentItem(), true));
                }
                break;
            case "§1Select a color":
                e.setCancelled(true);
                if (slot == 1) {
                    int ran = r.nextInt(16);
                    if (ran > 7) {
                        ran++;
                    }
                    p.openInventory(gui.generatePatternInv(e.getClickedInventory(), e.getClickedInventory().getItem(ran + 20)));
                }
                if (slot == 7) {
                    p.closeInventory();
                } else if (slot == 4) {
                    ItemStack item = e.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);
                    p.closeInventory();
                } else if (slot > 18 && slot < 36 && (slot % 9) > 0) {
                    p.openInventory(gui.generatePatternInv(e.getClickedInventory(), e.getCurrentItem()));
                }
                break;
            case "§1Select a pattern":
                e.setCancelled(true);
                if (slot == 1) {
                    if (((BannerMeta) e.getClickedInventory().getItem(4).getItemMeta()).numberOfPatterns() > 9) {
                        BannerUtil bU = new BannerUtil();
                        int ran = r.nextInt(38) + 9;
                        ItemStack item = bU.addPattern(e.getClickedInventory().getItem(4), new Pattern(bU.getColorFromBanner(e.getClickedInventory().getItem(ran)), bU.getPatternType(e.getClickedInventory().getItem(ran))));
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("");
                        item.setItemMeta(meta);
                        p.getInventory().addItem(item);
                        p.closeInventory();
                    } else {
                        p.openInventory(gui.generateColorInv(e.getClickedInventory(), e.getClickedInventory().getItem(r.nextInt(38) + 9), false));
                    }
                }
                if (slot == 7) {
                    p.closeInventory();
                } else if (slot == 4) {
                    ItemStack item = e.getCurrentItem();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("");
                    item.setItemMeta(meta);
                    p.getInventory().addItem(item);
                    p.closeInventory();
                } else if (slot > 8 && slot < 54) {
                    if (((BannerMeta) e.getClickedInventory().getItem(4).getItemMeta()).numberOfPatterns() > 9) {
                        BannerUtil bU = new BannerUtil();
                        ItemStack item = bU.addPattern(e.getClickedInventory().getItem(4), new Pattern(bU.getColorFromBanner(e.getCurrentItem()), bU.getPatternType(e.getCurrentItem())));
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("");
                        item.setItemMeta(meta);
                        p.getInventory().addItem(item);
                        p.closeInventory();
                    } else {
                        p.openInventory(gui.generateColorInv(e.getClickedInventory(), e.getCurrentItem(), false));
                    }

                }
                break;
        }
    }
}

