package org.github.vectri.warps.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.Warp;
import org.github.vectri.warps.Warp.WarpGroup;
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
        String list = "List of " + ChatColor.GREEN + warpType.name().toLowerCase() + ChatColor.RESET + " warps: ";
        String membershipList = "List of " + ChatColor.GREEN + warpType.name().toLowerCase() + ChatColor.RESET + " warps that you have membership to: ";
        if (warpType == WarpType.Personal) {
            ArrayList<Warp> playerWarps = WarpHandler.getPlayerList(player, warpArray);
            for (Warp warp : playerWarps) {
                    if (playerWarps.indexOf(warp) == playerWarps.size() - 1) {
                        list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ".";
                        continue;
                    }
                    list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ", ";
            }
        } else if (warpType == WarpType.Group) {
            ArrayList<Warp> playerWarps = WarpHandler.getPlayerList(player, warpArray);
            for (Warp warp : playerWarps) {
                if (playerWarps.indexOf(warp) == playerWarps.size() - 1) {
                    list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ".";
                    continue;
                }
                list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ", ";
            }
            ArrayList<WarpGroup> membershipWarps = WarpHandler.getPlayerMembershipList(player);
            for (WarpGroup warpGroup : membershipWarps) {
                if (warpArray.indexOf(warpGroup) == warpArray.size() - 1) {
                    membershipList += ChatColor.GREEN + warpGroup.getName() + ChatColor.RESET + ".";
                    continue;
                }
                membershipList += ChatColor.GREEN + warpGroup.getName() + ChatColor.RESET + ", ";
            }
        } else if (warpType == WarpType.Server) {
            for (Warp warp : warpArray) {
                if (warpArray.indexOf(warp) == warpArray.size() - 1) {
                    list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ".";
                    continue;
                }
                list += ChatColor.GREEN + warp.getName() + ChatColor.RESET + ", ";
            }
        }
        if (warpType == WarpType.Group) {
            sender.sendMessage(new String[]{list, membershipList});
        } else {
            sender.sendMessage(list);
        }
        return true;
    }
}
