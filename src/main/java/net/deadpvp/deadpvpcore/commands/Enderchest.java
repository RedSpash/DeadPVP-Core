package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class Enderchest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 0){
                if(p.hasPermission("deadpvp_core.command.enderchest.my")){
                    p.sendMessage("§aOuverture de votre enderchest!");
                    InventoryView inventoryView = p.openInventory(p.getEnderChest());
                    inventoryView.setTitle("§5Votre EnderChest");
                }else{
                    return false;
                }
            }else{
                if(p.hasPermission("deadpvp_core.command.enderchest.other")){
                    Player target = Bukkit.getPlayer(strings[0]);
                    if(target != null){
                        p.sendMessage("§aOuverture de l'enderchest de "+target.getName()+"!");
                        InventoryView inventoryView = p.openInventory(target.getEnderChest());
                        inventoryView.setTitle("§5EnderChest de "+target.getName());
                    }
                }else{
                    return false;
                }
            }
            p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN,1,1);
            return true;
        }
        return false;
    }
}
