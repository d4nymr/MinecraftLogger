package org.dygoz.minecraftLogger.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static FileConfiguration config;
    private static FileConfiguration messages;

    public static void setup(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public static String getDbHost() {
        return config.getString("database.host");
    }

    public static int getDbPort() {
        return config.getInt("database.port");
    }

    public static String getDbDatabase() {
        return config.getString("database.name");
    }

    public static String getDbUser() {
        return config.getString("database.user");
    }

    public static String getDbPassword() {
        return config.getString("database.password");
    }

    public static Location getSpawnNotVerified() {
        double x = config.getDouble("spawn.unverified.x");
        double y = config.getDouble("spawn.unverified.y");
        double z = config.getDouble("spawn.unverified.z");
        float yaw = (float) config.getDouble("spawn.unverified.yaw");
        float pitch = (float) config.getDouble("spawn.unverified.pitch");
        String world = config.getString("spawn.unverified.world");

        if (world == null || org.bukkit.Bukkit.getWorld(world) == null) return null;

        return new Location(org.bukkit.Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public static Location getSpawnVerified() {
        double x = config.getDouble("spawn.verified.x");
        double y = config.getDouble("spawn.verified.y");
        double z = config.getDouble("spawn.verified.z");
        float yaw = (float) config.getDouble("spawn.verified.yaw");
        float pitch = (float) config.getDouble("spawn.verified.pitch");
        String world = config.getString("spawn.verified.world");

        if (world == null || org.bukkit.Bukkit.getWorld(world) == null) return null;

        return new Location(org.bukkit.Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }


    public static int getTitleFadeIn() {
        return config.getInt("title.fade_in", 10);
    }

    public static int getTitleStay() {
        return config.getInt("title.stay", 70);
    }

    public static int getTitleFadeOut() {
        return config.getInt("title.fade_out", 20);
    }

    public static String getMessage(String path) {
        // cerca in messages.yml
        return messages.getString(path, "Message not set: " + path);
    }
}
