package org.kauazs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.kauazs.Pvp;
import org.kauazs.utils.Colorize;

import java.io.File;
import java.io.IOException;

import static org.kauazs.utils.LocationUtils.getLocation;

public class SpawnSets implements CommandExecutor {

    File file = new File(Pvp.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length == 0) {
            Colorize.sendMessage(sender, "&c/set [spawn|]");
        }

        Player player = (Player) sender;

        if(args[0].equals("spawn")) {

            config.set("lobby.location", getLocation(player.getLocation()));
            try {
                config.save(file);
                player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "SPAWN:" + ChatColor.RESET + " Local de nascimento definido com sucesso!");
            } catch (IOException e) {
                player.sendMessage("Ocorreu um erro ao tentar salvar o local de nascimento.");
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
