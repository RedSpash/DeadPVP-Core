package net.deadpvp.deadpvpcore.commands;

import net.deadpvp.deadpvpcore.vanish.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class God implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            p.setInvulnerable(!p.isInvulnerable());
            if(p.isInvulnerable()){
                p.sendMessage("§aVous êtes invulnérable");
            }else{
                p.sendMessage("§cVous n'êtes plus invulnérable");
            }
            return true;
        }
        return false;
    }
}
