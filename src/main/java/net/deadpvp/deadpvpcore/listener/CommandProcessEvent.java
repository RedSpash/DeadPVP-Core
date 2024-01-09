package net.deadpvp.deadpvpcore.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class CommandProcessEvent implements Listener {

    private final String defaultMessageError;
    private final List<String> lockedCommands = Arrays.asList("?","help","op","list","me","msg","seed","teammsg","tm","w","tell","pl","plugins");

    public CommandProcessEvent() {
        this.defaultMessageError = Bukkit.spigot().getConfig().getString("messages.unknown-command");
    }

    @EventHandler
    public void processCommand(PlayerCommandPreprocessEvent e){
        String message = e.getMessage().replaceFirst("/","").toLowerCase();

        if(message.length() > 1){
            int index = 0;
            while(index < message.length() && message.charAt(index) != ' '){
                if(message.charAt(index) == ':'){
                    if(index+1 < message.length()){
                        message = message.substring(index+1);
                    }
                    break;
                }
                index = index + 1;
            }
        }

        for(String command : this.lockedCommands){
            if((message+" ").startsWith(command.toLowerCase()+" ")){
                e.getPlayer().sendMessage(this.defaultMessageError);
                e.setCancelled(true);
                return;
            }
        }
    }
}
