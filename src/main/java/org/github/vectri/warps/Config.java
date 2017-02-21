package org.github.vectri.warps;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.vectri.warps.Warps;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * A file to create, edit, and save configs.
 */
public class Config {
    private final Warps plugin;

    private File configFile;
    private FileConfiguration fileConfiguration;

    public Config(Warps plugin, String fileName) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), fileName);
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }
}
