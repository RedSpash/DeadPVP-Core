package net.deadpvp.deadpvpcore.commands.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleTabCompleter implements TabCompleter {
    private final List<TabComplete> completion;

    public SimpleTabCompleter(TabComplete... completion){
        this.completion = List.of(completion);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length <= completion.size()){
            TabComplete tabComplete = completion.get(strings.length-1);
            if(tabComplete.hasPermission(commandSender)){
                ArrayList<String> list = new ArrayList<>(tabComplete.getElements());
                list.removeIf(element -> !element.startsWith(strings[strings.length-1]));
                return list;
            }
        }
        return Collections.emptyList();
    }
}
