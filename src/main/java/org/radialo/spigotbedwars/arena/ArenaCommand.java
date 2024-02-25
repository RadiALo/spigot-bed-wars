package org.radialo.spigotbedwars.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.radialo.spigotbedwars.BedWarsPlugin;
import org.radialo.spigotbedwars.config.ConfigManager;
import org.radialo.spigotbedwars.game.Arena;

public class ArenaCommand implements CommandExecutor {
    private final BedWarsPlugin plugin;

    public ArenaCommand(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                player.sendMessage(ChatColor.GREEN + "There are the available arenas:");

                for (Arena arena : plugin.getArenaManager().getArenas()) {
                    player.sendMessage(
                            ChatColor.GREEN + "-" + arena.getId() + "(" + arena.getState() + ")"
                    );
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                Arena arena = plugin.getArenaManager().getArena(player);

                if (arena != null) {
                    arena.removePlayer(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not in arena");
                }
            } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                try {
                    if (plugin.getArenaManager().getArena(player) != null) {
                        player.sendMessage(ChatColor.RED + "You already playing in an arena!");
                    }

                    Arena arena = plugin.getArenaManager().getArena(Integer.parseInt(args[1]));

                    if (arena == null) {
                        player.sendMessage(ChatColor.RED + "There is no arena with provided id");
                    } else if (arena.getState().equals(Arena.ArenaState.LIVE)) {
                        player.sendMessage(ChatColor.RED + "The arena already in game!");
                    } else if (arena.getPlayers().size() >= ConfigManager.getMaxPlayers()) {
                        player.sendMessage(ChatColor.RED + "This arena is full!");
                    } else {
                        player.sendMessage(ChatColor.GREEN + "You are now playing in an arena" + arena.getId() + "!");
                        arena.addPlayer(player);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage! There are the options:");
                player.sendMessage(ChatColor.RED + "- /arena list");
                player.sendMessage(ChatColor.RED + "- /arena leave");
                player.sendMessage(ChatColor.RED + "- /arena join <id>");
            }
        }

        return false;
    }
}
