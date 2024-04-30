package org.kauazs.arena.state;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.kauazs.Pvp;
import org.kauazs.arena.ArenaState;
import org.kauazs.managers.Arena;
import org.kauazs.managers.ArenaStates;
import org.kauazs.utils.PlayerUtils;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.kauazs.utils.Colorize.format;

public class StartingArena {
    private int counter = 6;
    private int taskId;
    public StartingArena(Arena arena) {

        BukkitScheduler scheduler = Pvp.getInstance().getServer().getScheduler();
        taskId = scheduler.scheduleSyncRepeatingTask(Pvp.getInstance(), new Runnable() {
        @Override
        public void run() {
                counter--;
                if (counter <= 5 && counter > 0) {
                    arena.sendMessagePlayers("&ePartida iniciando em &a"+ counter + " &esegundos");
                    if (counter <= 3) {
                        for (UUID id : arena.getPlayers() ) {
                            Player p = Pvp.getInstance().getServer().getPlayer(id);
                            p.sendTitle(format("&c" + counter), "");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 100.0F, 0);
                            arena.updateScoreboard("", format("Iniciando em &a" + counter + "s"));
                        }
                    }

                } else if (arena.getArenaState().equals(ArenaStates.STARTING)) {
                    PlayerUtils utils = new PlayerUtils(arena);
                    utils.giveItems();
                    arena.setArenaState(ArenaStates.BATTLE);
                } else if (arena.getArenaState().equals(ArenaStates.BATTLE)) {
                    String lines = "";
                    for (UUID id: arena.getPlayers()) {
                        Player p = Pvp.getInstance().getServer().getPlayer(id);

                        String health = String.format("%.2f", p.getHealth());
                        String fixed = health.substring(0, Math.min(health.length(), 2));

                        lines += format("&e" + p.getDisplayName() + ": &c❤ " + fixed);
                    }
                    arena.updateScoreboard("", lines);
                } else if (arena.getArenaState().equals(ArenaStates.WAITING)) {
                    scheduler.cancelTask(taskId);
                }
            }
        }, 0L, 20L);
    }

}
