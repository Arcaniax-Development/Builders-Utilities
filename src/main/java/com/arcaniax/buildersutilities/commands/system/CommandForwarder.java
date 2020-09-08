package com.arcaniax.buildersutilities.commands.system;

import com.arcaniax.buildersutilities.Main;
import com.arcaniax.buildersutilities.commands.*;
import com.arcaniax.buildersutilities.commands.aliases.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Banner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandForwarder implements CommandExecutor {
    private final Map<String, ICommand> commands;

    public CommandForwarder() {
        commands = new HashMap<>();
        registerCommand(new HelpCommand(), "help", "h", "?");
        registerCommand(new AdvancedFlyCommand(), "af", "advancedfly");
        registerCommand(new BannerCommand(), "banner");
        registerCommand(new ColorCommand(), "color", "ac", "armorcolor");
        registerCommand(new SecretBlockCommand(), "secretblock", "secretblocks", "blocks");
        registerCommand(new NoClipCommand(), "noclip", "nc");
        registerCommand(new NightVisionCommand(), "nv", "nightvision", "n");
        registerCommand(new UtilsCommand(), "utils", "u", "util", "gui", "inventory");
        registerCommand(new PosOneAliasCommand(), "p1", "1", "one", "pos1");
        registerCommand(new PosTwoAliasCommand(), "p2", "2", "two", "pos2");
        registerCommand(new CuboidSelectionAliasCommand(), "cuboid", "cube");
        registerCommand(new ConvexSelectionAliasCommand(), "convex", "conv");
        registerCommand(new SetAliasCommand(), "set", "s");
        registerCommand(new ReplaceAliasCommand(), "replace", "r", "repl", "rep");
        registerCommand(new PasteAliasCommand(), "paste", "pa");
        registerCommand(new FlipAliasCommand(), "flip", "f");
        registerCommand(new CopyAliasCommand(), "copy", "c", "clone");
        registerCommand(new DeformRotateAliasCommand(), "deformrotate", "defrot", "dr");
        registerCommand(new TwistAliasCommand(), "twist", "tw", "t");
        registerCommand(new ScaleAliasCommand(), "scale", "sc");
        registerCommand(new WalkSpeedAliasCommand(), "walkspeed", "ws");
        registerCommand(new FlySpeedAliasCommand(), "flyspeed", "fs");
    }

    private void registerCommand(ICommand iCommand, String... aliases) {
        for (String alias : aliases) {
            commands.put(alias.toLowerCase(), iCommand);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Make it player-only
        Player player = sender instanceof Player ? ((Player) sender) : null;
        if (player == null) {
            sender.sendMessage(Main.MSG_ERROR + "Sorry this command can only be used by a player.");
            return false;
        }

        if (args.length == 0) {
            new HelpCommand().execute(player, args);
            return true;
        }

        ICommand iCommand = commands.get(args[0].toLowerCase());
        if (iCommand != null) {
            iCommand.execute(player, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }
}
