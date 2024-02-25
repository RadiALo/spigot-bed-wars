package org.radialo.spigotbedwars.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Game {
    protected Arena arena;

    public abstract void start();

    public abstract void reset();

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    protected void endGame(Player player) {
        arena.endGame(player);
    }
}
