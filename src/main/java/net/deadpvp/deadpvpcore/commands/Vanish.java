package net.deadpvp.deadpvpcore.commands;

import net.deadpvp.deadpvpcore.vanish.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    private final VanishManager vanishManager;

    public Vanish(VanishManager vanishManager) {
        this.vanishManager = vanishManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(this.vanishManager.isVanish(p.getUniqueId())){
                this.vanishManager.unVanish(p);
                p.sendMessage("§aVous n'êtes plus en vanish!");
            }else {
                this.vanishManager.vanish(p);
                p.sendMessage("§aVous êtes désormais en vanish!");
            }
            return true;
        }
        return false;
    }
}
