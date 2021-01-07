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
package net.arcaniax.buildersutilities;

import net.arcaniax.buildersutilities.utils.CustomConfig;

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
    public static boolean sendDebugMessages;

    private final CustomConfig customConfig;

    public Settings(CustomConfig customConfig) {
        this.customConfig = customConfig;
        reloadSettings();
    }

    public void reloadSettings() {
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
        sendDebugMessages = customConfig.get("send-debug-messages", Boolean.class, false);
    }
}
