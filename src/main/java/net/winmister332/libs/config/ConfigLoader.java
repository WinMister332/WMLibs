/*
 * ==========
 * ConfigLoader.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:17:11 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ConfigLoader {
    private File configFile;
    private FileConfiguration config;
    private JavaPlugin plugin;

    ConfigLoader(JavaPlugin plugin, String fileName) throws IOException {
        this.plugin = plugin;
        if (fileName.endsWith(".yaml") && !fileName.endsWith(".yml"))
            fileName.replaceAll(".yaml", ".yml");
        else if (!fileName.endsWith(".yaml") && !fileName.endsWith(".yml"))
            fileName += ".yml";

        this.configFile = new File(plugin.getDataFolder(), fileName);
        config = requestConfigFile();
    }

    public File getConfigFile() { return configFile; }
    public FileConfiguration getConfig() { return config; }
    public JavaPlugin getPlugin() { return plugin; }

    public void saveConfig() throws IOException {
        if (configFile.isFile() && !configFile.exists())
            configFile.mkdirs();

        if (configFile.getName().equalsIgnoreCase("config.yml"))
            plugin.saveDefaultConfig();
        else
        {
            if (plugin.getResource(configFile.getName()) != null)
                plugin.saveResource(configFile.getName(), true);
            else
            {
                if (!configFile.exists())
                    config.set(configFile.getName().replaceAll(".yml", ""), null);
                config.save(configFile);
            }
        }
    }

    private FileConfiguration requestConfigFile() throws IOException {
        saveConfig();

        if (configFile.getName().equalsIgnoreCase("config.yml"))
            return plugin.getConfig();
        else
            return YamlConfiguration.loadConfiguration(configFile);
    }

    public static FileConfiguration loadConfig(JavaPlugin plugin, String configFile) throws IOException {
        if (plugin == null || (configFile == null || (configFile != null && (configFile.isEmpty() || configFile.isBlank())))) return null;

        ConfigLoader loader = new ConfigLoader(plugin, configFile);
        return loader.getConfig();
    }
}
