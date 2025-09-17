package org.dygoz.minecraftLogger.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.dygoz.minecraftLogger.MinecraftLogger;
import org.dygoz.minecraftLogger.config.ConfigManager;
import org.dygoz.minecraftLogger.database.MySQLManager;

public class PlayerJoinListener implements Listener {

    private final MinecraftLogger plugin;
    private final Set<UUID> verifyingPlayers = new HashSet<>();

    public PlayerJoinListener(MinecraftLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MySQLManager mysql = plugin.getMySQL();

        Location spawnNotVerified = ConfigManager.getSpawnNotVerified();
        if (spawnNotVerified != null) {
            player.teleport(spawnNotVerified);
        }

        String titlePre = ConfigManager.getMessage("title.pre_verification");
        String subtitlePre = ConfigManager.getMessage("title.pre_verification_subtitle");
        int fadeIn = ConfigManager.getTitleFadeIn();
        int stay = ConfigManager.getTitleStay();
        int fadeOut = ConfigManager.getTitleFadeOut();
        player.sendTitle(titlePre, subtitlePre, fadeIn, stay, fadeOut);

        verifyingPlayers.add(player.getUniqueId());

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000, 1, false, false, false));

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            boolean verified = mysql.isPlayerVerified(player.getName());

            verifyingPlayers.remove(player.getUniqueId());
            player.removePotionEffect(PotionEffectType.BLINDNESS);

            if (verified) {
                Location spawnVerified = ConfigManager.getSpawnVerified();
                if (spawnVerified != null) {
                    player.teleport(spawnVerified);
                }

                String titlePost = ConfigManager.getMessage("title.post_verification");
                String subtitlePost = ConfigManager.getMessage("title.post_verification_subtitle");
                player.sendTitle(titlePost, subtitlePost, fadeIn, stay, fadeOut);

            } else {
                String kickMsg = ConfigManager.getMessage("kick.not_verified");
                player.kickPlayer(kickMsg);
            }
        }, 60L);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (verifyingPlayers.contains(event.getPlayer().getUniqueId())) {
            if (event.getFrom().distanceSquared(event.getTo()) > 0) {
                event.setTo(event.getFrom());
            }
        }
    }
}
