package org.radialo.spigotbedwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.radialo.spigotbedwars.arena.ArenaCommand;
import org.radialo.spigotbedwars.arena.ArenaManager;
import org.radialo.spigotbedwars.config.ConfigManager;
import org.radialo.spigotbedwars.listener.ConnectListener;
import org.radialo.spigotbedwars.listener.GameListener;

public final class BedWarsPlugin extends JavaPlugin {
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigManager.setupConfig(this);
        arenaManager = new ArenaManager(this);
        Bukkit.getPluginManager().registerEvents(new ConnectListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
        getCommand("arena").setExecutor(new ArenaCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }
}
