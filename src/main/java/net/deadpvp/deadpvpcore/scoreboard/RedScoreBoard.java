package net.deadpvp.deadpvpcore.scoreboard;

import net.deadpvp.deadpvpcore.DeadPvpPlayer;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.rank.Rank;
import net.deadpvp.deadpvpcore.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class RedScoreBoard {

    private Scoreboard board;
    private final UUID playerUUID;

    public RedScoreBoard(Player p){
        this.board = p.getScoreboard();
        this.playerUUID = p.getUniqueId();
    }


    public void setTeam(Player p, int power, String teamName, String prefix, ChatColor teamColor){
        this.checkBoard();

        Team team = this.board.getTeam(power+"-"+teamName);
        if(team == null){
            team = this.board.registerNewTeam(power+"-"+teamName);
            team.setCanSeeFriendlyInvisibles(false);
        }
        if(prefix.length() != 2){
            prefix = prefix + " ";
        }
        if(!team.getPrefix().equals(prefix)){
            Bukkit.broadcastMessage("changed");
            team.setPrefix(prefix);
            if(teamColor != null){
                team.setColor(teamColor);
            }
        }
        if(!team.hasEntry(p.getName())){
            Bukkit.broadcastMessage("added "+p.getName()+" to entries");
            team.addEntry(p.getName());
        }
    }

    private void checkBoard() {
        Player p = Bukkit.getPlayer(playerUUID);
        if(p != null && p.isOnline() && this.board != p.getScoreboard()){
            this.board = p.getScoreboard();
            Bukkit.broadcastMessage("updated");
        }
    }
}