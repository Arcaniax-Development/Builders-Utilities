package com.arcaniax.buildersutilities.listeners;

import com.arcaniax.buildersutilities.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        if (Settings.disableWeatherChanges) {
            e.setCancelled(true);
        }
    }
}
