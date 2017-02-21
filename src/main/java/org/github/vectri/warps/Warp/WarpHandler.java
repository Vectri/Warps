package org.github.vectri.warps.Warp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warps;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A file to handle warps held in RAM.
 */
public class WarpHandler {
    private final Warps plugin;

    private static ArrayList<Warp> personalWarps = new ArrayList<>();
    private static ArrayList<Warp> groupWarps = new ArrayList<>();
    private static ArrayList<Warp> serverWarps = new ArrayList<>();

    public WarpHandler(Warps plugin) {
        this.plugin = plugin;
    }

    private ArrayList getArrayByWarpType(WarpType type) {
        switch (type) {
            case Personal:
                return personalWarps;
            case Group:
                return groupWarps;
            case Server:
                return serverWarps;
        }
        return null;
    }

    private boolean exists(ArrayList<Warp> warpsArray, String name) {
        for (Warp warp : warpsArray) {
            if (warp.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean exists(WarpType type, String name) {
        if (type != null) {
            ArrayList<Warp> warpsArray = this.getArrayByWarpType(type);
            for (Warp warp : warpsArray) {
                if (warp.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void create(WarpType type, UUID owner, String name, Location location) {
        ArrayList<Warp> warpsArray = this.getArrayByWarpType(type);
        if (!this.exists(warpsArray, name)) {
            warpsArray.add(new Warp(type, owner, name, location));
            for (Warp warp : warpsArray) {
                plugin.getLogger().info(warp.getName());
            }
        }
    }
}
