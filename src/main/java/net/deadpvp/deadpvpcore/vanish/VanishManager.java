package net.deadpvp.deadpvpcore.vanish;

import net.deadpvp.deadpvpcore.DeadPVPCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {

    private final ArrayList<UUID> vanishedPlayers;
    private final DeadPVPCore deadPVPCore;

    public VanishManager(DeadPVPCore deadPVPCore) {
        this.deadPVPCore = deadPVPCore;
        this.vanishedPlayers = new ArrayList<>();
    }

    public void vanish(Player p){
        this.vanishedPlayers.add(p.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().equals(p.getUniqueId())){
                if(!pl.hasPermission("deadpvp_core.vanish.show_players")){
                    pl.hidePlayer(this.deadPVPCore,p);
                }
            }
        }
    }

    public void unVanish(Player p){
        this.vanishedPlayers.remove(p.getUniqueId());
        for(Player pl : Bukkit.getOnlinePlayers()){
            if(!pl.getUniqueId().equals(p.getUniqueId())){
                pl.showPlayer(this.deadPVPCore,p);
            }
        }
    }

    public void updateVanishedPlayers(Player p){
        for(UUID uuid : this.vanishedPlayers){
            Player vanishedPlayer = Bukkit.getPlayer(uuid);
            if(vanishedPlayer != null && vanishedPlayer.isOnline()){
                p.hidePlayer(this.deadPVPCore,vanishedPlayer);
            }
        }
    }

    public void disabledVanish(){
        for(UUID uuid : this.vanishedPlayers){
            Player vanishedPlayer = Bukkit.getPlayer(uuid);
            if(vanishedPlayer != null && vanishedPlayer.isOnline()){
                this.unVanish(vanishedPlayer);
                vanishedPlayer.setGameMode(GameMode.SPECTATOR);
                vanishedPlayer.sendMessage("§cVotre vanish vient d'être retiré suite à un redémarrage du plugin. Vous passez donc en spectateur!");
            }
        }

    }

    public boolean isVanish(UUID uuid) {
        return this.vanishedPlayers.contains(uuid);
    }
}
