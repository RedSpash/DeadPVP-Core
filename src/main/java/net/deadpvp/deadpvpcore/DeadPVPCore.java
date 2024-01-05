package net.deadpvp.deadpvpcore;

import net.deadpvp.deadpvpcore.commands.*;
import net.deadpvp.deadpvpcore.listener.CommandProcessEvent;
import net.deadpvp.deadpvpcore.listener.PlayerListener;
import net.deadpvp.deadpvpcore.players.PlayerManager;
import net.deadpvp.deadpvpcore.rank.RankManager;
import net.deadpvp.deadpvpcore.scoreboard.ScoreboardManager;
import net.deadpvp.deadpvpcore.sql.SQLManager;
import net.deadpvp.deadpvpcore.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeadPVPCore extends JavaPlugin {

    private VanishManager vanishManager;

    @Override
    public void onEnable() {
        SQLManager sqlManager = new SQLManager();
        RankManager rankManager = new RankManager(sqlManager);
        PlayerManager playerManager = new PlayerManager(rankManager);
        this.vanishManager = new VanishManager(this);
        ScoreboardManager scoreboardManager = new ScoreboardManager(playerManager, this.vanishManager);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(playerManager,rankManager,scoreboardManager),this);
        Bukkit.getPluginManager().registerEvents(new CommandProcessEvent(),this);

        Bukkit.getScheduler().runTaskTimer(this,scoreboardManager,20*5,20*5);

        getCommand("vanish").setExecutor(new Vanish(this.vanishManager));
        getCommand("god").setExecutor(new God());
        getCommand("enderchest").setExecutor(new Enderchest());
        getCommand("feed").setExecutor(new Feed());
        getCommand("heal").setExecutor(new Heal());
        getCommand("gmc").setExecutor(new GamemodeShortCut(GameMode.CREATIVE));
        getCommand("gmsp").setExecutor(new GamemodeShortCut(GameMode.SPECTATOR));
        getCommand("gma").setExecutor(new GamemodeShortCut(GameMode.ADVENTURE));
        getCommand("gms").setExecutor(new GamemodeShortCut(GameMode.SURVIVAL));
        getCommand("getpos").setExecutor(new GetPos());
        getCommand("ptime").setExecutor(new PTime());
        getCommand("pweather").setExecutor(new PWeather());

        getCommand("nick").setExecutor(new Nick(this, scoreboardManager));

        for(Player p : Bukkit.getOnlinePlayers()){
            playerManager.insertIfNotExists(p);
            scoreboardManager.update(p);
        }
    }

    @Override
    public void onDisable() {
        this.vanishManager.disabledVanish();
    }
}
