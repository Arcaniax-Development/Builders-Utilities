package net.arcaniax.buildersutilities.menus;

import net.arcaniax.buildersutilities.NoClipManager;
import net.arcaniax.buildersutilities.listeners.BlockBreakListener;
import net.arcaniax.buildersutilities.listeners.IronTrapdoorListener;
import net.arcaniax.buildersutilities.listeners.PlayerMoveListener;
import net.arcaniax.buildersutilities.listeners.TerracottaInteractListener;
import net.arcaniax.buildersutilities.menus.inv.ClickableItem;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryContents;
import net.arcaniax.buildersutilities.menus.inv.content.InventoryProvider;
import net.arcaniax.buildersutilities.utils.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilitiesMenuProvider implements InventoryProvider {
    private static final ItemStack ENABLED = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
    private static final ItemStack DISABLED = new ItemStack(Material.RED_STAINED_GLASS_PANE);
    private static final ItemStack NO_PERMISSION = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);

    private static final String ENABLED_LORE = "&a&lEnabled__&7__&7Click to toggle";
    private static final String DISABLED_LORE = "&c&lDisabled__&7__&7Click to toggle";

    @Override
    public void init(Player player, InventoryContents contents) {
        setIronTrapdoorItem(player, contents);
        setSlabItem(player, contents);
        setTerracottaItem(player, contents);
        setNightVisionItem(player, contents);
        setNoClipItem(player, contents);
        setFlyItem(player, contents);
    }

    private void setIronTrapdoorItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.irontrapdoor")) {
            setNoPermission(1, contents);
            contents.set(1, 1, ClickableItem.empty(
                    Items.create(Material.IRON_TRAPDOOR, "&6Iron Trapdoor Interaction", "&7&lNo Permission")));
            return;
        }

        if (IronTrapdoorListener.ironTrapdoorIds.contains(player.getUniqueId())) {
            setEnabledGlassPanes(1, true, contents);
            contents.set(1, 1, ClickableItem.of(Items.create(Material.IRON_TRAPDOOR,
                    "&6Iron Trapdoor Interaction", ENABLED_LORE),
                    inventoryClickEvent -> {
                        IronTrapdoorListener.ironTrapdoorIds.remove(player.getUniqueId());
                        setIronTrapdoorItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(1, false, contents);
            contents.set(1, 1, ClickableItem.of(Items.create(Material.IRON_TRAPDOOR,
                    "&6Iron Trapdoor Interaction", DISABLED_LORE),
                    inventoryClickEvent -> {
                        IronTrapdoorListener.ironTrapdoorIds.add(player.getUniqueId());
                        setIronTrapdoorItem(player, contents);
                    }));
        }
    }

    private void setSlabItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.slabs")) {
            setNoPermission(2, contents);
            contents.set(1, 2, ClickableItem.empty(
                    Items.create(Material.STONE_SLAB, "&6Custom Slab Breaking", "&7&lNo Permission")));
            return;
        }

        if (BlockBreakListener.slabIds.contains(player.getUniqueId())) {
            setEnabledGlassPanes(2, true, contents);
            contents.set(1, 2, ClickableItem.of(Items.create(Material.STONE_SLAB,
                    "&6Custom Slab Breaking", ENABLED_LORE),
                    inventoryClickEvent -> {
                        BlockBreakListener.slabIds.remove(player.getUniqueId());
                        setSlabItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(2, false, contents);
            contents.set(1, 2, ClickableItem.of(Items.create(Material.STONE_SLAB,
                    "&6Custom Slab Breaking", DISABLED_LORE),
                    inventoryClickEvent -> {
                        BlockBreakListener.slabIds.add(player.getUniqueId());
                        setSlabItem(player, contents);
                    }));
        }
    }

    private void setTerracottaItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.terracotta")) {
            setNoPermission(3, contents);
            contents.set(1, 3, ClickableItem.empty(
                    Items.create(Material.ORANGE_GLAZED_TERRACOTTA, "&6Glazed Terracotta Rotating", "&7&lNo Permission")));
            return;
        }

        if (TerracottaInteractListener.terracottaIds.contains(player.getUniqueId())) {
            setEnabledGlassPanes(3, true, contents);
            contents.set(1, 3, ClickableItem.of(Items.create(Material.ORANGE_GLAZED_TERRACOTTA,
                    "&6Glazed Terracotta Rotating", ENABLED_LORE),
                    inventoryClickEvent -> {
                        TerracottaInteractListener.terracottaIds.remove(player.getUniqueId());
                        setTerracottaItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(3, false, contents);
            contents.set(1, 3, ClickableItem.of(Items.create(Material.ORANGE_GLAZED_TERRACOTTA,
                    "&6Glazed Terracotta Rotating", DISABLED_LORE),
                    inventoryClickEvent -> {
                        TerracottaInteractListener.terracottaIds.add(player.getUniqueId());
                        setTerracottaItem(player, contents);
                    }));
        }

    }

    private void setNightVisionItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.nightvision")) {
            setNoPermission(5, contents);
            contents.set(1, 5, ClickableItem.empty(
                    Items.create(Material.ENDER_EYE, "&6Night Vision", "&7&lNo Permission")));
            return;
        }

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            setEnabledGlassPanes(5, true, contents);
            contents.set(1, 5, ClickableItem.of(Items.create(Material.ENDER_EYE,
                    "&6Night Vision", ENABLED_LORE),
                    inventoryClickEvent -> {
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        setNightVisionItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(5, false, contents);
            contents.set(1, 5, ClickableItem.of(Items.create(Material.ENDER_EYE,
                    "&6Night Vision", DISABLED_LORE),
                    inventoryClickEvent -> {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
                        setNightVisionItem(player, contents);
                    }));
        }
    }

    private void setNoClipItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.noclip")) {
            setNoPermission(6, contents);
            contents.set(1, 6, ClickableItem.empty(
                    Items.create(Material.COMPASS, "&6No Clip", "&7&lNo Permission")));
            return;
        }

        if (NoClipManager.noClipPlayerIds.contains(player.getUniqueId())) {
            setEnabledGlassPanes(6, true, contents);
            contents.set(1, 6, ClickableItem.of(Items.create(Material.COMPASS,
                    "&6No Clip", ENABLED_LORE),
                    inventoryClickEvent -> {
                        NoClipManager.noClipPlayerIds.remove(player.getUniqueId());
                        setNoClipItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(6, false, contents);
            contents.set(1, 6, ClickableItem.of(Items.create(Material.COMPASS,
                    "&6No Clip", DISABLED_LORE),
                    inventoryClickEvent -> {
                        NoClipManager.noClipPlayerIds.add(player.getUniqueId());
                        setNoClipItem(player, contents);
                    }));
        }
    }

    private void setFlyItem(Player player, InventoryContents contents) {
        if (!player.hasPermission("builders.util.advancedfly")) {
            setNoPermission(7, contents);
            contents.set(1, 7, ClickableItem.empty(
                    Items.create(Material.FEATHER, "&6Advanced Fly", "&7&lNo Permission")));
            return;
        }

        if (PlayerMoveListener.enabledPlayers.contains(player.getUniqueId())) {
            setEnabledGlassPanes(7, true, contents);
            contents.set(1, 7, ClickableItem.of(Items.create(Material.FEATHER,
                    "&6Advanced Fly", ENABLED_LORE),
                    inventoryClickEvent -> {
                        PlayerMoveListener.enabledPlayers.remove(player.getUniqueId());
                        setFlyItem(player, contents);
                    }));
        } else {
            setEnabledGlassPanes(7, false, contents);
            contents.set(1, 7, ClickableItem.of(Items.create(Material.FEATHER,
                    "&6Advanced Fly", DISABLED_LORE),
                    inventoryClickEvent -> {
                        PlayerMoveListener.enabledPlayers.add(player.getUniqueId());
                        setFlyItem(player, contents);
                    }));
        }
    }

    /**
     * Sets the glass panes above and below the center row to
     * show if a feature is enabled or disabled.
     * https://i.imgur.com/ETI22Py.png
     *
     * @param col     the column to set the glass panes in
     * @param enabled true if green panes, false if red panes
     */
    private void setEnabledGlassPanes(int col, boolean enabled, InventoryContents contents) {
        contents.set(0, col, ClickableItem.empty(enabled ? ENABLED : DISABLED));
        contents.set(2, col, ClickableItem.empty(enabled ? ENABLED : DISABLED));
    }

    private void setNoPermission(int col, InventoryContents contents) {
        contents.set(0, col, ClickableItem.empty(NO_PERMISSION));
        contents.set(2, col, ClickableItem.empty(NO_PERMISSION));
    }
}
