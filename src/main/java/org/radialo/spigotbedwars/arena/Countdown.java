package org.radialo.spigotbedwars.arena;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.GameState;
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

    public void start() {
        arena.setGameState(GameState.COUNTDOWN);
        runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        if (countdownSeconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (countdownSeconds <= 10 || countdownSeconds % 5 == 0) {
            arena.sendMessage(
                    ChatColor.GREEN + "Game will start in " + countdownSeconds
                            + " second" + (countdownSeconds > 1 ? "s" : "") + "!"
            );
        }

        countdownSeconds--;
    }
}
