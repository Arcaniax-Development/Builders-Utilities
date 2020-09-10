/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2020 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.arcaniax.buildersutilities.menus.bannermenus;

import net.arcaniax.buildersutilities.menus.Menus;
import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.SmartInventory;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.menus.inv.content.SlotPos;
import net.arcaniax.buildersutilities.utils.BannerUtil;
import net.arcaniax.buildersutilities.utils.Items;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BannerMenuProvider implements InventoryProvider {

    private static final ItemStack grayPane = Items
            .create(Material.GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", "");
    private static final ItemStack randomizeHead = Items.createHead(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19",
            1, "&7Click to randomise", "");
    //    private static final ItemStack currentColor = BannerUtil.createBanner("&a", 1, DyeColor.WHITE, "");
    private static final ItemStack closeButton = Items
            .create(Material.BARRIER, (short) 0, 1, "&cClick to close", "");
    private static final ItemStack whiteBanner = Items
            .create(Material.WHITE_BANNER, (short) 0, 1, "&c", "");

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fill(ClickableItem.empty(grayPane));
        contents.set(0, 1, ClickableItem.of(randomizeHead, inventoryClickEvent -> selectRandomColor(player)));
        contents.set(0, 4, ClickableItem.empty(whiteBanner));
        contents.set(0, 7, ClickableItem.of(closeButton, inventoryClickEvent -> contents.inventory().close(player)));

        int row = 2;
        int column = 1;
        for (DyeColor color : BannerUtil.allColors){
            contents.set(row, column, ClickableItem.of(BannerUtil.createBanner("&3" + color.toString().toLowerCase().replace("_", " "), color, "&7__&7click to select"), inventoryClickEvent -> selectColor(player, color)));
            column++;
            if (column==9){
                column = 1;
                row++;
            }
        }

//        contents.set(0, 1, ClickableItem.of(randomizeHead, NULL));
//        contents.set(0, 4, ClickableItem.empty(currentColor));
//        contents.set(0, 7, ClickableItem.of(closeButton, inventoryClickEvent -> contents.inventory().close(player)));
//        for (int x = 19; x < 27; x++) {
//            ItemStack banner = BannerUtil.createBanner("&3" + BannerUtil.allColors.get(x - 19).toString().toLowerCase().replace("_", " "), 1, BannerUtil.allColors.get(x - 19), "&7__&7click to select");
//            contents.set(x / 9, x % 9, ClickableItem.of(banner, NULL));
//        }
//        for (int x = 28; x < 36; x++) {
//            ItemStack banner = BannerUtil.createBanner("&3" + BannerUtil.allColors.get(x - 28 + 8).toString().toLowerCase().replace("_", " "), 1, BannerUtil.allColors.get(x - 28 + 8), "&7__&7click to select");
//            contents.set(x / 9, x % 9, ClickableItem.of(banner, NULL));
//        }
    }

    private void selectRandomColor(Player player){
        Random random = new Random();
        DyeColor dyeColor = BannerUtil.allColors.get(random.nextInt(BannerUtil.allColors.size()-1));
        BannerUtil.currentBanner.put(player.getUniqueId(), BannerUtil.createBanner("&3" + dyeColor.toString().toLowerCase().replace("_", " "), dyeColor, "&7__&7click to select"));
        Menus.BANNER_MENU_COLOR.open(player);
    }

    private void selectColor(Player player, DyeColor dyeColor){
        BannerUtil.currentBanner.put(player.getUniqueId(), BannerUtil.createBanner("&3" + dyeColor.toString().toLowerCase().replace("_", " "), dyeColor, "&7__&7click to select"));
        Menus.BANNER_MENU_COLOR.open(player);
    }
}
