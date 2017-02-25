package org.github.vectri.warps.Warp;

import org.bukkit.Location;

import java.util.UUID;

/**
 * A file to handle individual warp data.
 */
public class Warp {
    private WarpType type;
    private UUID owner;
    private String name;
    private Location location;

    public Warp (WarpType type, UUID owner, String name, Location location) {
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.location = location;
    }

    public WarpType getType() {
        return type;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
