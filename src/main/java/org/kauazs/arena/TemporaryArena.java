package org.kauazs.arena;

import org.bukkit.Location;
import org.kauazs.arena.state.DefaultArenaState;
import org.kauazs.managers.Arena;

import java.util.ArrayList;

public class TemporaryArena {
    private String arenaId;
    private Location spawnOne;
    private Location spawnTwo;

    public TemporaryArena(String arenaId) {
        this.arenaId = arenaId;
        this.spawnOne = null;
        this.spawnTwo = null;
    }

    public Arena toArena() {
        if(spawnOne == null || spawnTwo == null) {
            return null;
        }

        return new Arena(arenaId, spawnOne, spawnTwo, new DefaultArenaState(), new ArrayList<>());
    }

}
