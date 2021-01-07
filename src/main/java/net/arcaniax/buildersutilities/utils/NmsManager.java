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
package net.arcaniax.buildersutilities.utils;

import net.arcaniax.buildersutilities.BuildersUtilities;

public class NmsManager {

    private final String version;

    public NmsManager() {
        String packageString = BuildersUtilities.getInstance().getServer().getClass().getPackage().getName();
        version = packageString.substring(packageString.lastIndexOf('.') + 1);
    }

    public boolean isVersion(String v) {
        return version.equalsIgnoreCase(v);
    }

    public boolean isVersion(int gameV, int releaseV, int subReleaseV) {
        return version.equalsIgnoreCase("v" + gameV + "_" + releaseV + "_R" + subReleaseV);
    }

    public boolean isAtLeastVersion(int gameV, int releaseV, int subReleaseV) {
        String[] split = version.split("_");
        int game = Integer.parseInt(split[0].toLowerCase().replace("v", ""));
        int release = Integer.parseInt(split[1]);
        int subRelease = Integer.parseInt(split[2].toLowerCase().replace("r", ""));

        if (game > gameV) {
            return true;
        } else if (game < gameV) {
            return false;
        } else {
            if (release > releaseV) {
                return true;
            } else if (release < releaseV) {
                return false;
            } else {
                return subRelease >= subReleaseV;
            }
        }
    }
}
