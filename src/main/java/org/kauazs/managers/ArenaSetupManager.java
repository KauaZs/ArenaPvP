package org.kauazs.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.kauazs.Pvp;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ArenaSetupManager {
    private HashMap<Player, Integer> setup;

    public ArenaSetupManager(HashMap<Player, Integer> setup) {
        this.setup = setup;
    }

    File file = new File(Pvp.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


    public void startSetup(Player p) {
        ConfigurationSection configurationSection = config.getConfigurationSection("arena");
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

        String str = String.format("arena.%s.pos_%s", ID, opponentNumber);
        String pos = String.format("%s, %s, %s, %s", posX, posY, posZ, posD);
        config.set(str, pos);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
