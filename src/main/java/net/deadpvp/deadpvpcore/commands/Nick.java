package net.deadpvp.deadpvpcore.commands;

import net.deadpvp.deadpvpcore.DeadPVPCore;
import net.deadpvp.deadpvpcore.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class Nick implements CommandExecutor {

    private final DeadPVPCore deadPVPCore;
    private final ScoreboardManager scoreboardManager;

    public Nick(DeadPVPCore deadPVPCore, ScoreboardManager scoreboardManager) {
        this.deadPVPCore = deadPVPCore;
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1 && commandSender instanceof Player p){
            //p.setPlayerListName(strings[0]);
            p.setDisplayName(strings[0]);
            this.changeName(p,strings[0],false);
            p.sendMessage("§aPseudo changé en "+strings[0]);

            scoreboardManager.updateAll();
        }
        return false;
    }

    public void changeName(Player player, String name, boolean disabling) {
        try {
            Method getProfile = player.getClass().getMethod("getProfile");
            getProfile.setAccessible(true);
            Object profile = getProfile.invoke(player);
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, name);
            if (!disabling) {
                Iterator<? extends Player> var6 = Bukkit.getOnlinePlayers().iterator();

                while(var6.hasNext()) {
                    Player p = var6.next();
                    if (p.canSee(player)) {
                        p.hidePlayer(this.deadPVPCore, player);
                        p.showPlayer(this.deadPVPCore, player);
                    }
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException |
                 NoSuchFieldException | NoSuchMethodException | InvocationTargetException var8) {
            var8.printStackTrace();
        }

    }
}
