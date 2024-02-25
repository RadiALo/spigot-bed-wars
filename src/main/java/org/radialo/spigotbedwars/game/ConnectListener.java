package org.radialo.spigotbedwars.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.game.Arena;
import org.radialo.spigotbedwars.config.ConfigManager;

public class ConnectListener implements Listener {
    private final BedWarsPlugin plugin;

    public ConnectListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(ConfigManager.getLobbySpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Arena arena = plugin.getArenaManager().getArena(event.getPlayer());

        if (arena != null) {
            arena.removePlayer(event.getPlayer());
        }
    }
}
