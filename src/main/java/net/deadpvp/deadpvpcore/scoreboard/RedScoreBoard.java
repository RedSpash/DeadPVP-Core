package net.deadpvp.deadpvpcore.scoreboard;

import net.deadpvp.deadpvpcore.DeadPvpPlayer;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class RedScoreBoard {

    private final Scoreboard board;
    private final PlayerManager playerManager;

    public RedScoreBoard(Player p, PlayerManager playerManager){
        this.board = p.getScoreboard();
        this.playerManager = playerManager;
        this.update();
    }

    void update() {
        for(Player p : Bukkit.getOnlinePlayers()){
            DeadPvpPlayer deadPvpPlayer = this.playerManager.getData(p.getUniqueId());
            this.setTeam(p,deadPvpPlayer.getRankPower(),deadPvpPlayer.getPrefix(), deadPvpPlayer.getPlayerRank());
        }
    }

    public void setTeam(Player p,int power, String teamName, Rank rank){
        Team team = this.board.getTeam(power+"-"+teamName);
        if(team == null){
            team = this.board.registerNewTeam(power+"-"+teamName);
            team.setCanSeeFriendlyInvisibles(false);
        }
        String prefix = rank.rankToFormat();
        if(!prefix.isEmpty()){
            prefix = prefix + " ";
        }
        if(!team.getPrefix().equals(prefix)){
            team.setPrefix(prefix);
            ChatColor color = null;
            for(ChatColor chatColor : ChatColor.values()){
                if(("§"+chatColor.getChar()).equals(rank.getColor())){
                    color = chatColor;
                    break;
                }
            }
            if(color != null){
                team.setColor(color);
            }
        }
        team.addEntry(p.getName());
    }

    public Scoreboard getBoard() {
        return board;
    }
}