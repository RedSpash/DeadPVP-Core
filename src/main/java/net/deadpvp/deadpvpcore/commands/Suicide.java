package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Suicide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            Player target = p;
            if(p.hasPermission("deadpvp_core.command.suicide.other") && strings.length >= 2){
                target = Bukkit.getPlayer(strings[0]);
                if(target == null || !target.isOnline()){
                    p.sendMessage("§cErreur: le joueur est introuvable!");
                    return true;
                }
            }

            if(!p.getUniqueId().equals(target.getUniqueId())){
                p.sendMessage("§a"+target.getName()+" vient de mourir!");
            }
            target.sendMessage("§aVous venez de mourir!");
            target.damage(target.getHealth());
        }
        return false;
    }
}
