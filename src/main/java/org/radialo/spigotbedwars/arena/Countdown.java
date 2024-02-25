package org.radialo.spigotbedwars.arena;

import org.bukkit.scheduler.BukkitRunnable;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.config.ConfigManager;

public class Countdown extends BukkitRunnable {
    private final BedWarsPlugin plugin;
    private final Arena arena;
    private int countdownSeconds;

    public Countdown(BedWarsPlugin plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }


    @Override
    public void run() {

    }
}
