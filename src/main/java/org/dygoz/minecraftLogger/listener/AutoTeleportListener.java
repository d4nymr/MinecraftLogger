package org.dygoz.minecraftLogger.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.dygoz.minecraftLogger.MinecraftLogger;

public class AutoTeleportListener implements Listener {

    private final MinecraftLogger plugin;

    public AutoTeleportListener(MinecraftLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        teleportToGeneration(player, "unverified");
    }

    public void teleportToGeneration(Player player, String type) {
        FileConfiguration config = plugin.getConfig();
        String path = "spawn." + type;

        String worldName = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage("§cGeneration location world not found for: " + type);
            return;
        }

        Location loc = new Location(world, x, y, z, yaw, pitch);
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.teleport(loc), 5L);
    }
}
