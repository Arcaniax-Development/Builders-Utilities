package net.arcaniax.buildersutilities.listeners;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.NoClipManager;
import net.arcaniax.buildersutilities.Settings;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitAndJoinListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        NoClipManager.noClipPlayerIds.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (Main.getInstance().getNmsManager().isAtLeastVersion(1, 9, 0)) {
            AttributeInstance attribute = event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            if (attribute == null) {
                return;
            }

            if (Settings.fixAttackSpeed) {
                attribute.setBaseValue(1024.0D);
            } else if (attribute.getBaseValue() == 1024.0D) {
                attribute.setBaseValue(attribute.getDefaultValue());
            }
            event.getPlayer().saveData();

        }
    }

}
