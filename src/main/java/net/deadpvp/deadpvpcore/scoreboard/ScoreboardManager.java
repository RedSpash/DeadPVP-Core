package net.deadpvp.deadpvpcore.scoreboard;

import net.deadpvp.deadpvpcore.players.PlayerManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager {

    private final HashMap<UUID,RedScoreBoard> scoreBoardHashMap;
    private final PlayerManager playerManager;

    public ScoreboardManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
        this.scoreBoardHashMap = new HashMap<>();
    }

    public void update(Player p) {
        if(!this.scoreBoardHashMap.containsKey(p.getUniqueId())){
            this.scoreBoardHashMap.put(p.getUniqueId(),new RedScoreBoard(p,this.playerManager));
        }else{
            this.scoreBoardHashMap.get(p.getUniqueId()).update();
        }
    }

    public void updateAll() {
        for(RedScoreBoard scoreBoard : this.scoreBoardHashMap.values()){
            scoreBoard.update();
        }
    }

    public void removePlayer(Player p) {
        this.scoreBoardHashMap.remove(p.getUniqueId());
    }
}
