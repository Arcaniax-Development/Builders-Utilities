package com.arcaniax.buildersutilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class NoClipManager {
    public static Set<UUID> noClipPlayerIds;

    public NoClipManager() {
        noClipPlayerIds = new HashSet<>();
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::checkForBlocks, 1L, 1L);
    }

    private void checkForBlocks() {
        for (UUID name : noClipPlayerIds) {
            Player player = Bukkit.getPlayer(name);
            if (player != null && player.isOnline()) {
                if (player.getGameMode().equals(GameMode.CREATIVE)) {
                    boolean noClip;
                    if (player.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR && player.isSneaking()) {
                        noClip = true;
                    } else {
                        noClip = isNoClip(player);
                    }
                    if (noClip) {
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                } else if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    boolean noClip;
                    if (player.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR) {
                        noClip = true;
                    } else noClip = isNoClip(player);
                    if (!noClip) {
                        player.setGameMode(GameMode.CREATIVE);
                    }
                }
            }
        }
    }

    private boolean isNoClip(Player player) {
        boolean noClip = false;
        if (player.getLocation().add(+0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(-0.4, 0, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 0, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 0, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(+0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(-0.4, 1, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 1, +0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, 1, -0.4).getBlock().getType() != Material.AIR) {
            noClip = true;
        } else if (player.getLocation().add(0, +1.9, 0).getBlock().getType() != Material.AIR) {
            noClip = true;
        }
        return noClip;
    }
}
