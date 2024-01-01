package net.deadpvp.deadpvpcore.listener;

import net.deadpvp.deadpvpcore.authorizedcommands.AuthorizedCommandsConfig;
import net.deadpvp.deadpvpcore.sql.SQLManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandProcessEvent implements Listener {



    private final SQLManager sqlManager;
    private final AuthorizedCommandsConfig authorizedCommandsConfig;
    private String defaultMessageError;

    public CommandProcessEvent(SQLManager sqlManager, AuthorizedCommandsConfig authorizedCommandsConfig) {
        this.sqlManager = sqlManager;
        this.authorizedCommandsConfig = authorizedCommandsConfig;
        try {
            this.setDefaultMessageError();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDefaultMessageError() throws SQLException {
        PreparedStatement statement = this.sqlManager.getConnection().prepareStatement("SELECT value FROM settings WHERE id = 'server.command.unknown'");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        this.defaultMessageError = resultSet.getString("value");
        statement.close();
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

        boolean authorized = false;
        for(String command : this.authorizedCommandsConfig.getEnableCommands()){
            if(message.startsWith(command.toLowerCase())){
                authorized = true;
                break;
            }
        }
        if(!authorized){
            e.getPlayer().sendMessage(this.defaultMessageError);
            e.setCancelled(true);
        }
    }
}
