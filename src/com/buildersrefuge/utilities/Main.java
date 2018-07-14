package com.buildersrefuge.utilities;

import com.buildersrefuge.utilities.cmd.BannerHandler;
import com.buildersrefuge.utilities.cmd.ColorHandler;
import com.buildersrefuge.utilities.cmd.CommandHandler;
import com.buildersrefuge.utilities.cmd.SecretBlockHandler;
import com.buildersrefuge.utilities.listeners.*;
import com.buildersrefuge.utilities.object.NoClipManager;
import com.massivestats.MassiveStats;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
    public static String version;
    public static List<String> ironTrapdoorNames;
    public static List<String> slabNames;
    public static List<String> terracottaNames;
    public static Main main;

    public void onEnable() {
        String a = this.getServer().getClass().getPackage().getName();
        version = a.substring(a.lastIndexOf('.') + 1);

        PluginManager pm = getServer().getPluginManager();
        CommandHandler commandHandler = new CommandHandler(this);

        ironTrapdoorNames = new ArrayList<>();
        slabNames = new ArrayList<>();
        terracottaNames = new ArrayList<>();

        main = this;

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

        new MassiveStats(this);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (this.getConfig().getBoolean("disable-weather-changes")) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
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
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.DRAGON_EGG) && this.getConfig().getBoolean("prevent-dragon-egg-teleport")) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(PlayerInteractEvent e)
    {
        if (slabNames.contains(e.getPlayer().getName())){
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (!e.getAction().equals(Action.LEFT_CLICK_BLOCK)){return;}
        Material type = e.getPlayer().getInventory().getItemInHand().getType();
        if (!version.contains("v1_8")){
            if (!(type.equals(Material.STEP)||type.equals(Material.WOOD_STEP)||type.equals(Material.STONE_SLAB2)||type.equals(Material.PURPUR_SLAB))){
                return;
            }
        }
        else{
            if (!(type.equals(Material.STEP)||type.equals(Material.WOOD_STEP)||type.equals(Material.STONE_SLAB2))){
                return;
            }
        }
        if (e.isCancelled()){return;}
        if (e.getClickedBlock().getType().equals(Material.DOUBLE_STEP)){
            if (e.getClickedBlock().getData()<=7){
                e.setCancelled(true);
                byte data = e.getClickedBlock().getData();
                if (isTop(e.getPlayer(), e.getClickedBlock())){
                    e.getClickedBlock().setType(Material.STEP);
                    e.getClickedBlock().setData(data);
                }
                else{
                    e.getClickedBlock().setType(Material.STEP);
                    e.getClickedBlock().setData((byte) (data+8));
                }
            }
        }
        if (e.getClickedBlock().getType().equals(Material.WOOD_DOUBLE_STEP)){
            if (e.getClickedBlock().getData()<=7){
                e.setCancelled(true);
                byte data = e.getClickedBlock().getData();
                if (isTop(e.getPlayer(), e.getClickedBlock())){
                    e.getClickedBlock().setType(Material.WOOD_STEP);
                    e.getClickedBlock().setData(data);
                }
                else{
                    e.getClickedBlock().setType(Material.WOOD_STEP);
                    e.getClickedBlock().setData((byte) (data+8));
                }
            }
        }
        if (e.getClickedBlock().getType().equals(Material.DOUBLE_STONE_SLAB2)){
            if (e.getClickedBlock().getData()<=7){
                e.setCancelled(true);
                byte data = e.getClickedBlock().getData();
                if (isTop(e.getPlayer(), e.getClickedBlock())){
                    e.getClickedBlock().setType(Material.STONE_SLAB2);
                    e.getClickedBlock().setData(data);
                }
                else{
                    e.getClickedBlock().setType(Material.STONE_SLAB2);
                    e.getClickedBlock().setData((byte) (data+8));
                }
            }
        }
        if (!version.contains("v1_8")){
            if (e.getClickedBlock().getType().equals(Material.PURPUR_DOUBLE_SLAB)){
                if (e.getClickedBlock().getData()<=7){
                    e.setCancelled(true);
                    byte data = e.getClickedBlock().getData();
                    if (isTop(e.getPlayer(), e.getClickedBlock())){
                        e.getClickedBlock().setType(Material.PURPUR_SLAB);
                        e.getClickedBlock().setData(data);
                    }
                    else{
                        e.getClickedBlock().setType(Material.PURPUR_SLAB);
                        e.getClickedBlock().setData((byte) (data+8));
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
        if (this.getConfig().getBoolean("disable-soil-trample")) {
            if (e.getAction() == Action.PHYSICAL) {
                Block block = e.getClickedBlock();
                if (block == null) return;
                if (block.getType() == Material.SOIL) {
                    e.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                    e.setCancelled(true);
                    block.setTypeIdAndData(block.getType().getId(), block.getData(), true);
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
        if (!version.contains("v1_8")) {
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
        if (!version.contains("v1_12") && (e.getHand() == null)) {
            return;
        }
        if (e.getHand() == null) {
            return;
        }
        if (!e.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if (!terracottaNames.contains(e.getPlayer().getName())) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
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
            byte da = b.getData();
            byte data = (byte) (da + 1);
            if (!(da >= 0 && da < 4)) {
                data = 0;
            }
            b.setData(data, true);
        }, 0L);
        e.setCancelled(true);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void ironTrapDoorInteract(final PlayerInteractEvent e) {
        if (ironTrapdoorNames.contains(e.getPlayer().getName())) {
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!e.getClickedBlock().getType().equals(Material.IRON_TRAPDOOR)) {
            return;
        }
        if (!version.contains("v1_8")) {
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
                byte da = b.getData();
                byte data = 0;
                if (da >= 0 && da < 4) {
                    data = (byte) (da + 4);
                } else if (da >= 4 && da < 8) {
                    data = (byte) (da - 4);
                } else if (da >= 8 && da < 12) {
                    data = (byte) (da + 4);
                } else if (da >= 12 && da < 16) {
                    data = (byte) (da - 4);
                }
                b.setData(data, true);
            }
        }, 0L);
        e.setCancelled(true);
    }
}
