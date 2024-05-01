package org.kauazs.managers;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.kauazs.utils.Colorize.format;

public class ScoreBoard {
    private Map<UUID, FastBoard> boards = new HashMap<>();
    private Player player;
    public ScoreBoard(Player player) {
        this.player = player;
    }

    public void createScoreboard() {
        FastBoard fastBoard = new FastBoard(this.player);
        fastBoard.updateTitle(format("&e&lArena PvP"));
        boards.put(this.player.getUniqueId(), fastBoard);
    }

    public boolean hasScoreboard() {
        return boards.get(this.player.getUniqueId()) != null ? true : false;
    }

    public void updateScoreboard(String ...lines) {
        if (!this.hasScoreboard()) {
            this.createScoreboard();
        }

        FastBoard board = boards.get(this.player.getUniqueId());
        board.updateLines(lines);
    }


    public void removeScoreboard() {
        this.boards.remove(this.player.getUniqueId());
    }
}
