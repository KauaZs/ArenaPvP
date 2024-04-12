package org.kauazs.arena.state;

import jdk.javadoc.internal.doclets.formats.html.markup.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kauazs.Pvp;
import org.kauazs.managers.Arena;
import org.kauazs.managers.ArenaStates;

import java.awt.*;
import java.util.UUID;

import static org.kauazs.utils.Colorize.format;
import static org.kauazs.utils.PlayerUtils.resetPlayer;

public class FinishedArena {
    public  FinishedArena(Arena arena, Player winner) {
        arena.sendMessagePlayers("&l&l&aVitória - &r&6" + winner.getName() + " &evenceu a partida!");
        for (UUID id : arena.getPlayers()) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);
            resetPlayer(p);


            if (p.equals(winner)) {
                p.sendTitle(format("&aVITÓRIA!!"), "");
                p.playSound(p.getLocation(), Sound.CAT_MEOW, 100, 0);
            } else {
                p.sendTitle(format("&cDERROTA!!"), "");
            }
        }

        arena.setArenaState(ArenaStates.WAITING);
        arena.reset();

    }
}
