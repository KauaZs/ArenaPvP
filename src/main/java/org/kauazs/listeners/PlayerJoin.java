package org.kauazs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kauazs.Pvp;
import org.kauazs.utils.Colorize;
import org.kauazs.utils.LocationUtils;

import java.io.File;

public class PlayerJoin implements Listener {

    File file = new File(Pvp.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + player.getName() + Colorize.format(" &eentrou no servidor"));

        String spawn = config.getString("lobby.location");
        Location local = LocationUtils.getFromLocation(player.getWorld(), spawn);

        player.teleport(local);
        player.setGameMode(GameMode.ADVENTURE);

    }

}
