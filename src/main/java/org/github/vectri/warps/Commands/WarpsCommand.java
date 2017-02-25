package org.github.vectri.warps.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.Warp;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

import java.util.ArrayList;

/**
 * A file to handle the /warps command.
 */
public class WarpsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Too many arguments.");
            return false;
        }
        WarpType warpType;
        if (args.length == 1) {
            warpType = WarpType.get(args[0]);
            if (warpType == null) {
                sender.sendMessage(ChatColor.RED + "You must specify a valid warp type. (Personal, Group, Server)");
                return true;
            }
        } else {
            warpType = WarpType.Personal;
        }
        ArrayList<Warp> warpArray = WarpHandler.getList(warpType);
        Player player = (Player) sender;
        String list = "List of " + warpType.name().toLowerCase() + " warps: ";
        if (warpArray.isEmpty()) {
            list += "None.";
        } else {
            for (Warp warp : warpArray) {
                if (warp.getOwner() == player.getUniqueId()) {
                    if (warpArray.indexOf(warp) == warpArray.size() - 1) {
                        list += warp.getName() + ".";
                        continue;
                    }
                    list += warp.getName() + ", ";
                }
            }
        }
        sender.sendMessage(list);
        return true;
    }
}
