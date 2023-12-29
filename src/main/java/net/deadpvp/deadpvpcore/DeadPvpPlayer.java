package net.deadpvp.deadpvpcore;

import net.deadpvp.deadpvpcore.rank.Rank;
import net.deadpvp.deadpvpcore.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeadPvpPlayer {

    private final RankManager rankManager;
    private final UUID uuid;
    private Rank playerRank;

    public DeadPvpPlayer(UUID uuid, RankManager rankManager){
        this.uuid = uuid;
        this.rankManager = rankManager;

        this.updateData();
    }

    public void updateData() {
        Player p = Bukkit.getPlayer(this.uuid);

        if(p != null){
            for(Rank rank : this.rankManager.getRanks()){
                if(rank.hasPermission(p)){
                    this.playerRank = rank;
                    break;
                }
            }
        }
    }

    public String getRankColor() {
        return this.playerRank.getColor();
    }

    public String getPrefix() {
        return this.playerRank.getPrefix();
    }

    public String getLongRankName() {
        return this.playerRank.getLongName();
    }
    public int getRankPower(){return this.playerRank.getPower();}

    public UUID getUuid() {
        return uuid;
    }

    public String getRankWithColor() {
        return this.playerRank.getColor()+this.playerRank.getPrefix();
    }

    public Rank getPlayerRank() {
        return playerRank;
    }
}
