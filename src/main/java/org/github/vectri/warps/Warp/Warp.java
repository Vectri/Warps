package org.github.vectri.warps.Warp;

import org.bukkit.Location;

import java.util.UUID;

/**
 * A file to handle individual warp data.
 */
class Warp {
    private WarpType type;
    private UUID owner;
    private String name;
    private Location location;

    Warp (WarpType type, UUID owner, String name, Location location) {
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.location = location;
    }

    WarpType getType() {
        return type;
    }

    UUID getOwner() {
        return owner;
    }

    String getName() {
        return name;
    }

    Location getLocation() {
        return location;
    }
}
