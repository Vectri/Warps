package org.github.vectri.warps.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

/**
 * A file to handle the /setwarp command.
 */
public class SetWarpCommand implements CommandExecutor {
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
        if (args.length > 2) {
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
            sender.sendMessage(ChatColor.RED + "You cannot name a warp with a reserved keyword.");
            return true;
        }
        if (WarpHandler.exists(warpType, warpName)) {
            sender.sendMessage(ChatColor.RED + "Warp name already exists! Please choose another name.");
            return true;
        }
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        double playerX = playerLocation.getX();
        double playerY = playerLocation.getY();
        double playerZ = playerLocation.getZ();
        float playerYaw = Math.round(playerLocation.getYaw() / 90) * 90;
        Location warpLocation = new Location(playerLocation.getWorld(), playerX, playerY, playerZ, playerYaw, 0);
        WarpHandler.create(warpType, player.getUniqueId(), warpName, warpLocation);
        sender.sendMessage(ChatColor.GREEN + "Successfully created " + warpType.name().toLowerCase() + " warp, " + warpName + "!");
        return true;
    }
}
