package com.buildersrefuge.utilities.cmd;

import com.buildersrefuge.utilities.Main;
import com.buildersrefuge.utilities.object.NoClipManager;
import com.buildersrefuge.utilities.util.ToggleGUI;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandHandler implements Listener, CommandExecutor {
    public static Main plugin;
    public static String prefix = "§3buildersUtil> §b";

    public CommandHandler(Main main) {
        plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;
        StringBuilder s;
        switch (cmd.getName().toLowerCase()) {
            case "nc":
                if (p.hasPermission("builders.util.noclip")) {
                    if (NoClipManager.noClipPlayerNames.contains(p.getName())) {
                        NoClipManager.noClipPlayerNames.remove(p.getName());
                        p.sendMessage(prefix + " NoClip §cDisabled");
                        if (p.getGameMode() == GameMode.SPECTATOR) {
                            p.setGameMode(GameMode.CREATIVE);
                        }
                    } else {
                        NoClipManager.noClipPlayerNames.add(p.getName());
                        p.sendMessage(prefix + " NoClip §aEnabled");
                    }
                    return true;
                }
                break;
            case "n":
                if (p.hasPermission("builders.util.nightvision")) {
                    if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        p.removePotionEffect(PotionEffectType.NIGHT_VISION);

                        p.sendMessage(prefix + " NightVision §cDisabled");
                    } else {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
                        p.sendMessage(prefix + " NightVision §aEnabled");
                    }
                    return true;
                }
                break;
            case "butil":

                ToggleGUI gui = new ToggleGUI();
                p.openInventory(gui.generateInv(p));
                return true;
            case "af":
                if (p.hasPermission("builders.util.advancedfly")) {
                    if (com.buildersrefuge.utilities.listeners.PlayerMoveListener.togglePlayer(p)) {
                        p.sendMessage(prefix + " Advanced Fly §aEnabled");
                        return true;
                    } else {
                        p.sendMessage(prefix + " Advanced Fly §cDisabled");
                        return true;
                    }
                }
                break;
            case "/1":
                plugin.getServer().dispatchCommand(p, "/pos1");
                return true;
            case "/2":
                plugin.getServer().dispatchCommand(p, "/pos2");
                return true;
            case "/cuboid":
                plugin.getServer().dispatchCommand(p, "/sel cuboid");
                return true;
            case "/convex":
                plugin.getServer().dispatchCommand(p, "/sel convex");
                return true;
            case "/s":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }
                plugin.getServer().dispatchCommand(p, "/set " + s);
                return true;
            case "/r":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }
                plugin.getServer().dispatchCommand(p, "/replace " + s);
                return true;
            case "/pa":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }
                plugin.getServer().dispatchCommand(p, "/paste " + s);
                return true;
            case "/f":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }
                plugin.getServer().dispatchCommand(p, "/flip " + s);
                return true;
            case "/c":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }
                plugin.getServer().dispatchCommand(p, "/copy " + s);
                return true;
            case "/derot":
                if (args.length == 2) {
                    int degrees;
                    try {
                        degrees = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(prefix + "§c //derot [axis] [degrees]");
                        return true;
                    }
                    float radian = (float) (((float) degrees / (float) 360) * 2 * Math.PI);
                    if (args[0].equalsIgnoreCase("x")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(y,z," + radian + ")");
                    } else if (args[0].equalsIgnoreCase("y")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(x,z," + radian + ")");
                    } else if (args[0].equalsIgnoreCase("z")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(x,y," + radian + ")");
                    } else {
                        p.sendMessage(prefix + "§c //derot [axis] [degrees]");
                    }
                    return true;
                } else {
                    p.sendMessage(prefix + "§c //derot [axis] [degrees]");
                    return true;
                }
            case "/twist":
                if (args.length == 2) {
                    int degrees;
                    try {
                        degrees = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage(prefix + "§c //twist [axis] [degrees]");
                        return true;
                    }
                    float radian = (float) (((float) degrees / (float) 360) * 2 * Math.PI);
                    if (args[0].equalsIgnoreCase("x")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(y,z," + radian / 2 + "*(x+1))");
                    } else if (args[0].equalsIgnoreCase("y")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(x,z," + radian / 2 + "*(y+1))");
                    } else if (args[0].equalsIgnoreCase("z")) {
                        plugin.getServer().dispatchCommand(p, "/deform rotate(x,y," + radian / 2 + "*(z+1))");
                    } else {
                        p.sendMessage(prefix + "§c //twist [axis] [degrees]");
                    }
                    return true;
                } else {
                    p.sendMessage(prefix + "§c //twist [axis] [degrees]");
                    return true;
                }
            case "/scale":
                if (args.length == 1) {
                    double size;
                    try {
                        size = Double.parseDouble(args[0]);
                    } catch (Exception e) {
                        p.sendMessage(prefix + "§c //scale [size]");
                        return true;
                    }
                    plugin.getServer().dispatchCommand(p, "/deform x/=" + size + ";y/=" + size + ";z/=" + size);
                    return true;
                } else {
                    p.sendMessage(prefix + "§c //scale [size]");
                    return true;
                }
            case "/ws":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }

                plugin.getServer().dispatchCommand(p, "speed walk " + s);
                return true;
            case "fa":
                s = new StringBuilder();
                if (args.length != 0) {
                    for (String arg : args) {
                        s.append(arg).append(" ");
                    }
                }

                plugin.getServer().dispatchCommand(p, "speed fly " + s);
                return true;
        }
        return false;
    }

}