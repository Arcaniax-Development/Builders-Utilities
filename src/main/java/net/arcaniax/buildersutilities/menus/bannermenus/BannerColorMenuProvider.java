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

import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BannerColorMenuProvider implements InventoryProvider {

    private static final ItemStack grayPane = Items
            .create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, (short) 0, 1, "&7", "");
    private static final ItemStack head = Items.createHead(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk3OTU1NDYyZTRlNTc2NjY0NDk5YWM0YTFjNTcyZjYxNDNmMTlhZDJkNjE5NDc3NjE5OGY4ZDEzNmZkYjIifX19",
            1, "&7Click to randomise", "");

    @Override
    public void init(Player player, InventoryContents contents) {
//        for (int x = 0; x < 54; x++) {
//            contents.set(x / 9, x % 9, grayPane);
//        }
//        inv1.setItem(1, );
//        if (first) {
//            inv1.setItem(4, bU.createBanner("&aClick to get the banner", 1, DyeColor.valueOf(clicked.getType().toString().replace("_BANNER", "")), ""));
//        } else {
//            inv1.setItem(4, bU.addPattern(inv.getItem(4), new Pattern(bU.getColorFromBanner(clicked), bU.getPatternType(clicked))));
//        }
//        inv1.setItem(7, i.create(Material.BARRIER, (short) 0, 1, "&cClick to close", ""));
//        for (int x = 19; x < 27; x++) {
//            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 19), 1, "&3" + bU.getAllColors().get(x - 19).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
//        }
//        for (int x = 28; x < 36; x++) {
//            inv1.setItem(x, i.create(Material.LEGACY_INK_SACK, (short) (x - 20), 1, "&3" + bU.getAllColors().get(x - 20).toString().toLowerCase().replace("_", " "), "&7__&7click to select"));
//        }
    }
}
