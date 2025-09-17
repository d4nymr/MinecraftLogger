package org.dygoz.minecraftLogger.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.dygoz.minecraftLogger.MinecraftLogger;

import java.util.ArrayList;
import java.util.List;

public class SetGenerationCommand implements CommandExecutor, TabCompleter {

    private final MinecraftLogger plugin;

    public SetGenerationCommand(MinecraftLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can be executed only by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /setgeneration <verified|unverified>");
            return true;
        }

        String type = args[0].toLowerCase();

        if (!type.equals("verified") && !type.equals("unverified")) {
            player.sendMessage("Usage: /setgeneration <verified|unverified>");
            return true;
        }

        Location loc = player.getLocation();
        FileConfiguration config = plugin.getConfig();

        config.set("spawn." + type + ".world", loc.getWorld().getName());
        config.set("spawn." + type + ".x", loc.getX());
        config.set("spawn." + type + ".y", loc.getY());
        config.set("spawn." + type + ".z", loc.getZ());
        config.set("spawn." + type + ".yaw", loc.getYaw());
        config.set("spawn." + type + ".pitch", loc.getPitch());

        plugin.saveConfig();

        player.sendMessage("Spawn location for '" + type + "' saved successfully.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if ("verified".startsWith(args[0].toLowerCase())) completions.add("verified");
            if ("unverified".startsWith(args[0].toLowerCase())) completions.add("unverified");
        }
        return completions;
    }
}
