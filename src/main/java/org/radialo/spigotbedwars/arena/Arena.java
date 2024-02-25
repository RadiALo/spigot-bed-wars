package org.radialo.spigotbedwars.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.GameState;
import org.radialo.spigotbedwars.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private final int id;
    private final Location spawn;
    private final BedWarsPlugin plugin;
    private Game game;


    private List<UUID> players;
    private GameState gameState;
    private Countdown countdown;

    public Arena(BedWarsPlugin plugin, int id, Location spawn) {
        this.plugin = plugin;
        this.id = id;
        this.spawn = spawn;
        reset(false);
    }

    public void start() {
        game.start();
    }

    public void reset(boolean kickPlayer) {
        if (kickPlayer) {
            if (players != null) {
                for (UUID uuid : players) {
                    Bukkit.getPlayer(uuid).teleport(ConfigManager.getLobbySpawn());
                }
            }

            players = new ArrayList<>();
        }

        if (countdown != null) {
            countdown.cancel();
        }

        gameState = GameState.RECRUTING;
        countdown = new Countdown(plugin, this);
        this.game = new Game(this);
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (gameState.equals(GameState.RECRUTING)
                && players.size()>= ConfigManager.getRequiredPlayers()) {
            countdown.start();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
    }

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getId() {
        return id;
    }

    public Location getSpawn() {
        return spawn;
    }
}
