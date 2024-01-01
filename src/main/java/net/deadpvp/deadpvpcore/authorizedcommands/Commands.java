package net.deadpvp.deadpvpcore.authorizedcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    private final AuthorizedCommandsConfig authorizedCommandsConfig;

    public Commands(AuthorizedCommandsConfig authorizedCommandsConfig) {
        this.authorizedCommandsConfig = authorizedCommandsConfig;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("deadpvp.staff.developer")){
            if(strings.length == 0) {
                commandSender.sendMessage("§c/commands <add|remove|list>");
                return true;
            }
            switch (strings[0].toLowerCase()){
                case "list"->{
                    StringBuilder message = new StringBuilder("§aListe des commandes activées:\n");
                    for(String enableCommand : this.authorizedCommandsConfig.getEnableCommands()){
                        message.append(enableCommand).append(",");
                    }
                    commandSender.sendMessage(message.substring(0,message.length()-1));
                    return true;
                }
                case "add"->{
                    if(strings.length == 1){
                        commandSender.sendMessage("§c/command add <commande>");
                        return true;
                    }

                    StringBuilder commande = new StringBuilder();
                    for(int i = 1; i < strings.length; i++){
                        commande.append(strings[i]);
                        if(i != strings.length-1){
                            commande.append(" ");
                        }
                    }
                    if(this.authorizedCommandsConfig.contaisCommand(commande.toString())){
                        commandSender.sendMessage("§cLa commande est déjà activée!");
                    }else{
                        this.authorizedCommandsConfig.addCommand(commande.toString());
                        commandSender.sendMessage("§aLa commande est désormais activée!");
                    }
                }
                case "remove"->{
                    if(strings.length == 1){
                        commandSender.sendMessage("§c/command remove <commande>");
                        return true;
                    }

                    StringBuilder commande = new StringBuilder();
                    for(int i = 1; i < strings.length; i++){
                        commande.append(strings[i]).append(" ");
                    }

                    String commandString = commande.substring(0,commande.length()-1).toLowerCase();

                    if(!this.authorizedCommandsConfig.contaisCommand(commandString)){
                        commandSender.sendMessage("§cLa commande n'est pas activée!");
                    }else{
                        this.authorizedCommandsConfig.removeCommand(commandString);
                        commandSender.sendMessage("§aLa commande est désormais désactivée!");
                    }
                }
            }
        }
        return false;
    }
}
