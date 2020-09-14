/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2020 Arcaniax
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

package net.arcaniax.buildersutilities.commands.system;

import net.arcaniax.buildersutilities.Main;
import net.arcaniax.buildersutilities.commands.AdvancedFlyCommand;
import net.arcaniax.buildersutilities.commands.BannerCommand;
import net.arcaniax.buildersutilities.commands.ColorCommand;
import net.arcaniax.buildersutilities.commands.HelpCommand;
import net.arcaniax.buildersutilities.commands.SecretBlockCommand;
import net.arcaniax.buildersutilities.commands.NoClipCommand;
import net.arcaniax.buildersutilities.commands.NightVisionCommand;
import net.arcaniax.buildersutilities.commands.UtilsCommand;
import net.arcaniax.buildersutilities.commands.aliases.ConvexSelectionAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.CuboidSelectionAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PosOneAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PosTwoAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.SetAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.ReplaceAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.PasteAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.FlipAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.CopyAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.DeformRotateAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.TwistAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.ScaleAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.WalkSpeedAliasCommand;
import net.arcaniax.buildersutilities.commands.aliases.FlySpeedAliasCommand;
import org.bukkit.Bukkit;
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
        registerCommand(new AdvancedFlyCommand(), "af", "advfly");
        registerCommand(new BannerCommand(), "banner", "bm");
        registerCommand(new ColorCommand(), "color", "ac", "armorcolor");
        registerCommand(new SecretBlockCommand(), "blocks", "secretblocks");
        registerCommand(new NoClipCommand(), "noclip", "nc");
        registerCommand(new NightVisionCommand(), "nv", "nightvision", "n");
        registerCommand(new UtilsCommand(), "butil", "bu");
        registerCommand(new PosOneAliasCommand(), "/p1", "/1");
        registerCommand(new PosTwoAliasCommand(), "/p2", "/2");
        registerCommand(new CuboidSelectionAliasCommand(), "/cub");
        registerCommand(new ConvexSelectionAliasCommand(), "/con");
        registerCommand(new SetAliasCommand(), "/s");
        registerCommand(new ReplaceAliasCommand(), "/r");
        registerCommand(new PasteAliasCommand(), "/pa");
        registerCommand(new FlipAliasCommand(), "/f");
        registerCommand(new CopyAliasCommand(), "/c");
        registerCommand(new DeformRotateAliasCommand(), "/derot");
        registerCommand(new TwistAliasCommand(), "/twist");
        registerCommand(new ScaleAliasCommand(), "/scale");
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

        ICommand iCommand = commands.get(s.toLowerCase());
        if (iCommand != null) {
            iCommand.execute(player, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }
}
