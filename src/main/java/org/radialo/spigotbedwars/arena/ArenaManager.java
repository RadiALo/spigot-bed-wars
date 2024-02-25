package org.radialo.spigotbedwars.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.game.Arena;
import org.radialo.spigotbedwars.game.bedwars.BedWarsGame;
import org.radialo.spigotbedwars.game.blockbreak.BreakBlocksGame;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    private final List<Arena> arenas;

    public ArenaManager(BedWarsPlugin plugin) {
        arenas = new ArrayList<>();

        FileConfiguration config = plugin.getConfig();

        System.out.println(config);
        for (String str : config.getConfigurationSection("arenas").getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection("arenas." + str);

            arenas.add(
                    new Arena(
                            plugin,
                            new BedWarsGame(section),
                            Integer.parseInt(str),
                            new Location(
                                    Bukkit.getWorld(section.getString("spawn.world")),
                                    section.getDouble("spawn.x"),
                                    section.getDouble("spawn.y"),
                                    section.getDouble("spawn.z"),
                                    (float) section.getDouble("spawn.yaw"),
                                    (float) section.getDouble("spawn.pitch")
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
            if (arena.getPlayers().contains(player.getUniqueId())) {
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
