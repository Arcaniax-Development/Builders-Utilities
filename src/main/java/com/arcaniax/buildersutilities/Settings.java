package com.arcaniax.buildersutilities;

import com.arcaniax.buildersutilities.utils.CustomConfig;

public class Settings {
    public static boolean disableWeatherChanges;
    public static boolean disableRedstone;
    public static boolean disablePhysics;
    public static boolean disableGravityPhysics;
    public static boolean disableExplosions;
    public static boolean disableSoilTrample;
    public static boolean disableLeavesDecay;
    public static boolean fixAttackSpeed;
    public static boolean preventDragonEggTeleport;
    public static boolean sendErrorMessages;

    private final CustomConfig customConfig;

    public Settings(CustomConfig customConfig) {
        this.customConfig = customConfig;
        reloadSettings();
    }

    public void reloadSettings(){
        disableWeatherChanges = customConfig.get("disable-weather-changes", Boolean.class, true);
        disableRedstone = customConfig.get("disable-redstone", Boolean.class, false);
        disablePhysics = customConfig.get("disable-physics", Boolean.class, true);
        disableGravityPhysics = customConfig.get("disable-gravity-physics", Boolean.class, false);
        disableExplosions = customConfig.get("disable-explosions", Boolean.class, true);
        disableSoilTrample = customConfig.get("disable-soil-trample", Boolean.class, true);
        disableLeavesDecay = customConfig.get("disable-leaves-decay", Boolean.class, true);
        fixAttackSpeed = customConfig.get("fix-attackspeed", Boolean.class, false);
        preventDragonEggTeleport = customConfig.get("prevent-dragon-egg-teleport", Boolean.class, true);
        sendErrorMessages = customConfig.get("send-error-messages", Boolean.class, true);
    }
}
