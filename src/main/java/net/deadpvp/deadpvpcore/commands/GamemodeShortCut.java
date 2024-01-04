package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeShortCut implements CommandExecutor {

    private final GameMode gameMode;

    public GamemodeShortCut(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 0){
                p.setGameMode(this.gameMode);
                return true;
            } else if (p.hasPermission("deadpvp_core.command.gmc.other")) {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target != null && !target.isOnline()){
                    target.setGameMode(this.gameMode);
                    target.sendMessage("§a"+p.getName()+" vient de passer votre mode de jeu en "+this.gameMode.name()+".");
                    p.sendMessage("§aVous venez de passer "+p.getName()+" en mode "+this.gameMode.name()+".");
                }else{
                    p.sendMessage("§cLe joueur est introuvable!");
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
