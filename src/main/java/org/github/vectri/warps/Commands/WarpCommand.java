package org.github.vectri.warps.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.Warp;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A file to handle the /warp command.
 */
public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command cannot be run from console.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Too few arguments.");
            return false;
        }
        if (args.length > 3) {
            sender.sendMessage(ChatColor.RED + "Too many arguments.");
            return false;
        }
        WarpType warpType = WarpType.get(args[0]);
        String warpName = "";
        if (args.length == 1) {
            if (warpType != null) {
                sender.sendMessage(ChatColor.RED + "You must specify the name of the warp.");
                return true;
            }
            warpType = WarpType.Personal;
            warpName = args[0];
        } else if (args.length == 2) {
            if (warpType == null) {
                sender.sendMessage(ChatColor.RED + "You must specify a valid warp type. (Personal, Group, Server)");
                return true;
            }
            warpName = args[1];
        }
        if (WarpType.get(warpName) != null) {   // A return of null means the keyword is not reserved.
            sender.sendMessage(ChatColor.RED + "You cannot specify a warp with a reserved keyword.");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        if (args.length == 3) {
            warpName = args[2];
            for (Warp warp : WarpHandler.getPlayerMembershipList(player)) {
                if (warp.getName().equalsIgnoreCase(warpName)) {
                    player.teleport(warp.getLocation());
                    sender.sendMessage(ChatColor.GREEN + "You teleported to " + args[1] + "'s " + warp.getType().name().toLowerCase() + " warp, " + warp.getName() + "!");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "Warp not found or you are not a member of the specified warp!");
            return true;
        }
        Warp warp = WarpHandler.get(warpType, warpName, playerUUID);
        if (warp != null) {
            player.teleport(warp.getLocation());
            sender.sendMessage(ChatColor.GREEN + "You teleported to " + warp.getType().name().toLowerCase() + " warp, " + warp.getName() + "!");
        } else {
            sender.sendMessage(ChatColor.RED + "That warp does not exist!");
        }
        return true;
    }
}
