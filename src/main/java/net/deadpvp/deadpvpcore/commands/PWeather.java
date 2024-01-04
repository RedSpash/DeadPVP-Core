package net.deadpvp.deadpvpcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PWeather implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length == 1){
                WeatherType type = this.getWeatherType(strings[0]);

                if(type == null){
                    if(strings[0].equalsIgnoreCase("off")){
                        p.resetPlayerWeather();
                        p.sendMessage("§aLa météo vient d'être réinitialisée!");
                        return true;
                    }
                    p.sendMessage("§cLa météo doit être une des valeurs suivantes: "+this.getPossibilities());
                }else{
                    p.setPlayerWeather(type);
                    p.sendMessage("§aVotre météo vient d'être mise à "+type.name());
                }
                return true;
            } else if (strings.length == 2) {
                if (p.hasPermission("deadpvp_core.command.pweather.other")) {
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
                    return true;
                }
                return false;
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

    private String getPossibilities() {
        StringBuilder possibilities = new StringBuilder();
        for(WeatherType weatherType : WeatherType.values()){
            possibilities.append(weatherType.name()).append(", ");
        }
        possibilities.append("off");
        return possibilities.toString();
    }

    private WeatherType getWeatherType(String string) {
        for(WeatherType weatherType : WeatherType.values()){
            if(weatherType.name().equalsIgnoreCase(string)){
                return weatherType;
            }
        }
        return null;
    }
}
