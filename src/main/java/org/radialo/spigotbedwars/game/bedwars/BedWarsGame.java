package org.radialo.spigotbedwars.game.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.radialo.spigotbedwars.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class BedWarsGame extends Game {
    private final Map<Team, Location> teamSpawns = new HashMap<>();
    private final Map<UUID, Team> playersTeams = new HashMap<>();

    public BedWarsGame(ConfigurationSection config) {
        super(config);
        World world = Bukkit.getWorld(config.getString("world"));

        for (Team team : Team.values()) {
            ConfigurationSection section = config.getConfigurationSection(team.name.toLowerCase());

            teamSpawns.put(
                    team,
                    new Location(
                            world,
                            section.getDouble("x"),
                            section.getDouble("y"),
                            section.getDouble("z"),
                            (float) section.getDouble("yaw"),
                            (float) section.getDouble("pitch")
                    )
            );
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < arena.getPlayers().size(); i++) {
            UUID player = arena.getPlayers().get(i);
            Team team = Team.values()[i  % Team.values().length];

            playersTeams.put(player, team);

            Bukkit.getPlayer(player).teleport(teamSpawns.get(team));
        }

    }

    @Override
    public void reset() {
        playersTeams.clear();
    }

    public enum Team {
        RED("Red"),
        BLUE("Blue"),
        GREEN("Green"),
        YELLOW("Yellow");

        private String name;

        Team(String name) {
            this.name = name;
        }
    }
}
