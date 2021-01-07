/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2021 Arcaniax
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface SlotIterator {

    Optional<ClickableItem> get();

    SlotIterator set(ClickableItem item);

    SlotIterator previous();

    SlotIterator next();

    SlotIterator blacklist(int row, int column);

    SlotIterator blacklist(SlotPos slotPos);

    int row();

    SlotIterator row(int row);

    int column();

    SlotIterator column(int column);

    boolean started();

    boolean ended();

    boolean doesAllowOverride();

    SlotIterator allowOverride(boolean override);

    enum Type {
        HORIZONTAL,
        VERTICAL
    }

    class Impl implements SlotIterator {

        private final InventoryContents contents;
        private final SmartInventory inv;

        private final Type type;
        private final Set<SlotPos> blacklisted = new HashSet<>();
        private boolean started = false;
        private boolean allowOverride = true;
        private int row, column;

        public Impl(InventoryContents contents, SmartInventory inv,
                    Type type, int startRow, int startColumn) {

            this.contents = contents;
            this.inv = inv;

            this.type = type;

            this.row = startRow;
            this.column = startColumn;
        }

        public Impl(InventoryContents contents, SmartInventory inv,
                    Type type) {

            this(contents, inv, type, 0, 0);
        }

        @Override
        public Optional<ClickableItem> get() {
            return contents.get(row, column);
        }

        @Override
        public SlotIterator set(ClickableItem item) {
            if (canPlace()) {
                contents.set(row, column, item);
            }

            return this;
        }

        @Override
        public SlotIterator previous() {
            if (row == 0 && column == 0) {
                this.started = true;
                return this;
            }

            do {
                if (!this.started) {
                    this.started = true;
                } else {
                    switch (type) {
                        case HORIZONTAL:
                            column--;

                            if (column == 0) {
                                column = inv.getColumns() - 1;
                                row--;
                            }
                            break;
                        case VERTICAL:
                            row--;

                            if (row == 0) {
                                row = inv.getRows() - 1;
                                column--;
                            }
                            break;
                    }
                }
            }
            while (!canPlace() && (row != 0 || column != 0));

            return this;
        }

        @Override
        public SlotIterator next() {
            if (ended()) {
                this.started = true;
                return this;
            }

            do {
                if (!this.started) {
                    this.started = true;
                } else {
                    switch (type) {
                        case HORIZONTAL:
                            column = ++column % inv.getColumns();

                            if (column == 0) {
                                row++;
                            }
                            break;
                        case VERTICAL:
                            row = ++row % inv.getRows();

                            if (row == 0) {
                                column++;
                            }
                            break;
                    }
                }
            }
            while (!canPlace() && !ended());

            return this;
        }

        @Override
        public SlotIterator blacklist(int row, int column) {
            this.blacklisted.add(SlotPos.of(row, column));
            return this;
        }

        @Override
        public SlotIterator blacklist(SlotPos slotPos) {
            return blacklist(slotPos.getRow(), slotPos.getColumn());
        }

        @Override
        public int row() {
            return row;
        }

        @Override
        public SlotIterator row(int row) {
            this.row = row;
            return this;
        }

        @Override
        public int column() {
            return column;
        }

        @Override
        public SlotIterator column(int column) {
            this.column = column;
            return this;
        }

        @Override
        public boolean started() {
            return this.started;
        }

        @Override
        public boolean ended() {
            return row == inv.getRows() - 1
                    && column == inv.getColumns() - 1;
        }

        @Override
        public boolean doesAllowOverride() {
            return allowOverride;
        }

        @Override
        public SlotIterator allowOverride(boolean override) {
            this.allowOverride = override;
            return this;
        }

        private boolean canPlace() {
            return !blacklisted.contains(SlotPos.of(row, column)) && (allowOverride || !this.get()
                    .isPresent());
        }

    }

}