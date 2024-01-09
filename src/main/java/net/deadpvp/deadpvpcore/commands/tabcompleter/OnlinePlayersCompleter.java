package net.deadpvp.deadpvpcore.commands.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class OnlinePlayersCompleter extends TabComplete{
    public OnlinePlayersCompleter(String permission) {
        super(permission);
    }

    @Override
    public List<String> getElements() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
}
