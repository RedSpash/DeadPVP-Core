package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class PWeather implements CommandExecutor {

    public static final String OTHER_PERMISSION = "deadpvp_core.command.ptime.other";
    private final HashMap<String, WeatherType> values = new HashMap<>();

    public PWeather(){
        this.values.put("pluie",WeatherType.DOWNFALL);
        this.values.put("ensoleillé", WeatherType.CLEAR);
        this.values.put("reset", null);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length > 0 ){
                Player target = p;
                if(strings.length == 2 && p.hasPermission("deadpvp_core.command.pweather.other")) {
                    target = Bukkit.getPlayer(strings[1]);
                    if(target == null || !target.isOnline()) {
                        p.sendMessage("§cErreur: Joueur introuvable!");
                        return true;
                    }
                }
                String value = strings[0].toLowerCase();
                if(this.values.containsKey(value)){
                    WeatherType weatherType = this.values.get(value);
                    if(weatherType == null){
                        target.resetPlayerWeather();
                        if(!p.getUniqueId().equals(target.getUniqueId())){
                            p.sendMessage("§aLa météo de "+target.getName()+" est désormais synchronisée avec le serveur!");
                        }
                        target.sendMessage("§aVotre météo est désormais synchronisée avec le serveur!");
                    }else{
                        target.setPlayerWeather(weatherType);
                        if(!p.getUniqueId().equals(target.getUniqueId())){
                            p.sendMessage("§aLa météo de "+target.getName()+" est désormais "+value+"!");
                        }
                        target.sendMessage("§aVotre météo est désormais "+value+"!");
                    }
                }else{
                    p.sendMessage("§cLa météo doit être "+String.join(" ou ", this.values.keySet())+" !");
                    return false;
                }
            }else{
                if(p.hasPermission(OTHER_PERMISSION)){
                    p.sendMessage("§c/pweather <meteo> <player>");
                }else{
                    p.sendMessage("§c/pweather <meteo>");
                }
            }
            return true;
        }
        return false;
    }
}
