package org.github.vectri.warps;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.vectri.warps.Warps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * A file to create, edit, and save configs.
 */
public class Config {
    private final Warps plugin;

    private File configFile;
    private FileConfiguration fileConfiguration;
    private String fileName;

    public Config(Warps plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            configFile = new File(plugin.getDataFolder(), fileName);
            if (!configFile.exists()) {
                this.plugin.saveResource(fileName, false);
            }
        } catch (NullPointerException e) {

            e.printStackTrace();
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    private void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            fileConfiguration.setDefaults(defConfig);
        }
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
