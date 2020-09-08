package net.arcaniax.buildersutilities.utils;

import net.arcaniax.buildersutilities.Main;

public class NmsManager {

    private final String version;

    public NmsManager() {
        String packageString = Main.getInstance().getServer().getClass().getPackage().getName();
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
