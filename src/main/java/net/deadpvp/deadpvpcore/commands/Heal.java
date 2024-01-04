package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 0){
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                p.sendMessage("§aVous venez d'être heal!");
                return true;
            } else if (p.hasPermission("deadpvp_core.command.heal.other")) {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target != null && !target.isOnline()){
                    target.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    target.sendMessage("§aVous venez d'être heal par "+target.getName()+"!");
                    p.sendMessage("§aVous venez de heal "+target.getName()+"!");
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
