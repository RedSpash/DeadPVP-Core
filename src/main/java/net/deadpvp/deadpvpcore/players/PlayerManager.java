package net.deadpvp.deadpvpcore.players;

import net.deadpvp.deadpvpcore.DeadPvpPlayer;
import net.deadpvp.deadpvpcore.rank.RankManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final HashMap<UUID, DeadPvpPlayer> playerData;
    private final RankManager rankManager;

    public PlayerManager(RankManager rankManager){
        this.playerData = new HashMap<>();
        this.rankManager = rankManager;
    }

    public void insertNewPlayer(Player p){
        this.insertNewPlayer(p.getUniqueId());
    }

    public void insertNewPlayer(UUID uuid){
        this.playerData.put(uuid,new DeadPvpPlayer(uuid,this.rankManager));
    }

    public DeadPvpPlayer getData(UUID uuid){
        if(!this.playerData.containsKey(uuid)) {
            this.insertNewPlayer(uuid);
        }
        return this.playerData.getOrDefault(uuid,null);
    }

    public void removePlayer(Player player) {
        this.playerData.remove(player.getUniqueId());
    }

    public void updateData() {
        for(DeadPvpPlayer deadPvpPlayer : this.playerData.values()){
            deadPvpPlayer.updateData();
        }
    }

    public void insertIfNotExists(Player p) {
        if(!this.playerData.containsKey(p.getUniqueId())){
            this.insertNewPlayer(p.getUniqueId());
        }
    }
}
