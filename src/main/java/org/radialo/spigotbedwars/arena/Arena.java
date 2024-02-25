package org.radialo.spigotbedwars.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.GameState;
import org.radialo.spigotbedwars.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private final int id;
    private final Location spawn;

    private final List<UUID> players = new ArrayList<>();
    private GameState gameState = GameState.RECRUTING;

    public Arena(int id, Location spawn) {
        this.id = id;
        this.spawn = spawn;
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getId() {
        return id;
    }

    public Location getSpawn() {
        return spawn;
    }
}
