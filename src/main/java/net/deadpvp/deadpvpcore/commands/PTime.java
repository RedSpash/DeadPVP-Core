package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PTime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 1){
                try{
                    int time = Integer.parseInt(strings[0]);
                    p.setPlayerTime(time,false);
                    p.sendMessage("§aVotre temps est désormais à "+time);
                }catch (Exception ignored){
                    if(strings[0].equalsIgnoreCase("off")){
                        p.resetPlayerTime();
                        p.sendMessage("§aVotre temps vient d'être réinitialisé!");
                    }else{
                        p.sendMessage("§cLe temps doit être un nombre ou off pour être désactiver");
                    }
                }
                return true;
            } else if (strings.length == 2) {
                if(strings[1].equalsIgnoreCase("off")){
                    p.resetPlayerTime();
                    p.sendMessage("§aVotre temps vient d'être réinitialisé!");
                } else if (p.hasPermission("deadpvp_core.command.ptime.other")) {
                    Player target = Bukkit.getPlayer(strings[0]);
                    if(target != null && !target.isOnline()){
                        try{
                            int time = Integer.parseInt(strings[1]);
                            target.setPlayerTime(time,false);
                            target.sendMessage("§aVotre temps vient d'être positionné à "+time+" par "+p.getName());
                            p.sendMessage("§aVous venez de positionner le temps de "+target.getName()+" à "+time);
                        }catch (Exception ignored){
                            p.sendMessage("§cLe temps doit être un nombre ou off pour être désactiver");
                        }
                    }else{
                        p.sendMessage("§cLe joueur est introuvable!");
                    }
                }

                return true;
            }else{
                if(p.hasPermission("deadpvp_core.command.getpos.other")){
                    p.sendMessage("§c/ptime <player> <time>");
                }else{
                    p.sendMessage("§c/ptime <time>");
                }
            }
            return false;
        }
        return false;
    }
}
