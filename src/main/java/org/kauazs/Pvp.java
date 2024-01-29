package org.kauazs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kauazs.commands.SetupArena;
import org.kauazs.commands.SpawnSets;
import org.kauazs.listeners.PlayerInteract;
import org.kauazs.listeners.PlayerJoin;
import org.kauazs.managers.ArenaSetupManager;

import java.util.HashMap;


public final class Pvp extends JavaPlugin {
    FileConfiguration fileConfiguration = getConfig();

    @Setter @Getter
    private static ArenaSetupManager arenaManager;

    @Setter @Getter
    private static Pvp instance;
    @Override
    public void onEnable() {
        setInstance(this);
        setArenaManager(new ArenaSetupManager(new HashMap<>()));
       getCommand("setup").setExecutor(new SetupArena());
       getCommand("set").setExecutor(new SpawnSets());

       getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
       getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
