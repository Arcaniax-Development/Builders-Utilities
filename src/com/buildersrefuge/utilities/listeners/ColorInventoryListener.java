package com.buildersrefuge.utilities.listeners;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.enums.InventoryTypeEnum;
import com.buildersrefuge.utilities.inventory.UtilitiesInventoryHolder;
import com.buildersrefuge.utilities.util.ColorGUI;
import com.buildersrefuge.utilities.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class ColorInventoryListener implements Listener {
    public Main plugin;

    public ColorInventoryListener(Main main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
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
            try {
                slot = e.getRawSlot();
            } catch (Exception exc) {
                return;
            }
            if (typeEnum.equals(InventoryTypeEnum.COLOR)) {
                Items i = new Items();
                ColorGUI gui = new ColorGUI();
                Random ra = new Random();
                String lore = "&7__&7Left click to increase__&7Right click to decrease__&7Shift click to change by 5";
                e.setCancelled(true);
                if (slot == 10 || slot == 19 || slot == 28 || slot == 37) {
                    float multiplier = 255f / 20f;
                    int r = (int) ((float) e.getClickedInventory().getItem(31).getAmount() * multiplier);
                    int g = (int) ((float) e.getClickedInventory().getItem(32).getAmount() * multiplier);
                    int b = (int) ((float) e.getClickedInventory().getItem(33).getAmount() * multiplier);
                    p.getInventory().addItem(i.color(i.create(e.getCurrentItem().getType(), (short) 0, 1, "", ""), r, g, b));
                } else if (slot == 31) { //RED
                    if (e.getClick().equals(ClickType.LEFT) && e.getClickedInventory().getItem(31).getAmount() < 20) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                e.getClickedInventory().getItem(31).getAmount() + 1, "&cRed", lore));
                    }
                    if (e.getClick().equals(ClickType.RIGHT) && e.getClickedInventory().getItem(31).getAmount() > 1) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                e.getClickedInventory().getItem(31).getAmount() - 1, "&cRed", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_LEFT) && e.getClickedInventory().getItem(31).getAmount() < 16) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                e.getClickedInventory().getItem(31).getAmount() + 5, "&cRed", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                20, "&cRed", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_RIGHT) && e.getClickedInventory().getItem(31).getAmount() > 5) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                e.getClickedInventory().getItem(31).getAmount() - 5, "&cRed", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                1, "&cRed", lore));
                    }
                    gui.updateInv(e.getClickedInventory());
                } else if (slot == 32) { //GREEN
                    if (e.getClick().equals(ClickType.LEFT) && e.getClickedInventory().getItem(32).getAmount() < 20) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                e.getClickedInventory().getItem(32).getAmount() + 1, "&aGreen", lore));
                    }
                    if (e.getClick().equals(ClickType.RIGHT) && e.getClickedInventory().getItem(32).getAmount() > 1) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                e.getClickedInventory().getItem(32).getAmount() - 1, "&aGreen", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_LEFT) && e.getClickedInventory().getItem(32).getAmount() < 16) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                e.getClickedInventory().getItem(32).getAmount() + 5, "&aGreen", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                20, "&aGreen", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_RIGHT) && e.getClickedInventory().getItem(32).getAmount() > 5) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                e.getClickedInventory().getItem(32).getAmount() - 5, "&aGreen", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                1, "&aGreen", lore));
                    }
                    gui.updateInv(e.getClickedInventory());
                } else if (slot == 33) { //BLUE
                    if (e.getClick().equals(ClickType.LEFT) && e.getClickedInventory().getItem(33).getAmount() < 20) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                e.getClickedInventory().getItem(33).getAmount() + 1, "&bBlue", lore));
                    }
                    if (e.getClick().equals(ClickType.RIGHT) && e.getClickedInventory().getItem(33).getAmount() > 1) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                e.getClickedInventory().getItem(33).getAmount() - 1, "&bBlue", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_LEFT) && e.getClickedInventory().getItem(33).getAmount() < 16) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                e.getClickedInventory().getItem(33).getAmount() + 5, "&bBlue", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                20, "&bBlue", lore));
                    }
                    if (e.getClick().equals(ClickType.SHIFT_RIGHT) && e.getClickedInventory().getItem(33).getAmount() > 5) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                e.getClickedInventory().getItem(33).getAmount() - 5, "&bBlue", lore));
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                1, "&bBlue", lore));
                    }
                    gui.updateInv(e.getClickedInventory());
                } else if (slot == 22) { //RANDOM RED
                    e.getClickedInventory().setItem(31, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                            ra.nextInt(20) + 1, "&cRed", lore));
                    gui.updateInv(e.getClickedInventory());
                } else if (slot == 23) { //RANDOM GREEN
                    e.getClickedInventory().setItem(32, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                            ra.nextInt(20) + 1, "&aGreen", lore));
                    gui.updateInv(e.getClickedInventory());
                } else if (slot == 24) { //RANDOM BLUE
                    e.getClickedInventory().setItem(33, i.createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                            ra.nextInt(20) + 1, "&bBlue", lore));
                    gui.updateInv(e.getClickedInventory());
                }
            }
        }
    }
}

