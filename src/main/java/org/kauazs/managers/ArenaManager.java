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

        if (this.arenas == null) {
            Pvp.getInstance().getServer().broadcastMessage("Arenas nao encontradas.");
        }
    }

    public Arena findArenaEmpty() {
        List<Arena> emptyArenas = new ArrayList<>();
        for(Arena arena : this.arenas.values()) {
            if (arena.getPlayers().size() < 2) {
                emptyArenas.add(arena);
            }
        }
        if (!emptyArenas.isEmpty()) {
            Random random = new Random();
            return emptyArenas.get(random.nextInt(emptyArenas.size()));
        }
        return null;
    }


}
