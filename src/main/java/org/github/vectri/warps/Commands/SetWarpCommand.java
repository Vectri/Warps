package org.github.vectri.warps.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;
import org.github.vectri.warps.Warps;

import java.util.UUID;

/**
 * A file to handle the /setwarp command.
 */
public class SetWarpCommand implements CommandExecutor {
    private WarpHandler warpHandler;

    public SetWarpCommand(Warps plugin) {
        warpHandler = new WarpHandler(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            String arg0 = (args.length != 0) ? args[0] : null;
            if (arg0 != null) {
                WarpType warpType = getWarpType(arg0);
                String warpName = null;
                if (warpType == null) {
                    warpType = WarpType.Personal;
                    warpName = arg0;
                } else {
                    String arg1 = (args.length > 0) ? args[1] : null;
                    if (arg1 != null) {
                        warpName = arg1;
                    } else {
                        sender.sendMessage("You must specify the name of the warp.");
                    }
                }
                if (this.getWarpType(warpName) == null) {
                    if (!warpHandler.exists(warpType, warpName)) {
                        Player player = (Player) sender;
                        warpHandler.create(warpType, player.getUniqueId(), warpName, player.getLocation());
                        sender.sendMessage("Successfully created " + warpType.name().toLowerCase() + " warp, " + warpName + "!");
                    } else {
                        sender.sendMessage("Warp name already exists! Please choose another name.");
                    }
                } else {
                    sender.sendMessage("You cannot name a warp with a reserved keyword.");
                }
                return true;
            }
        }
        return false;
    }

    private WarpType getWarpType(String type) {
        type = type.toLowerCase();
        switch (type) {
            case "p":
            case "personal":
                return WarpType.Personal;
            case "g":
            case "group":
                return WarpType.Group;
            case "s":
            case "server":
                return WarpType.Server;
            default:
                return null;
        }
    }
}
