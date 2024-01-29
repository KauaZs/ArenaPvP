package org.kauazs.managers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.kauazs.arena.ArenaState;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Arena {
    private String arenaId;
    private Location spawnOne;
    private Location spawnTwo;

    private ArenaState arenaState;
    private List<UUID> players;

    public void setState(ArenaState arenaState) {
        if(this.arenaState.getClass() == arenaState.getClass()) return;

        this.arenaState = arenaState;
    }
}
