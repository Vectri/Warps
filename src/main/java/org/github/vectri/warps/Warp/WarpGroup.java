package org.github.vectri.warps.Warp;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A file that extends Warps to add group functionality.
 */
public class WarpGroup extends Warp {
    private ArrayList<UUID> members = new ArrayList<>();

    WarpGroup(WarpType type, UUID owner, String name, Location location, ArrayList<UUID> members) {
        super(type, owner, name, location);
        this.members = members;
    }

    public ArrayList<UUID> getMembers() {
        return members;
    }

    public boolean addMember(UUID member) {
        return members.add(member);
    }

    public boolean removeMember(UUID member) {
        return members.remove(member);
    }

    public Warp toWarp() {
        return this;
    }
}
