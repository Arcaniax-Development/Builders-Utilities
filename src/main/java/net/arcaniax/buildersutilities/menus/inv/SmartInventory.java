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
package net.arcaniax.buildersutilities.menus.inv;

import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.menus.inv.opener.InventoryOpener;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class SmartInventory {

    private final InventoryManager manager;
    private String id;
    private String title;
    private InventoryType type;
    private int rows, columns;
    private boolean closeable;
    private InventoryProvider provider;
    private SmartInventory parent;
    private List<InventoryListener<? extends Event>> listeners;

    private SmartInventory(InventoryManager manager) {
        this.manager = manager;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Inventory open(Player player) {
        return open(player, 0);
    }

    public Inventory open(Player player, int page) {
        Optional<SmartInventory> oldInv = this.manager.getInventory(player);

        oldInv.ifPresent(inv -> {
            inv.getListeners().stream()
                    .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                    .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                            .accept(new InventoryCloseEvent(player.getOpenInventory())));

            this.manager.setInventory(player, null);
        });

        InventoryContents contents = new InventoryContents.Impl(this, player);
        contents.pagination().page(page);

        this.manager.setContents(player, contents);
        this.provider.init(player, contents);

        InventoryOpener opener = this.manager.findOpener(type)
                .orElseThrow(() -> new IllegalStateException(
                        "No opener found for the inventory type " + type.name()));
        Inventory handle = opener.open(this, player);

        this.manager.setInventory(player, this);

        return handle;
    }

    @SuppressWarnings("unchecked")
    public void close(Player player) {
        listeners.stream()
                .filter(listener -> listener.getType() == InventoryCloseEvent.class)
                .forEach(listener -> ((InventoryListener<InventoryCloseEvent>) listener)
                        .accept(new InventoryCloseEvent(player.getOpenInventory())));

        this.manager.setInventory(player, null);
        player.closeInventory();

        this.manager.setContents(player, null);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public InventoryType getType() {
        return type;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isCloseable() {
        return closeable;
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }

    public InventoryProvider getProvider() {
        return provider;
    }

    public Optional<SmartInventory> getParent() {
        return Optional.ofNullable(parent);
    }

    public InventoryManager getManager() {
        return manager;
    }

    List<InventoryListener<? extends Event>> getListeners() {
        return listeners;
    }

    public static final class Builder {

        private final List<InventoryListener<? extends Event>> listeners = new ArrayList<>();
        private String id = "unknown";
        private String title = "";
        private InventoryType type = InventoryType.CHEST;
        private int rows = 6, columns = 9;
        private boolean closeable = true;
        private InventoryManager manager;
        private InventoryProvider provider;
        private SmartInventory parent;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type) {
            this.type = type;
            return this;
        }

        public Builder size(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            return this;
        }

        public Builder closeable(boolean closeable) {
            this.closeable = closeable;
            return this;
        }

        public Builder provider(InventoryProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder parent(SmartInventory parent) {
            this.parent = parent;
            return this;
        }

        public Builder listener(InventoryListener<? extends Event> listener) {
            this.listeners.add(listener);
            return this;
        }

        public Builder manager(InventoryManager manager) {
            this.manager = manager;
            return this;
        }

        public SmartInventory build() {
            if (this.provider == null) {
                throw new IllegalStateException(
                        "The provider of the SmartInventory.Builder must be set.");
            }

            InventoryManager manager = this.manager;

            if (manager == null) {
                throw new IllegalStateException(
                        "The manager of the SmartInventory.Builder must be set, "
                                + "or the SmartInvs should be loaded as a plugin.");
            }

            SmartInventory inv = new SmartInventory(manager);
            inv.id = this.id;
            inv.title = this.title;
            inv.type = this.type;
            inv.rows = this.rows;
            inv.columns = this.columns;
            inv.closeable = this.closeable;
            inv.provider = this.provider;
            inv.parent = this.parent;
            inv.listeners = this.listeners;

            return inv;
        }
    }

}