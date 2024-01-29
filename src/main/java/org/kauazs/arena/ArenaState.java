package org.kauazs.arena;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.kauazs.Pvp;

public abstract class ArenaState implements Listener {
    public void onEnbale(Pvp plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable(Pvp plugin) {
        HandlerList.unregisterAll(this);
    }


}
