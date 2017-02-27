package org.github.vectri.warps.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.Warp;
import org.github.vectri.warps.Warp.WarpGroup;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * A file to handle the /warpgroup command.
 */
public class WarpGroupCommand implements CommandExecutor {
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
        String modifier = (args.length >= 2) ? args[1] : "";
        Byte mode;
        switch (modifier) {
            case "add":
                mode = 0;
                break;
            case "remove":
                mode = 1;
                break;
            case "list":
                mode = 2;
                break;
            default:
                sender.sendMessage(ChatColor.RED + "You must specify a valid modifier. (add | remove | list)");
                return false;
        }
        if (args.length == 2 && mode != 2) {
            sender.sendMessage(ChatColor.RED + "You must specify a player to add or remove.");
            return false;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        if (!WarpHandler.exists(WarpType.Group, args[0], playerUUID)) {
            sender.sendMessage(ChatColor.RED + "Warp " + args[0] + " does not exist or is not a group warp.");
            return true;
        }
        WarpGroup warpGroup = WarpHandler.get(args[0], playerUUID);
        ArrayList<UUID> members = warpGroup.getMembers();
        if (mode == 2) {
            String memberList = "List of members in " + warpGroup.getName() + ": ";
            if (members.isEmpty()) {
                memberList += "None.";
            } else {
                for (UUID member : members) {
                    if (members.indexOf(member) == members.size() - 1) {
                        memberList += Bukkit.getOfflinePlayer(member).getName() + ".";
                        continue;
                    }
                    memberList += Bukkit.getOfflinePlayer(member).getName() + ", ";
                }
            }
            sender.sendMessage(memberList);
            return true;
        }
        UUID targetUUID = null;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.getName().equalsIgnoreCase(args[2])) {
                targetUUID = offlinePlayer.getUniqueId();
            }
        }
        if (targetUUID == null) {
            sender.sendMessage(ChatColor.RED + "The specified player " + args[2] + " is not a valid player.");
            return true;
        }
        if (mode == 0) {
            for (UUID member : members) {
                if (member == targetUUID) {
                    sender.sendMessage(ChatColor.RED + "That player is already a member of " + warpGroup.getName() + "!");
                    return true;
                }
            }
            warpGroup.addMember(targetUUID);
            sender.sendMessage(ChatColor.GREEN + args[2] + " was successfully added as a member of " + warpGroup.getName() + "!");
            sender.sendMessage(args[2] + " can use " + ChatColor.GREEN + "/warp g "+ sender.getName() + " " + warpGroup.getName() + ChatColor.RESET + " to use your warp!");
            return true;
        }
        if (mode == 1) {
            for (UUID member : members) {
                if (member == targetUUID) {
                    if (member == ((Player) sender).getUniqueId()) {
                        sender.sendMessage(ChatColor.RED + "You cannot remove yourself from the group.");
                        return true;
                    }
                    warpGroup.removeMember(targetUUID);
                    sender.sendMessage(ChatColor.RED + args[2] + " was successfully removed from " + warpGroup.getName() + "!");
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "That player is not a member of " + warpGroup.getName() + "!");
            return true;
        }
        return true;
    }
}
