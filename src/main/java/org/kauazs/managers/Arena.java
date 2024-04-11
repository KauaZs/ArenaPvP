package org.kauazs.managers;
import lombok.Data;
import org.bukkit.entity.Player;
import org.kauazs.Pvp;
import org.kauazs.arena.state.StartingArena;

import java.util.List;
import java.util.UUID;

import static org.kauazs.utils.Colorize.format;
import static org.kauazs.utils.Colorize.sendMessage;

@Data
public class Arena {
    private String arenaId;
    private String spawnOne;
    private String spawnTwo;

    private ArenaStates arenaState;
    private List<UUID> players;

    public void addPlayer(Player p) {
        if (players.size() > 2) {
            sendMessage(p, "&cEssa arena esta lotada");
            return;
        }
        if (players.contains(p)) {
            sendMessage(p, "&cVoce ja está conectado!");
            return;
        }

        players.add(p.getUniqueId());
        this.start();

    }

    public void removePlayer(Player p) {
        if (!(players.contains(p))) return;
        players.remove(p);
    }

    public void start() {
        this.setArenaState(ArenaStates.STARTING);
        new StartingArena(this);
    }

    public void sendMessagePlayers(String msg) {

        for (UUID id: players) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);
            p.sendMessage(format(msg));
        }
    }

}
