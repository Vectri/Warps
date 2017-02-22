package org.github.vectri.warps.Warp;

/**
 * An enum that defines the types of warps.
 */
public enum WarpType {
    Personal, Group, Server;

    public static WarpType get(String type) {
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
