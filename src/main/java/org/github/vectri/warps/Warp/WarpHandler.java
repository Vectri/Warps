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
    private static ArrayList<Warp> personalWarps = new ArrayList<>();
    private static ArrayList<Warp> groupWarps = new ArrayList<>();
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

    public static Warp get(WarpType type, String name) {
        ArrayList<Warp> warpsArray = getArrayByWarpType(type);
        return get(warpsArray, name);
    }

    public static Warp get(String name) {
        Warp personal = get(personalWarps, name);
        if (personal != null) {
            return personal;
        }
        Warp group = get(groupWarps, name);
        if (group != null) {
            return group;
        }
        Warp server = get(serverWarps, name);
        if (server != null) {
            return server;
        }
        return null;
    }

    public static boolean exists(WarpType type, String name) {
        return (get(type, name) != null) ? true : false;
    }

    public static boolean exists(String name) {
        return (get(name) != null) ? true : false;
    }

    public static void create(WarpType type, UUID owner, String name, Location location) {
        if (!exists(type, name)) {
            ArrayList<Warp> warpsArray = getArrayByWarpType(type);
            warpsArray.add(new Warp(type, owner, name, location));
        }
    }

    public static boolean delete(WarpType type, String name) {
        Warp warp = get(type, name);
        if (warp != null) {
            getArrayByWarpType(type).remove(warp);
            return true;
        }
        return false;
    }
}
