package org.kauazs.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.kauazs.Pvp;
import org.kauazs.arena.ArenaState;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ArenaSetupManager {
    private HashMap<Player, Integer> setup;
    private HashMap<String, Arena> arenas;
    public ArenaSetupManager(HashMap<Player, Integer> setup) {
        this.setup = setup;
        this.arenas = new HashMap<>();
    }

    File file = new File(Pvp.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


    public void startSetup(Player p) {
        ConfigurationSection configurationSection = config.getConfigurationSection("arenas");
        int size = 0;
        if(configurationSection != null) {
             size = configurationSection.getKeys(false).size();
        }
        setup.put(p, size + 1);
    }
    public void setId(Player p, Integer i) {
        setup.replace(p, i);
    }
    public Integer getId(Player p) {
        Integer id = setup.get(p);
        return id;
    }
    public void addArenaPos(int ID, Location local, int opponentNumber) {
        Double posX = local.getX();
        Double posY = local.getY();
        Double posZ = local.getZ();
        Vector posD = local.getDirection();

        String str = String.format("arenas.cage_%s.pos_%s", ID, opponentNumber);
        String pos = String.format("%s, %s, %s, %s, %s", posX, posY, posZ, posD, local.getWorld());
        config.set(str, pos);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadArenasFromConfig() {
        ConfigurationSection arenaSection = config.getConfigurationSection("arenas");
        if(arenaSection != null) {
            for(String id: arenaSection.getKeys(false)) {
                ConfigurationSection section = (ConfigurationSection) arenaSection.get(id);
                String pos_1 = (String) section.get("pos_1");
                String pos_2 = (String) section.get("pos_2");

                Arena arena = new Arena();
                arena.setArenaId(id);
                arena.setArenaState(ArenaStates.WAITING);
                arena.setSpawnOne(pos_1);
                arena.setSpawnTwo(pos_2);
                arena.setPlayers(new ArrayList<>());

                arenas.put(id, arena);
            }
        }
    }

    public HashMap<String, Arena> getArenas() {
        return arenas;
    }
}
