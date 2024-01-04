package net.deadpvp.deadpvpcore.scoreboard;

import net.deadpvp.deadpvpcore.DeadPvpPlayer;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardManager implements Runnable{

    private final HashMap<UUID,RedScoreBoard> scoreBoardHashMap;
    private final PlayerManager playerManager;
    private final VanishManager vanishManager;

    public ScoreboardManager(PlayerManager playerManager, VanishManager vanishManager) {
        this.playerManager = playerManager;
        this.vanishManager = vanishManager;
        this.scoreBoardHashMap = new HashMap<>();
    }

    public void update(Player p) {
        if(!this.scoreBoardHashMap.containsKey(p.getUniqueId())){
            this.scoreBoardHashMap.put(p.getUniqueId(),new RedScoreBoard(p));
        }else{
            this.update(this.scoreBoardHashMap.get(p.getUniqueId()));
        }
    }

    private void update(RedScoreBoard redScoreBoard) {
        for(Player p : Bukkit.getOnlinePlayers()){
            DeadPvpPlayer deadPvpPlayer = this.playerManager.getData(p.getUniqueId());
            if(this.vanishManager.isVanish(deadPvpPlayer.getUuid())){
                redScoreBoard.setTeam(p,deadPvpPlayer.getRankPower(),"vanish","§7[VANISH]", ChatColor.WHITE);
            }else{
                ChatColor color = null;
                for(ChatColor chatColor : ChatColor.values()){
                    if(("§"+chatColor.getChar()).equals(deadPvpPlayer.getRankColor())){
                        color = chatColor;
                        break;
                    }
                }
                redScoreBoard.setTeam(p,deadPvpPlayer.getRankPower(),deadPvpPlayer.getPrefix(), deadPvpPlayer.getPlayerRank().rankToFormat(),color);
            }
        }
    }

    public void updateAll() {
        for(RedScoreBoard scoreBoard : this.scoreBoardHashMap.values()){
            this.update(scoreBoard);
        }
    }

    public void removePlayer(Player p) {
        this.scoreBoardHashMap.remove(p.getUniqueId());
    }

    @Override
    public void run() {
        for(RedScoreBoard scoreBoard : this.scoreBoardHashMap.values()){
            this.update(scoreBoard);
        }
    }
}
