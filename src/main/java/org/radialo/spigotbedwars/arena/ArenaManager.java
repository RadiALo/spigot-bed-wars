package org.radialo.spigotbedwars.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.BedWarsPlugin;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private final List<Arena> arenas;

    public ArenaManager(BedWarsPlugin plugin) {
        arenas = new ArrayList<>();

        FileConfiguration config = plugin.getConfig();

        for (String str : config.getConfigurationSection("arenas.").getKeys(false)) {
            arenas.add(
                    new Arena(
                            Integer.parseInt(str),
                            new Location(
                                    Bukkit.getWorld("arenas." + str + ".world"),
                                    config.getDouble("arenas." + str + ".x"),
                                    config.getDouble("arenas." + str + ".y"),
                                    config.getDouble("arenas." + str + ".z"),
                                    (float) config.getDouble("arenas." + str + ".yaw"),
                                    (float) config.getDouble("arenas." + str + ".pitch")
                            )
                    )
            );
        }
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player)) {
                return arena;
            }
        }

        return null;
    }

    public Arena getArena(int id) {
        for (Arena arena : arenas) {
            if (arena.getId() == id) {
                return arena;
            }
        }

        return null;
    }
}
