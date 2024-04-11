package org.kauazs.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kauazs.Pvp;
import org.kauazs.managers.Arena;
import org.kauazs.managers.ArenaManager;

import java.util.HashMap;

import static org.kauazs.utils.Colorize.sendMessage;
import static org.kauazs.utils.LocationUtils.getFromLocation;

public class PlayArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas players podem executar este comando");
        }
        Player p = (Player) sender;
        Arena arena = new ArenaManager().findArenaEmpty();
        if (arena == null) {
            sendMessage(p, "&cNenhuma arena foi encontrada");
        }
        Location pos1 = getFromLocation(p.getWorld(), arena.getSpawnOne());
        p.teleport(pos1);

        sendMessage(p, "&aEnviando voce para " + arena.getArenaId() + "...");
        arena.addPlayer(p);
        return true;
    }
}
