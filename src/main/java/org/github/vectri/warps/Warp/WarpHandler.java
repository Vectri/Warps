package org.github.vectri.warps.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A file to handle warps held in RAM.
 */
public class WarpHandler {
    private static ArrayList<Warp> personalWarps = new ArrayList<>();
    private static ArrayList<WarpGroup> groupWarps = new ArrayList<>();
    private static ArrayList<Warp> serverWarps = new ArrayList<>();

    public static ArrayList getList(WarpType type) {
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

    public static ArrayList<Warp> getPlayerList(Player player, ArrayList<Warp> warpArray) {
        ArrayList<Warp> playerWarps = new ArrayList<>();
        for (Warp warp : warpArray) {
            if (warp.getOwner() == player.getUniqueId()) {
                playerWarps.add(warp);
            }
        }
        return playerWarps;
    }

    public static ArrayList<WarpGroup> getPlayerMembershipList(Player player) {
        ArrayList<WarpGroup> playerWarps = new ArrayList<>();
        for (WarpGroup warpGroup : groupWarps) {
            if (warpGroup.getMembers().contains(player.getUniqueId()) && warpGroup.getOwner() != player.getUniqueId()) {
                playerWarps.add(warpGroup);
            }
        }
        return playerWarps;
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
        ArrayList warpsArray = getList(type);
        return get(warpsArray, name);
    }

    public static boolean exists(WarpType type, String name) {
        return (get(type, name) != null);
    }

    public static void create(WarpType type, UUID owner, String name, Location location) {
        if (!exists(type, name)) {
            ArrayList warpsArray = getList(type);
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
            ArrayList warpsArray = getList(type);
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
