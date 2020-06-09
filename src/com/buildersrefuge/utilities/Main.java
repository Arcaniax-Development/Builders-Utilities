package com.buildersrefuge.utilities;

import com.buildersrefuge.utilities.cmd.BannerHandler;
import com.buildersrefuge.utilities.cmd.ColorHandler;
import com.buildersrefuge.utilities.cmd.CommandHandler;
import com.buildersrefuge.utilities.cmd.SecretBlockHandler;
import com.buildersrefuge.utilities.listeners.*;
import com.buildersrefuge.utilities.object.Metrics;
import com.buildersrefuge.utilities.object.NoClipManager;
import com.buildersrefuge.utilities.util.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    public static Main main;
    public static NmsManager nmsManager;
    public static List<String> ironTrapdoorNames;
    public static List<String> slabNames;
    public static List<String> terracottaNames;

    public void onEnable() {
        main = this;
        nmsManager = new NmsManager();

        Metrics metrics = new Metrics(this);

        PluginManager pm = getServer().getPluginManager();
        CommandHandler commandHandler = new CommandHandler(this);

        ironTrapdoorNames = new ArrayList<>();
        slabNames = new ArrayList<>();
        terracottaNames = new ArrayList<>();

        this.saveDefaultConfig();
        new NoClipManager(this);

        pm.registerEvents(new BannerInventoryListener(this), this);
        pm.registerEvents(new ColorInventoryListener(this), this);
        pm.registerEvents(new SecretBlocksInventoryListener(this), this);
        pm.registerEvents(new ToggleInventoryListener(this), this);
        pm.registerEvents(new PlayerMoveListener(this), this);
        pm.registerEvents(this, this);

        getCommand("banner").setExecutor(new BannerHandler(this));
        getCommand("armorcolor").setExecutor(new ColorHandler(this));
        getCommand("blocks").setExecutor(new SecretBlockHandler(this));
        getCommand("n").setExecutor(commandHandler);
        getCommand("nc").setExecutor(commandHandler);
        getCommand("/1").setExecutor(commandHandler);
        getCommand("/2").setExecutor(commandHandler);
        getCommand("/cuboid").setExecutor(commandHandler);
        getCommand("/convex").setExecutor(commandHandler);
        getCommand("/s").setExecutor(commandHandler);
        getCommand("/r").setExecutor(commandHandler);
        getCommand("/f").setExecutor(commandHandler);
        getCommand("/pa").setExecutor(commandHandler);
        getCommand("/c").setExecutor(commandHandler);
        getCommand("ws").setExecutor(commandHandler);
        getCommand("fs").setExecutor(commandHandler);
        getCommand("af").setExecutor(commandHandler);
        getCommand("advfly").setExecutor(commandHandler);
        getCommand("/derot").setExecutor(commandHandler);
        getCommand("/scale").setExecutor(commandHandler);
        getCommand("/twist").setExecutor(commandHandler);
        getCommand("butil").setExecutor(commandHandler);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (this.getConfig().getBoolean("disable-weather-changes")) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        try {
            if (e.getChangedType().name().toLowerCase().contains("fence") ||
                    e.getChangedType().name().toLowerCase().contains("pane") ||
                    e.getChangedType().name().toLowerCase().contains("wall") ||
                    e.getChangedType().name().toLowerCase().contains("bar")
            ){return;}
        } catch (Exception ex){return;}
        if (!this.getConfig().getBoolean("disable-redstone")) {
            if (e.getChangedType().name().toLowerCase().contains("redstone") ||
                    e.getChangedType().name().toLowerCase().contains("air") ||
                    e.getChangedType().name().toLowerCase().contains("daylight") ||
                    e.getChangedType().name().toLowerCase().contains("diode") ||
                    e.getChangedType().name().toLowerCase().contains("note") ||
                    e.getChangedType().name().toLowerCase().contains("lever") ||
                    e.getChangedType().name().toLowerCase().contains("button") ||
                    e.getChangedType().name().toLowerCase().contains("command") ||
                    e.getChangedType().name().toLowerCase().contains("tripwire") ||
                    e.getChangedType().name().toLowerCase().contains("plate") ||
                    e.getChangedType().name().toLowerCase().contains("string") ||
                    e.getChangedType().name().toLowerCase().contains("piston") ||
                    e.getChangedType().name().toLowerCase().contains("observer")) {
                if (!e.getBlock().getType().name().contains("air")) {
                    return;
                }
            }
        }
        if (!e.getChangedType().hasGravity()) {
            if (this.getConfig().getBoolean("disable-physics")) {
                e.setCancelled(true);
            }
        } else {
            if (this.getConfig().getBoolean("disable-gravity-physics")) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDragonEggTP(PlayerInteractEvent e) {
        if (e.isCancelled()){
            return;
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.DRAGON_EGG) && this.getConfig().getBoolean("prevent-dragon-egg-teleport")) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e)
    {
        if (e.isCancelled()){
            return;
        }
        if (slabNames.contains(e.getPlayer().getName())){
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        Material type = e.getPlayer().getInventory().getItemInMainHand().getType();
        if (type.toString().toLowerCase().contains("slab")) {
            if (e.isCancelled()) {
                return;
            }
            if (e.getBlock().getType().toString().toLowerCase().contains("slab")) {
                if (isTop(e.getPlayer(), e.getBlock())) {
                    Slab blockdata = (Slab) e.getBlock().getBlockData();
                    if (blockdata.getType().equals(Slab.Type.DOUBLE)) {
                        blockdata.setType(Slab.Type.BOTTOM);
                        e.getBlock().setBlockData(blockdata, true);
                        e.setCancelled(true);
                    }
                } else {
                    Slab blockdata = (Slab) e.getBlock().getBlockData();
                    if (blockdata.getType().equals(Slab.Type.DOUBLE)) {
                        blockdata.setType(Slab.Type.TOP);
                        e.getBlock().setBlockData(blockdata, true);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isTop(Player p, Block b) {
        Location start = p.getEyeLocation().clone();
        while ((!start.getBlock().equals(b)) && start.distance(p.getEyeLocation()) < 6) {
            start.add(p.getLocation().getDirection().multiply(0.05));
        }
        return start.getY() % 1 > 0.5;
    }


    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (this.getConfig().getBoolean("disable-explosions")) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled()){
            return;
        }
        if (this.getConfig().getBoolean("disable-soil-trample")) {
            if (e.getAction() == Action.PHYSICAL) {
                Block block = e.getClickedBlock();
                if (block == null) return;
                if (block.getType() == Material.FARMLAND) {
                    e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                    e.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent e) {
        if (this.getConfig().getBoolean("disable-leaves-decay")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        NoClipManager.noClipPlayerNames.remove(p.getName());
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if (Main.nmsManager.isAtLeastVersion(1, 9 ,0)){
            if (this.getConfig().getBoolean("fix-attackspeed")) {
                AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                attribute.setBaseValue(1024.0D);
                e.getPlayer().saveData();
            } else {
                AttributeInstance attribute = e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                if (attribute.getBaseValue() == 1024.0D) {
                    attribute.setBaseValue(attribute.getDefaultValue());
                    e.getPlayer().saveData();
                }
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getCause() == null) {
            return;
        }
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (!event.getPlayer().hasPermission("builders.util.tpgm3")) {
                event.setCancelled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void GlazedTerracottaInteract(final PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (terracottaNames.contains(e.getPlayer().getName())) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!e.getClickedBlock().getType().name().contains("GLAZED")) {
            return;
        }
        if (!e.getPlayer().isSneaking()) {
            return;
        }
        Material type = e.getPlayer().getInventory().getItemInHand().getType();
        if (!(type.equals(Material.AIR))) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Block b = e.getClickedBlock();
            Directional directional = (Directional) b.getBlockData();
            if (directional.getFacing().equals(BlockFace.NORTH)){
                directional.setFacing(BlockFace.EAST);
            }
            else if (directional.getFacing().equals(BlockFace.EAST)){
                directional.setFacing(BlockFace.SOUTH);
            }
            else if (directional.getFacing().equals(BlockFace.SOUTH)){
                directional.setFacing(BlockFace.WEST);
            }
            else if (directional.getFacing().equals(BlockFace.WEST)){
                directional.setFacing(BlockFace.NORTH);
            }
            b.setBlockData(directional);
        }, 0L);
        e.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void ironTrapDoorInteract(final PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (ironTrapdoorNames.contains(e.getPlayer().getName())) {
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
            return;
        }
        if (Main.nmsManager.isAtLeastVersion(1, 9 ,0)){
            if (!e.getHand().equals(EquipmentSlot.HAND)) {
                return;
            }
        }
        if (e.getPlayer().isSneaking()) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Block b = e.getClickedBlock();
                TrapDoor trapDoor = (TrapDoor) b.getBlockData();
                if (trapDoor.isOpen()){
                    trapDoor.setOpen(false);
                }
                else{
                    trapDoor.setOpen(true);
                }
                b.setBlockData(trapDoor);
            }
        }, 0L);
        e.setCancelled(true);
    }
}
