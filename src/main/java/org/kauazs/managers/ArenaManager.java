package org.kauazs.managers;

import org.kauazs.Pvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ArenaManager {
    private HashMap<String, Arena> arenas;
    public ArenaManager() {
        this.arenas = Pvp.getArenas();
    }

    public Arena findArenaEmpty() {

        List<Arena> emptyArenas = new ArrayList<>();
        for(Arena arena : this.arenas.values()) {
            if (arena.getPlayers().size() < 2 && arena.getArenaState().equals(ArenaStates.WAITING)) {
                emptyArenas.add(arena);
            }
        }
        if (!emptyArenas.isEmpty()) {
            Random random = new Random();
            return emptyArenas.get(random.nextInt(emptyArenas.size()));
        }
            return null;
        }


    public Arena findArenaWithPlayers() {
        List<Arena> arenasWithPlayers = new ArrayList<>();
        for (Arena arena : this.arenas.values()) {
            if (!arena.getPlayers().isEmpty() && arena.getArenaState().equals(ArenaStates.WAITING)) {
                arenasWithPlayers.add(arena);
            }
        }
        if (!arenasWithPlayers.isEmpty()) {
            Random random = new Random();
            return arenasWithPlayers.get(random.nextInt(arenasWithPlayers.size()));
        } else {
            return null;
        }
    }

}
