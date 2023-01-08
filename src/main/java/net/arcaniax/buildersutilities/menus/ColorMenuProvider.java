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
package net.arcaniax.buildersutilities.menus;

import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ColorMenuProvider implements InventoryProvider {

    private static final float MULTIPLIER = 255f / 20f;
    private static final String LORE = "&7__&7Left click to increase__&7Right click to decrease__&7Shift click to change by 5";

    @Override
    public void init(Player player, InventoryContents contents) {
        setRedItem(player, contents, 10);
        setRedRandomItem(player, contents);
        setGreenItem(player, contents, 10);
        setGreenRandomItem(player, contents);
        setBlueItem(player, contents, 10);
        setBlueRandomItem(player, contents);

        setLeatherItems(player, contents);
    }

    private void setLeatherItems(Player player, InventoryContents contents) {
        int r;
        if (contents.get(3, 4).get().getItem().getType().equals(Material.RED_STAINED_GLASS)) {
            r = 0;
        } else {
            r = (int) (contents.get(3, 4).get().getItem().getAmount() * MULTIPLIER);
        }

        int g;
        if (contents.get(3, 5).get().getItem().getType().equals(Material.GREEN_STAINED_GLASS)) {
            g = 0;
        } else {
            g = (int) (contents.get(3, 5).get().getItem().getAmount() * MULTIPLIER);
        }

        int b;
        if (contents.get(3, 6).get().getItem().getType().equals(Material.BLUE_STAINED_GLASS)) {
            b = 0;
        } else {
            b = (int) (contents.get(3, 6).get().getItem().getAmount() * MULTIPLIER);
        }

        contents.set(1, 1, ClickableItem.of(
                Items.color(new ItemStack(Material.LEATHER_HELMET), r, g, b),
                inventoryClickEvent -> player.getInventory().addItem(Items.color(new ItemStack(Material.LEATHER_HELMET), r, g, b))
        ));
        contents.set(2, 1, ClickableItem.of(
                Items.color(new ItemStack(Material.LEATHER_CHESTPLATE), r, g, b),
                inventoryClickEvent -> player
                        .getInventory()
                        .addItem(Items.color(new ItemStack(Material.LEATHER_CHESTPLATE), r, g, b))
        ));
        contents.set(3, 1, ClickableItem.of(
                Items.color(new ItemStack(Material.LEATHER_LEGGINGS), r, g, b),
                inventoryClickEvent -> player
                        .getInventory()
                        .addItem(Items.color(new ItemStack(Material.LEATHER_LEGGINGS), r, g, b))
        ));
        contents.set(4, 1, ClickableItem.of(
                Items.color(new ItemStack(Material.LEATHER_BOOTS), r, g, b),
                inventoryClickEvent -> player.getInventory().addItem(Items.color(new ItemStack(Material.LEATHER_BOOTS), r, g, b))
        ));
    }

    private void setRedItem(Player player, InventoryContents contents, int amount) {
        contents.set(3, 4, ClickableItem.of(
                amount == 0 ? Items.create(Material.RED_STAINED_GLASS, "&cRed", LORE) :
                        Items.createHead(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2Y0NmMzMWQ2ZWU2ZWE2MTlmNzJlNzg1MjMyY2IwNDhhYjI3MDQ2MmRiMGNiMTQ1NDUxNDQzNjI1MWMxYSJ9fX0=",
                                amount,
                                "&cRed",
                                LORE
                        ), inventoryClickEvent -> {
                    if (inventoryClickEvent.isShiftClick()) {
                        setRedItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 5, 20) : Math.max(amount - 5, 0)
                        );
                    } else {
                        setRedItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 1, 20) : Math.max(amount - 1, 0)
                        );
                    }
                    setLeatherItems(player, contents);
                }));
    }

    private void setRedRandomItem(Player player, InventoryContents contents) {
        contents.set(2, 4, ClickableItem.of(Items.createHead(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEzMWRlOGU5NTFmZGQ3YjlhM2QyMzlkN2NjM2FhM2U4NjU1YTMzNmI5OTliOWVkYmI0ZmIzMjljYmQ4NyJ9fX0=",
                1,
                "&cClick to randomize",
                ""
        ), inventoryClickEvent -> {
            setRedItem(player, contents, ThreadLocalRandom.current().nextInt(0, 21));
            setLeatherItems(player, contents);
        }));
    }

    private void setGreenItem(Player player, InventoryContents contents, int amount) {
        contents.set(3, 5, ClickableItem.of(
                amount == 0 ? Items.create(Material.GREEN_STAINED_GLASS, "&aGreen", LORE) :
                        Items.createHead(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZmNjlmN2I3NTM4YjQxZGMzNDM5ZjM2NThhYmJkNTlmYWNjYTM2NmYxOTBiY2YxZDZkMGEwMjZjOGY5NiJ9fX0=",
                                amount,
                                "&aGreen",
                                LORE
                        ), inventoryClickEvent -> {
                    if (inventoryClickEvent.isShiftClick()) {
                        setGreenItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 5, 20) : Math.max(amount - 5, 0)
                        );
                    } else {
                        setGreenItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 1, 20) : Math.max(amount - 1, 0)
                        );
                    }
                    setLeatherItems(player, contents);
                }));
    }

    private void setGreenRandomItem(Player player, InventoryContents contents) {
        contents.set(2, 5, ClickableItem.of(Items.createHead(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMzY2VjNjg3NjlmZTljOTcxMjkxZWRiN2VmOTZhNGUzYjYwNDYyY2ZkNWZiNWJhYTFjYmIzYTcxNTEzZTdiIn19fQ==",
                1,
                "&aClick to randomize",
                ""
        ), inventoryClickEvent -> {
            setGreenItem(player, contents, ThreadLocalRandom.current().nextInt(0, 21));
            setLeatherItems(player, contents);
        }));
    }

    private void setBlueItem(Player player, InventoryContents contents, int amount) {
        contents.set(3, 6, ClickableItem.of(
                amount == 0 ? Items.create(Material.BLUE_STAINED_GLASS, "&bBlue", LORE) :
                        Items.createHead(
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgzOWUzODFkOWZlZGFiNmY4YjU5Mzk2YTI3NjQyMzhkY2ViMmY3ZWVhODU2ZGM2ZmM0NDc2N2RhMzgyZjEifX19",
                                amount,
                                "&bBlue",
                                LORE
                        ), inventoryClickEvent -> {
                    if (inventoryClickEvent.isShiftClick()) {
                        setBlueItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 5, 20) : Math.max(amount - 5, 0)
                        );
                    } else {
                        setBlueItem(player, contents,
                                inventoryClickEvent.isLeftClick() ? Math.min(amount + 1, 20) : Math.max(amount - 1, 0)
                        );
                    }
                    setLeatherItems(player, contents);
                }));
    }

    private void setBlueRandomItem(Player player, InventoryContents contents) {
        contents.set(2, 6, ClickableItem.of(Items.createHead(
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjMyOGYzNzhmMjhhOTg3MjIyNmY1Y2UwNGQ2ZTFkZmExMTE2MTg1ODdmNDhkZmExZmU4MmQwNDMyMTZhNWNmIn19fQ==",
                1,
                "&bClick to randomize",
                ""
        ), inventoryClickEvent -> {
            setBlueItem(player, contents, ThreadLocalRandom.current().nextInt(0, 21));
            setLeatherItems(player, contents);
        }));
    }

}
