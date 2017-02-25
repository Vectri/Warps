package org.github.vectri.warps.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

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
        if (!WarpHandler.exists(WarpType.Group, args[0])) {
            sender.sendMessage(ChatColor.RED + "Warp " + args[0] + " does not exist.");
        }
        if (mode == 2) {
            // TODO: List players in a warp group.
        }
        UUID targetUUID = null;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().equalsIgnoreCase(args[2])) {
                targetUUID = player.getUniqueId();
            }
        }
        if (targetUUID == null) {
            sender.sendMessage(ChatColor.RED + "The specified player " + args[2] + " is not a valid player.");
            return true;
        }
        return true;
    }
}
