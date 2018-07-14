package com.buildersrefuge.utilities.object;

import com.buildersrefuge.utilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NoClipManager {
    public static List<String> noClipPlayerNames;
    private Main main;

    public NoClipManager(Main main) {
        noClipPlayerNames = new ArrayList<>();
        this.main = main;
        everyTick();
    }

    private void everyTick() {
        Bukkit.getScheduler().runTaskTimer(this.main, this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (String name : noClipPlayerNames) {
            Player p = Bukkit.getPlayer(name);
            if (p.isOnline()) {
                if (p.getGameMode().equals(GameMode.CREATIVE)) {
                    boolean noClip;
                    if (p.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR && p.isSneaking()) {
                        noClip = true;
                    } else {
                        noClip = isNoClip(p);
                    }
                    if (noClip) {
                        p.setGameMode(GameMode.SPECTATOR);
                    }
                } else if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    boolean noClip;
                    if (p.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR) {
                        noClip = true;
                    } else noClip = isNoClip(p);
                    if (!noClip) {
                        p.setGameMode(GameMode.CREATIVE);
                    }
                }
            }
        }
    }

    private boolean isNoClip(Player p) {
        boolean noClip = false;
        if (p.getLocation().add(+0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(-0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(0, 0, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(0, 0, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(+0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(-0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(0, 1, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(0, 1, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (p.getLocation().add(0, +1.9, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        }
        return noClip;
    }
}
