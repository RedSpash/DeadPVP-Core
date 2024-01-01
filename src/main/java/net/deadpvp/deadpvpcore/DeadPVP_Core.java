package net.deadpvp.deadpvpcore;

import net.deadpvp.deadpvpcore.listener.CommandProcessEvent;
import net.deadpvp.deadpvpcore.listener.PlayerListener;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.rank.RankManager;
import net.deadpvp.deadpvpcore.scoreboard.ScoreboardManager;
import net.deadpvp.deadpvpcore.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeadPVP_Core extends JavaPlugin {

    @Override
    public void onEnable() {
        SQLManager sqlManager = new SQLManager();
        RankManager rankManager = new RankManager(sqlManager);
        PlayerManager playerManager = new PlayerManager(rankManager);
        ScoreboardManager scoreboardManager = new ScoreboardManager(playerManager);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(playerManager,rankManager,scoreboardManager),this);
        Bukkit.getPluginManager().registerEvents(new CommandProcessEvent(),this);

        for(Player p : Bukkit.getOnlinePlayers()){
            playerManager.insertIfNotExists(p);
            scoreboardManager.update(p);
        }
    }

    @Override
    public void onDisable() {

    }
}
