package org.radialo.spigotbedwars.game.blockbreak;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BreakBlocksGame extends Game {
    private Map<UUID, Integer> points = new HashMap<>();

    @Override
    public void start() {
        for (UUID uuid : arena.getPlayers()) {
            points.put(uuid, 0);
        }
    }

    @Override
    public void reset() {
        points.clear();
    }

    public void addPoint(Player player) {
        int playerPoints = points.get(player.getUniqueId());
        playerPoints++;

        if (playerPoints == 20) {
            endGame(player);
        } else {
            player.sendMessage(ChatColor.GREEN + "+1 point!");
            points.replace(player.getUniqueId(), playerPoints);
        }
    }
}
