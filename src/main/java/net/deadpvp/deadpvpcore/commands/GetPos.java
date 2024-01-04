package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPos implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 0){
                p.sendMessage("§aVous êtes en "+this.getPosition(p));
                return true;
            } else if (p.hasPermission("deadpvp_core.command.getpos.other")) {
                Player target = Bukkit.getPlayer(strings[0]);
                if(target != null && !target.isOnline()){
                    p.sendMessage("§a"+target.getName()+" est en "+this.getPosition(target));
                }else{
                    p.sendMessage("§cLe joueur est introuvable!");
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private String getPosition(Player p) {
        Location location = p.getLocation();
        return location.getBlock()+" "+location.getBlockY()+" "+location.getBlockZ()+" dans le monde "+location.getWorld().getName();
    }
}
