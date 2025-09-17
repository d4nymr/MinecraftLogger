
package org.dygoz.minecraftLogger;

import org.bukkit.plugin.java.JavaPlugin;
import org.dygoz.minecraftLogger.commands.SetGenerationCommand;
import org.dygoz.minecraftLogger.database.MySQLManager;
import org.dygoz.minecraftLogger.listener.PlayerJoinListener;
import org.dygoz.minecraftLogger.config.ConfigManager;

public class MinecraftLogger extends JavaPlugin {

    private static MinecraftLogger instance;
    private MySQLManager mysql;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.setup(this);

        this.mysql = new MySQLManager(
                ConfigManager.getDbHost(),
                ConfigManager.getDbPort(),
                ConfigManager.getDbDatabase(),
                ConfigManager.getDbUser(),
                ConfigManager.getDbPassword()
        );
        this.mysql.connect();
        this.getCommand("setgeneration").setExecutor(new SetGenerationCommand(this));
        this.getCommand("setgeneration").setTabCompleter(new SetGenerationCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);


        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        getLogger().info("-----------MinecraftLogger-----------");
        getLogger().info("MinecraftLogger is enabling...");
        getLogger().info("  __  __ _____ _   _ ______ _____ _____            ______ _______ _      ____   _____  _____ ______ _____ ");
        getLogger().info(" |  \\/  |_   _| \\ | |  ____/ ____|  __ \\     /\\   |  ____|__   __| |    / __ \\ / ____|/ ____|  ____|  __ \\ ");
        getLogger().info(" | \\  / | | | |  \\| | |__ | |    | |__) |   /  \\  | |__     | |  | |   | |  | | |  __| |  __| |__  | |__) |");
        getLogger().info(" | |\\/| | | | | . ` |  __|| |    |  _  /   / /\\ \\ |  __|    | |  | |   | |  | | | |_ | | |_ |  __| |  _  / ");
        getLogger().info(" | |  | |_| |_| |\\  | |___| |____| | \\ \\  / ____ \\| |       | |  | |___| |__| | |__| | |__| | |____| | \\ \\ ");
        getLogger().info(" |_|  |_|_____|_| \\_|______\\_____|_|  \\_\\/_/    \\_\\_|       |_|  |______\\____/ \\_____|\\_____|______|_|  \\_\\");
        getLogger().info("Created: D4nymr");
        getLogger().info("-----------MinecraftLogger-----------");

    }

    @Override
    public void onDisable() {
        if (this.mysql != null) {
            this.mysql.disconnect();
        }
        getLogger().info("-----------MinecraftLogger-----------");
        getLogger().info("MinecraftLogger is disabling...");
        getLogger().info("  __  __ _____ _   _ ______ _____ _____            ______ _______ _      ____   _____  _____ ______ _____ ");
        getLogger().info(" |  \\/  |_   _| \\ | |  ____/ ____|  __ \\     /\\   |  ____|__   __| |    / __ \\ / ____|/ ____|  ____|  __ \\ ");
        getLogger().info(" | \\  / | | | |  \\| | |__ | |    | |__) |   /  \\  | |__     | |  | |   | |  | | |  __| |  __| |__  | |__) |");
        getLogger().info(" | |\\/| | | | | . ` |  __|| |    |  _  /   / /\\ \\ |  __|    | |  | |   | |  | | | |_ | | |_ |  __| |  _  / ");
        getLogger().info(" | |  | |_| |_| |\\  | |___| |____| | \\ \\  / ____ \\| |       | |  | |___| |__| | |__| | |__| | |____| | \\ \\ ");
        getLogger().info(" |_|  |_|_____|_| \\_|______\\_____|_|  \\_\\/_/    \\_\\_|       |_|  |______\\____/ \\_____|\\_____|______|_|  \\_\\");
        getLogger().info("Created: D4nymr");
        getLogger().info("-----------MinecraftLogger-----------");

    }

    public static MinecraftLogger getInstance() {
        return instance;
    }

    public MySQLManager getMySQL() {
        return mysql;
    }
}
