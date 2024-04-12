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
import static org.kauazs.utils.PlayerUtils.inArena;

public class PlayArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas players podem executar este comando");
        }
        Player p = (Player) sender;

        if (inArena(p)) {
            sendMessage(p, "&cVoce já está em uma arena!");
            return true;
        }

        if (Pvp.getArenas().size() <= 0) {
            sendMessage(p, "&cNenhuma arena foi encontrada");
            return true;
        }

        Arena arenaEmpty = new ArenaManager().findArenaEmpty();
        Arena arenaWithPlayers = new ArenaManager().findArenaWithPlayers();

        Arena arena = arenaWithPlayers != null ? arenaWithPlayers : arenaEmpty;
        if (arena == null) {
            sendMessage(p, "&cNenhuma arena foi encontrada");
        }
        Location pos1 = getFromLocation(arena.getSpawnOne());
        p.teleport(pos1);

        sendMessage(p, "&aEnviando voce para " + arena.getArenaId() + "...");
        arena.addPlayer(p);
        return true;
    }
}
