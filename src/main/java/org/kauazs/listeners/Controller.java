package org.kauazs.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kauazs.Pvp;
import org.kauazs.arena.state.FinishedArena;
import org.kauazs.managers.Arena;
import org.kauazs.managers.ArenaStates;
import org.kauazs.managers.ScoreBoard;

import java.util.UUID;
import java.util.stream.Stream;

import static org.kauazs.utils.Colorize.sendMessage;
import static org.kauazs.utils.PlayerUtils.getArena;
import static org.kauazs.utils.PlayerUtils.inArena;


public class Controller implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent block) {
        Player p = block.getPlayer();
        if (inArena(p)) {
            Arena arena = getArena(p);
            if (!arena.getBlocks().contains(block.getBlock())) {
                block.setCancelled(true);
                sendMessage(p, "&cVoce só pode quebrar blocos colocados por players.");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent block) {
        Player p = block.getPlayer();
        if (inArena(p)) {
            Arena arena = getArena(p);
            arena.addBlock(block.getBlock());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        new ScoreBoard(p).removeScoreboard();
        if (inArena(p)) {
            Arena arena = getArena(p);
            arena.removePlayer(p);
            if (arena.getArenaState().equals(ArenaStates.BATTLE)) {
                UUID opponent = arena.getPlayers().stream().filter(x -> !x.equals(p.getUniqueId()))
                        .findFirst()
                        .orElse(null);
                Player winner = Pvp.getInstance().getServer().getPlayer(opponent);
                new FinishedArena(arena, winner);
            }
        }
    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player p = (Player) event.getEntity();
            if (!inArena(p)) return;
            Arena arena = getArena(p);

            if (arena.getArenaState().equals(ArenaStates.STARTING)) {
                event.setCancelled(true);
            }

            double damage = event.getFinalDamage();

            if ((p.getHealth() - damage) <= 0) {

                event.setCancelled(true);
                UUID opponent = arena.getPlayers().stream().filter(x -> !x.equals(p.getUniqueId()))
                        .findFirst()
                        .orElse(null);

                Player winner = Pvp.getInstance().getServer().getPlayer(opponent);

                new FinishedArena(arena, winner);
            }
        }
    }
    @EventHandler
    public void shootBowArrow(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) return;

        Player victim = (Player) event.getEntity();
        Player shooter = (Player) ((Arrow) event.getDamager()).getShooter();

        String health = String.format("%.2f", victim.getHealth());
        String fixed = health.substring(0, Math.min(health.length(), 2));
        if (inArena(shooter) && inArena(victim)) {
            sendMessage(shooter, "&eVocê acertou a flecha em&a " + victim.getName() + " &ee ficou com &c" + fixed + "❤ de vida");
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player p = (Player) e.getEntity();
        if (inArena(p)) {
            e.setCancelled(true);
            e.setFoodLevel(20);

        }
    }

}
