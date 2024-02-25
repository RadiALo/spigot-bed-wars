package org.radialo.spigotbedwars.arena;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.GameState;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game {
    private final Arena arena;
    private final Map<UUID, Integer> points = new HashMap<>();


    public Game(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        arena.setGameState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN + "GAME HAS STARTED!");

        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
        }
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId());
        playerPoints++;

        if (playerPoints == 20) {
            arena.sendMessage(ChatColor.GREEN + player.getName() + " win the game!");

        } else {
            player.sendMessage(ChatColor.GREEN + "+1 point!");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }
}
