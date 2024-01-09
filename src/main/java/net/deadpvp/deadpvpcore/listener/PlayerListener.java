package net.deadpvp.deadpvpcore.listener;

import net.deadpvp.deadpvpcore.DeadPvpPlayer;
import net.deadpvp.deadpvpcore.scoreboard.ScoreboardManager;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.rank.RankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final PlayerManager playerManager;
    private final RankManager rankManager;
    private final ScoreboardManager scoreboardManager;

    public PlayerListener(PlayerManager playerManager, RankManager rankManager, ScoreboardManager scoreboardManager) {
        this.playerManager = playerManager;
        this.rankManager = rankManager;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        this.playerManager.insertIfNotExists(p);

        this.scoreboardManager.update(p);

        if(e.getJoinMessage() != null)return;

        DeadPvpPlayer deadPvpPlayer = this.playerManager.getData(p.getUniqueId());
        String rankIsFull = "";
        if(!deadPvpPlayer.getPlayerRank().getPrefix().isEmpty()){
            rankIsFull = " ";
        }
        e.setJoinMessage("§7[§a+§7] "+
                deadPvpPlayer.getPlayerRank().rankToFormat()+
                rankIsFull+
                p.getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(e.getQuitMessage() != null)return;

        DeadPvpPlayer deadPvpPlayer = this.playerManager.getData(p.getUniqueId());
        String rankIsFull = "";
        if(!deadPvpPlayer.getPlayerRank().getPrefix().isEmpty()){
            rankIsFull = " ";
        }
        e.setQuitMessage("§7[§c-§7] "+
                deadPvpPlayer.getPlayerRank().rankToFormat()+
                rankIsFull+
                p.getName());

        this.playerManager.removePlayer(p);
        this.scoreboardManager.removePlayer(p);
    }


    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        DeadPvpPlayer deadPvpPlayer = this.playerManager.getData(p.getUniqueId());
        String color = "§f";
        if(deadPvpPlayer.getPlayerRank().isDefaultRank()){
            color = "§7";
        }
        String playerSpace = "";
        if(deadPvpPlayer.getPlayerRank().getPrefix().isEmpty()){
            playerSpace = " ";
        }
        e.setFormat(
                this.rankManager.getChatFormat()
                .replace("{rankprefix}"+playerSpace, deadPvpPlayer.getPlayerRank().rankToFormat())
                .replace("{player}",p.getName())
                .replace("{message}", color+e.getMessage()
                )
        );

        scoreboardManager.updateAll();
    }

}
