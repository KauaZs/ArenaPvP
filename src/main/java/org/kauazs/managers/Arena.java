package org.kauazs.managers;
import com.avaje.ebean.validation.NotNull;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.kauazs.Pvp;
import org.kauazs.arena.state.StartingArena;

import java.util.*;

import static org.kauazs.utils.Colorize.format;
import static org.kauazs.utils.Colorize.sendMessage;

@Data
public class Arena {
    private String arenaId;
    private String spawnOne;
    private String spawnTwo;

    private ArenaStates arenaState;
    private List<UUID> players;

    @Getter
    private List<Block> blocks = new ArrayList<>();
    private Map<UUID, FastBoard> boards = new HashMap<>();

    public void addPlayer(Player p) {
        if (players.size() > 2) {
            sendMessage(p, "&cEssa arena esta lotada");
            return;
        }
        if (players.contains(p)) {
            sendMessage(p, "&cVoce ja está conectado!");
            return;
        }

        players.add(p.getUniqueId());
        this.updateScoreboard("", "Aguardando players");
        if (players.size() >= 1) {
            this.start();
        }
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }
    public void removePlayer(Player p) {
        if (!(players.contains(p.getUniqueId()))) return;
        players.remove(p);
    }

    public void start() {
        this.setArenaState(ArenaStates.STARTING);
        new StartingArena(this);
    }

    public void sendMessagePlayers(String msg) {

        for (UUID id: players) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);
            p.sendMessage(format(msg));
        }
    }

    public void reset() {
        blocks.forEach(block -> {
            block.setType(Material.EMPTY_MAP, false);
        });
        this.players.clear();
        this.blocks.clear();
    }

    public void updateScoreboard(String ...lines) {
        for(UUID id: players) {
            Player p = Pvp.getInstance().getServer().getPlayer(id);

            FastBoard board = new FastBoard(p);
            board.updateTitle(format("&e&lArena PvP"));
            board.updateLines(lines);
        }
    }
}
