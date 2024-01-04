package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class Feed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 0){
                p.setFoodLevel(20);
                p.sendMessage("§aVous venez d'être rassasié!");
                return true;
            } else if (p.hasPermission("deadpvp_core.command.feed.other")) {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target != null && !target.isOnline()){
                    target.setFoodLevel(20);
                    target.sendMessage("§aVous venez d'être rassasié par "+target.getName()+"!");
                    p.sendMessage("§aVous venez de rassasier "+target.getName()+"!");
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
