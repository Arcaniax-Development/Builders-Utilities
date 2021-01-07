/*
 *      ____        _ _     _                      _    _ _   _ _ _ _   _
 *     |  _ \      (_) |   | |                    | |  | | | (_) (_) | (_)
 *     | |_) |_   _ _| | __| | ___ _ __ ___ ______| |  | | |_ _| |_| |_ _  ___  ___
 *     |  _ <| | | | | |/ _` |/ _ \ '__/ __|______| |  | | __| | | | __| |/ _ \/ __|
 *     | |_) | |_| | | | (_| |  __/ |  \__ \      | |__| | |_| | | | |_| |  __/\__ \
 *     |____/ \__,_|_|_|\__,_|\___|_|  |___/       \____/ \__|_|_|_|\__|_|\___||___/
 *
 *    Builder's Utilities is a collection of a lot of tiny features that help with building.
 *                          Copyright (C) 2021 Arcaniax
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.buildersutilities.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomConfig {

    private static final Logger logger = LoggerFactory.getLogger("BU/" + CustomConfig.class.getSimpleName());

    private final JavaPlugin plugin;
    private final String fileName;
    private final File configFile;
    private FileConfiguration config;

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
            logger.info("Attempting to save resource: {}", configFile.getName());
            plugin.saveResource(fileName, true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            logger.error("Couldn't save {}, because {}", fileName, e.getMessage());
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
            throw new IllegalArgumentException(
                    tClass + " is of a primitive type, please use the objectified version.");
        }
        if (!has(path)) {
            if (tDefault == null) {
                try {
                    throw new ConfigurationException(
                            "Configuration key doesn't exist and the default is null");
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
