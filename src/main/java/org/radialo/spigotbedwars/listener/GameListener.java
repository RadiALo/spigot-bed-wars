package org.radialo.spigotbedwars.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.GameState;
import org.radialo.spigotbedwars.arena.Arena;

public class GameListener implements Listener {
    private final BedWarsPlugin plugin;

    public GameListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Arena arena = plugin.getArenaManager().getArena(event.getPlayer());

        if (arena != null && arena.getGameState().equals(GameState.LIVE)) {
            arena.getGame().addPoint(event.getPlayer());
        }
    }
}
