package net.arcaniax.buildersutilities.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.naming.ConfigurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomConfig {
    private final JavaPlugin plugin;
    private final String fileName;
    private FileConfiguration config;
    private File configFile;

    public CustomConfig(JavaPlugin plugin, File folder, String fileName) {
        this.plugin = plugin;
        if (!fileName.endsWith(".yml")) {
            fileName += ".yml";
        }
        this.fileName = fileName;
        configFile = new File(folder, fileName);
        reloadConfig();
    }

    public CustomConfig(JavaPlugin plugin, String fileName) {
        this(plugin, plugin.getDataFolder(), fileName);
    }

    public void reloadConfig() {
        if (!configFile.exists()) {
            plugin.getLogger().info("Attempting to save resource: " + configFile.getName());
            plugin.saveResource(fileName, true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            plugin.getLogger().severe(String.format("Couldn't save '%s', because: '%s'", fileName,
                    e.getMessage()));
        }
        reloadConfig();
    }

    public void set(String path, Object value, boolean save) {
        config.set(path, value);
        if (save) {
            saveConfig();
        }
    }

    public void set(String path, Object value) {
        set(path, value, false);
    }

    public Set<String> getKeys(boolean deep) {
        return this.config.getKeys(deep);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public boolean has(String path) {
        return config.contains(path);
    }

    public <T> T get(String path, Class<T> tClass) {
        return get(path, tClass, null);
    }

    public <T> T get(String path, Class<T> tClass, T tDefault) {
        if (tClass.isPrimitive()) {
            throw new IllegalArgumentException(tClass + " is of a primitive type, please use the objectified version.");
        }
        if (!has(path)) {
            if (tDefault == null) {
                try {
                    throw new ConfigurationException("Configuration key doesn't exist and the default is null");
                } catch (ConfigurationException e) {
                    e.printStackTrace();
                }
            }
            return tDefault;
        }
        Object object = config.get(path);
        if (object == null) {
            return tDefault;
        }
        if (!tClass.isInstance(object)) {
            throw new IllegalArgumentException(path + " is not of type " + tClass.getSimpleName());
        }
        return tClass.cast(object);
    }

    public List<String> getStringList(String path) {
        if (!has(path)) {
            return new ArrayList<>();
        }
        return config.getStringList(path);
    }

}
