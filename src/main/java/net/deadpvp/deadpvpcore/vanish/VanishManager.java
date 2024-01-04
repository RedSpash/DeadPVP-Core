package net.deadpvp.deadpvpcore.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VanishManager {

    private final ArrayList<UUID> vanishedPlayers;
    private final Plugin deadpvp_core;

    public VanishManager(Plugin deadpvpCore) {
        deadpvp_core = deadpvpCore;
        this.vanishedPlayers = new ArrayList<>();
    }

    public void vanish(Player p){
        this.vanishedPlayers.add(p.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().equals(p.getUniqueId())){
                pl.hidePlayer(this.deadpvp_core,p);
            }
        }
    }

    public void unVanish(Player p){
        this.vanishedPlayers.remove(p.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().equals(p.getUniqueId())){
                pl.showPlayer(this.deadpvp_core,p);
            }
        }
    }

    public void updateVanishedPlayers(Player p){
        for(UUID uuid : this.vanishedPlayers){
            Player vanishedPlayer = Bukkit.getPlayer(uuid);
            if(vanishedPlayer != null && vanishedPlayer.isOnline()){
                p.hidePlayer(this.deadpvp_core,vanishedPlayer);
            }
        }
    }

    public List<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    public void shutdownPlugin(){

    }
}
