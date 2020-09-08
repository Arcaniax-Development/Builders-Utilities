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

package net.arcaniax.buildersutilities.menus.inv.content;

import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.SmartInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface InventoryContents {

    SmartInventory inventory();

    Pagination pagination();

    Optional<SlotIterator> iterator(String id);

    SlotIterator newIterator(String id, SlotIterator.Type type, int startRow, int startColumn);

    SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn);

    SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos);

    SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos);

    ClickableItem[][] all();

    Optional<SlotPos> firstEmpty();

    Optional<ClickableItem> get(int row, int column);

    Optional<ClickableItem> get(SlotPos slotPos);

    InventoryContents set(int row, int column, ClickableItem item);

    InventoryContents set(SlotPos slotPos, ClickableItem item);

    InventoryContents add(ClickableItem item);

    InventoryContents fill(ClickableItem item);

    InventoryContents fillRow(int row, ClickableItem item);

    InventoryContents fillColumn(int column, ClickableItem item);

    InventoryContents fillBorders(ClickableItem item);

    InventoryContents fillRect(int fromRow, int fromColumn,
                               int toRow, int toColumn, ClickableItem item);

    InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item);

    <T> T property(String name);

    <T> T property(String name, T def);

    InventoryContents setProperty(String name, Object value);

    class Impl implements InventoryContents {

        private final SmartInventory inv;
        private final Player player;

        private final ClickableItem[][] contents;

        private final Pagination pagination = new Pagination.Impl();
        private final Map<String, SlotIterator> iterators = new HashMap<>();
        private final Map<String, Object> properties = new HashMap<>();

        public Impl(SmartInventory inv, Player player) {
            this.inv = inv;
            this.player = player;
            this.contents = new ClickableItem[inv.getRows()][inv.getColumns()];
        }

        @Override
        public SmartInventory inventory() {
            return inv;
        }

        @Override
        public Pagination pagination() {
            return pagination;
        }

        @Override
        public Optional<SlotIterator> iterator(String id) {
            return Optional.ofNullable(this.iterators.get(id));
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, int startRow,
                                        int startColumn) {
            SlotIterator iterator = new SlotIterator.Impl(this, inv,
                    type, startRow, startColumn);

            this.iterators.put(id, iterator);
            return iterator;
        }

        @Override
        public SlotIterator newIterator(String id, SlotIterator.Type type, SlotPos startPos) {
            return newIterator(id, type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, int startRow, int startColumn) {
            return new SlotIterator.Impl(this, inv, type, startRow, startColumn);
        }

        @Override
        public SlotIterator newIterator(SlotIterator.Type type, SlotPos startPos) {
            return newIterator(type, startPos.getRow(), startPos.getColumn());
        }

        @Override
        public ClickableItem[][] all() {
            return contents;
        }

        @Override
        public Optional<SlotPos> firstEmpty() {
            for (int row = 0; row < contents.length; row++) {
                for (int column = 0; column < contents[0].length; column++) {
                    if (!this.get(row, column).isPresent()) {
                        return Optional.of(new SlotPos(row, column));
                    }
                }
            }

            return Optional.empty();
        }

        @Override
        public Optional<ClickableItem> get(int row, int column) {
            if (row >= contents.length) {
                return Optional.empty();
            }
            if (column >= contents[row].length) {
                return Optional.empty();
            }

            return Optional.ofNullable(contents[row][column]);
        }

        @Override
        public Optional<ClickableItem> get(SlotPos slotPos) {
            return get(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public InventoryContents set(int row, int column, ClickableItem item) {
            if (row >= contents.length) {
                return this;
            }
            if (column >= contents[row].length) {
                return this;
            }

            contents[row][column] = item;
            update(row, column, item != null ? item.getItem() : null);
            return this;
        }

        @Override
        public InventoryContents set(SlotPos slotPos, ClickableItem item) {
            return set(slotPos.getRow(), slotPos.getColumn(), item);
        }

        @Override
        public InventoryContents add(ClickableItem item) {
            for (int row = 0; row < contents.length; row++) {
                for (int column = 0; column < contents[0].length; column++) {
                    if (contents[row][column] == null) {
                        set(row, column, item);
                        return this;
                    }
                }
            }

            return this;
        }

        @Override
        public InventoryContents fill(ClickableItem item) {
            for (int row = 0; row < contents.length; row++) {
                for (int column = 0; column < contents[row].length; column++) {
                    set(row, column, item);
                }
            }

            return this;
        }

        @Override
        public InventoryContents fillRow(int row, ClickableItem item) {
            if (row >= contents.length) {
                return this;
            }

            for (int column = 0; column < contents[row].length; column++) {
                set(row, column, item);
            }

            return this;
        }

        @Override
        public InventoryContents fillColumn(int column, ClickableItem item) {
            for (int row = 0; row < contents.length; row++) {
                set(row, column, item);
            }

            return this;
        }

        @Override
        public InventoryContents fillBorders(ClickableItem item) {
            fillRect(0, 0, inv.getRows() - 1, inv.getColumns() - 1, item);
            return this;
        }

        @Override
        public InventoryContents fillRect(int fromRow, int fromColumn, int toRow, int toColumn,
                                          ClickableItem item) {
            for (int row = fromRow; row <= toRow; row++) {
                for (int column = fromColumn; column <= toColumn; column++) {
                    if (row != fromRow && row != toRow && column != fromColumn && column != toColumn) {
                        continue;
                    }

                    set(row, column, item);
                }
            }

            return this;
        }

        @Override
        public InventoryContents fillRect(SlotPos fromPos, SlotPos toPos, ClickableItem item) {
            return fillRect(fromPos.getRow(), fromPos.getColumn(), toPos.getRow(), toPos.getColumn(),
                    item);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T property(String name) {
            return (T) properties.get(name);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T property(String name, T def) {
            return properties.containsKey(name) ? (T) properties.get(name) : def;
        }

        @Override
        public InventoryContents setProperty(String name, Object value) {
            properties.put(name, value);
            return this;
        }

        private void update(int row, int column, ItemStack item) {
            if (!inv.getManager().getOpenedPlayers(inv).contains(player)) {
                return;
            }

            Inventory topInventory = player.getOpenInventory().getTopInventory();
            topInventory.setItem(inv.getColumns() * row + column, item);
        }

    }

}