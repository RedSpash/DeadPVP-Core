package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PTime implements CommandExecutor, TabCompleter {

    public static final String OTHER_PERMISSION = "deadpvp_core.command.ptime.other";
    private final HashMap<String,Integer> values = new HashMap<>();

    public PTime(){
        this.values.put("jour",0);
        this.values.put("midi",6000);
        this.values.put("crépuscule",11700);
        this.values.put("minuit",18000);
        this.values.put("reset",-1);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length > 0 ){
                Player target = p;
                if(strings.length == 2 && p.hasPermission(OTHER_PERMISSION)) {
                    target = Bukkit.getPlayer(strings[1]);
                    if(target == null || !target.isOnline()) {
                        p.sendMessage("§cErreur: Joueur introuvable!");
                        return true;
                    }
                }

                Integer value = getTimeValue(strings[0].toLowerCase());
                if(value == null){
                    p.sendMessage("§cLe temps doit être une valeur entière ou: "+
                            String.join(" ou ", this.values.keySet())
                    );
                    return true;
                } else if (value == -1) {
                    target.resetPlayerTime();
                    target.sendMessage("§aLe temps de votre jeu vient d'être réinitialisé!");
                    if(!p.getUniqueId().equals(target.getUniqueId())){
                        p.sendMessage("§aLe temps de "+target.getName()+" vient d'être réinitialisé!");
                    }
                    return true;
                }

                target.setPlayerTime(value,false);
                target.sendMessage("§aLe temps de votre jeu vient d'être mis à "+value);
                if(!p.getUniqueId().equals(target.getUniqueId())){
                    p.sendMessage("§aLe temps de "+target.getName()+" vient d'être mis à "+value);
                }
            }else{
                if(p.hasPermission(OTHER_PERMISSION)){
                    p.sendMessage("§c/ptime <time> <player>");
                }else{
                    p.sendMessage("§c/ptime <time>");
                }
            }
            return true;
        }
        return false;
    }

    private Integer getTimeValue(String string) {
        Integer value = this.values.getOrDefault(string,null);

        if(value == null){
            try {
                value = Integer.parseInt(string);
            }catch (Exception ignored) {
                return null;
            }
        }
        return value;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length <= 1){
            List<String> list = new ArrayList<>(this.values.keySet().stream().toList());
            if(strings.length == 1){
                list.removeIf(element -> !element.startsWith(strings[0]));
            } else if (commandSender.hasPermission(OTHER_PERMISSION)) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
            return list;
        }
        return List.of();
    }
}
