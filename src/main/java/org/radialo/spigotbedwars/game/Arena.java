package org.radialo.spigotbedwars.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.config.ConfigManager;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private final int id;
    private final Location spawn;
    private final BedWarsPlugin plugin;
    private final Game game;
    private final List<UUID> players = new ArrayList<>();
    private ArenaState state;
    private Countdown countdown;

    public Arena(BedWarsPlugin plugin, Game game, int id, Location spawn) {
        this.id = id;
        this.plugin = plugin;
        this.spawn = spawn;
        this.game = game;
        game.setArena(this);
        reset(false);
    }

    /* Starting game */
    public void start() {
        game.start();
        setState(Arena.ArenaState.LIVE);
        sendMessage(ChatColor.GREEN + "GAME HAS STARTED!");
    }

    /* Resetting game */
    public void reset(boolean kickPlayer) {
        if (kickPlayer) {
            if (players != null) {
                for (UUID uuid : players) {
                    Bukkit.getPlayer(uuid).teleport(ConfigManager.getLobbySpawn());
                }
            }

            players.clear();
        }

        if (countdown != null) {
            countdown.cancel();
        }

        state = ArenaState.RECRUITING;
        countdown = new Countdown(plugin, this);
        game.reset();
    }

    /* Ending game */
    public void endGame(Player player) {
        sendMessage(ChatColor.GREEN + player.getName() + " win the game!");
        reset(true);
    }

    /* Adding player to arena */
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(ArenaState.RECRUITING)
                && players.size() >= ConfigManager.getRequiredPlayers()) {
            state = ArenaState.COUNTDOWN;
            countdown.start();
        }
    }

    /* Removing player from to arena */
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("You left arena " + id, "Bye");

        if (state.equals(ArenaState.COUNTDOWN)
                && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "There is not enough players. Countdown stopped.");
            reset(false);
        }

        if (state.equals(ArenaState.LIVE)
                && players.size() < ConfigManager.getRequiredPlayers()) {
            sendMessage(ChatColor.RED + "The game has ended as too many players have left.");
            reset(false);
        }
    }

    /* Sending message to every arena player */
    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    /* Sending title to every arena player */
    public void sendTitle(String title, String subtitle) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
        }
    }

    public List<UUID> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public Location getSpawn() {
        return spawn;
    }

    public enum ArenaState {
        RECRUITING,
        COUNTDOWN,
        LIVE;
    }
}
