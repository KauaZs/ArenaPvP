package org.kauazs.managers;
import lombok.Data;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static org.kauazs.utils.Colorize.sendMessage;

@Data
public class Arena {
    private String arenaId;
    private String spawnOne;
    private String spawnTwo;

    private ArenaStates arenaState;
    private List<UUID> players;

    public void addPlayer(Player p) {
        if (players.size() < 2) {
            sendMessage(p, "$cEssa cage esta lotada");
        } else {
            players.add(p.getUniqueId());
        }
    }

    public void start() {

    }

}
