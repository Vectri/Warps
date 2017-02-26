package org.github.vectri.warps;

import org.bukkit.plugin.java.JavaPlugin;
import org.github.vectri.warps.Commands.DelWarpCommand;
import org.github.vectri.warps.Commands.SetWarpCommand;
import org.github.vectri.warps.Commands.WarpCommand;
import org.github.vectri.warps.Commands.WarpsCommand;
import org.github.vectri.warps.Commands.WarpGroupCommand;
import org.github.vectri.warps.Warp.WarpConfig;
import org.github.vectri.warps.Warp.WarpHandler;
import org.github.vectri.warps.Warp.WarpType;

public final class Warps extends JavaPlugin {
    private WarpConfig warpConfig = new WarpConfig(this);

    @Override
    public void onEnable() {
        warpConfig.load();
        this.getCommand("warp").setExecutor(new WarpCommand());
        this.getCommand("warps").setExecutor(new WarpsCommand());
        this.getCommand("setwarp").setExecutor(new SetWarpCommand());
        this.getCommand("delwarp").setExecutor(new DelWarpCommand());
        this.getCommand("warpgroup").setExecutor(new WarpGroupCommand());
    }

    @Override
    public void onDisable() {
        warpConfig.save();
    }
}
