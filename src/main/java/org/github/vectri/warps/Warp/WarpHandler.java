package org.github.vectri.warps.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.vectri.warps.Warps;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A file to handle warps held in RAM.
 */
public class WarpHandler {
    private static ArrayList<Warp> personalWarps = new ArrayList<>();
    private static ArrayList<WarpGroup> groupWarps = new ArrayList<>();
    private static ArrayList<Warp> serverWarps = new ArrayList<>();

    private static ArrayList getArrayByWarpType(WarpType type) {
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

    private static Warp get(ArrayList<Warp> warpArray, String name) {
        for (Warp warp : warpArray) {
            if (warp.getName().equalsIgnoreCase(name)) {
                return warp;
            }
        }
        return null;
    }

    public static WarpGroup get(String name) {
        for (WarpGroup warpGroup : groupWarps) {
            if (warpGroup.getName().equalsIgnoreCase(name)) {
                return warpGroup;
            }
        }
        return null;
    }

    public static Warp get(WarpType type, String name) {
        ArrayList warpsArray = getArrayByWarpType(type);
        return get(warpsArray, name);
    }

    public static boolean exists(WarpType type, String name) {
        return (get(type, name) != null);
    }

    public static void create(WarpType type, UUID owner, String name, Location location) {
        if (!exists(type, name)) {
            ArrayList warpsArray = getArrayByWarpType(type);
            if (type == WarpType.Group) {
                ArrayList<UUID> members = new ArrayList<>();
                members.add(owner);
                try {
                    warpsArray.add(new WarpGroup(type, owner, name, location, members));
                } catch (NullPointerException e) {
                    Bukkit.getLogger().severe("Error adding group warp:" + e.getMessage());
                }
            } else {
                try {
                    warpsArray.add(new Warp(type, owner, name, location));
                } catch (NullPointerException e) {
                    Bukkit.getLogger().severe("Error adding warp:" + e.getMessage());
                }
            }
        }
    }

    public static boolean delete(WarpType type, String name) {
        Warp warp = get(type, name);
        if (warp != null) {
            ArrayList warpsArray = getArrayByWarpType(type);
            try {
                warpsArray.remove(warp);
            } catch (NullPointerException e) {
                Bukkit.getLogger().severe("Error removing warp:" + e.getMessage());
            }
            return true;
        }
        return false;
    }
}
