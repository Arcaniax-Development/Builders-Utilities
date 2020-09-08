package net.arcaniax.buildersutilities.commands.system;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.commands.*;
import net.arcaniax.buildersutilities.commands.aliases.*;
import net.arcaniax.buildersutilities.commands.AdvancedFlyCommand;
import net.arcaniax.buildersutilities.commands.BannerCommand;
import net.arcaniax.buildersutilities.commands.ColorCommand;
import net.arcaniax.buildersutilities.commands.HelpCommand;
import net.arcaniax.buildersutilities.commands.NightVisionCommand;
import net.arcaniax.buildersutilities.commands.NoClipCommand;
import net.arcaniax.buildersutilities.commands.SecretBlockCommand;
import net.arcaniax.buildersutilities.commands.UtilsCommand;
import net.arcaniax.buildersutilities.commands.aliases.ConvexSelectionAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.CopyAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.CuboidSelectionAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.DeformRotateAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.FlipAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.FlySpeedAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PasteAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PosOneAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PosTwoAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.ReplaceAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.ScaleAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.SetAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.TwistAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.WalkSpeedAliasCommand;
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
