package org.github.vectri.warps.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.github.vectri.warps.Config;
import org.github.vectri.warps.Warps;
import sun.java2d.windows.GDIRenderer;

import java.util.*;

/**
 * Handles saving and loading warps to and from a file.
 */
public class WarpConfig {
    private Config warpConfig;

    private ArrayList<Warp> personalWarps = new ArrayList<>();
    private ArrayList<WarpGroup> groupWarps = new ArrayList<>();
    private ArrayList<Warp> serverWarps = new ArrayList<>();

    public WarpConfig(Warps plugin) {
        warpConfig = new Config(plugin, "data.yml");
    }

    public void load() {
        this.loadWarps(WarpType.Personal);
        this.loadWarps(WarpType.Server);
        this.loadWarps(WarpType.Group);
    }

    public void save() {
        personalWarps = WarpHandler.getList(WarpType.Personal);
        groupWarps = WarpHandler.getList(WarpType.Group);
        serverWarps = WarpHandler.getList(WarpType.Server);
        this.saveWarps(WarpType.Personal, personalWarps);
        this.saveGroupWarps(groupWarps);
        this.saveWarps(WarpType.Server, serverWarps);
        warpConfig.saveConfig();
    }

    private void saveWarps(WarpType warpType, ArrayList<Warp> warpArray) {
        for (Warp warp : warpArray) {
            Location warpLocation = warp.getLocation();
            String path = warpType.name().toLowerCase() + "." + warp.getOwner() + "." + warp.getName()+ ".location.";
            warpConfig.getConfig().set(path + "world", warpLocation.getWorld().getName());
            warpConfig.getConfig().set(path + "x", warpLocation.getX());
            warpConfig.getConfig().set(path + "y", warpLocation.getY());
            warpConfig.getConfig().set(path + "z", warpLocation.getZ());
            warpConfig.getConfig().set(path + "yaw", warpLocation.getYaw());
            warpConfig.getConfig().set(path + "pitch", warpLocation.getPitch());
        }
    }

    private void saveGroupWarps(ArrayList<WarpGroup> warpArray) {
        ArrayList<Warp> convertedList = new ArrayList<>();
        warpArray.forEach(warpGroup -> convertedList.add(warpGroup.toWarp()));
        this.saveWarps(WarpType.Group, convertedList);
        for (WarpGroup warpGroup : warpArray) {
            String path = "group." + warpGroup.getOwner() + "." + warpGroup.getName()+ ".members";
            ArrayList members = new ArrayList<>();
            warpGroup.getMembers().forEach(uuid -> members.add(uuid.toString()));
            warpConfig.getConfig().set(path, members);
        }
    }

    private void loadWarps(WarpType warpType) {
        String type = warpType.name().toLowerCase();
        for (Object player : warpConfig.getConfig().getConfigurationSection(type).getKeys(false)) {
            for (Object warp : warpConfig.getConfig().getConfigurationSection(type + "." + player).getKeys(false)) {
                List locationString = warpConfig.getConfig().getList(type + "." + player + "." + warp + ".location");
                String ownerString = player.toString();
                UUID owner = UUID.fromString(ownerString);
                String warpName = warp.toString();
                String worldString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.world");
                World world = Bukkit.getServer().getWorld(worldString);
                String xString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.x");
                double x = Double.valueOf(xString);
                String yString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.y");
                double y = Double.valueOf(yString);
                String zString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.z");
                double z = Double.valueOf(zString);
                String yawString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.yaw");
                float yaw = Float.valueOf(yawString);
                String pitchString = warpConfig.getConfig().getString(type + "." + player + "." + warp + ".location.pitch");
                float pitch = Float.valueOf(pitchString);
                if (warpType == WarpType.Personal) {
                    personalWarps.add(new Warp(warpType, owner, warpName, new Location(world, x, y, z, yaw, pitch)));
                } else if (warpType == WarpType.Group) {
                    List<String> membersString = warpConfig.getConfig().getStringList(type + "." + player + "." + warp + ".members");
                    ArrayList<UUID> members = new ArrayList<>();
                    membersString.forEach(member -> members.add(UUID.fromString(member)));
                    groupWarps.add(new WarpGroup(warpType, owner, warpName, new Location(world, x, y, z, yaw, pitch), members));
                } else if (warpType == WarpType.Server) {
                    serverWarps.add(new Warp(warpType, owner, warpName, new Location(world, x, y, z, yaw, pitch)));
                }
            }
        }
        if (warpType == WarpType.Personal) {
            WarpHandler.setList(warpType, personalWarps);
        } else if (warpType == WarpType.Server) {
            WarpHandler.setList(warpType, serverWarps);
        } else if (warpType == WarpType.Group) {
            WarpHandler.setList(warpType, groupWarps);
        }
    }
}
