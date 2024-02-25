package org.radialo.spigotbedwars.game.blockbreak;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.game.Arena;

public class BreakBlocksListener implements Listener {
    private final BedWarsPlugin plugin;

    public BreakBlocksListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Arena arena = plugin.getArenaManager().getArena(event.getPlayer());

        if (arena != null && arena.getState().equals(Arena.ArenaState.LIVE)) {
            if (arena.getGame() instanceof BreakBlocksGame) {
                BreakBlocksGame game = (BreakBlocksGame) arena.getGame();

                game.addPoint(event.getPlayer());
            }
        }
    }
}
