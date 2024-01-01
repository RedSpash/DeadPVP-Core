package net.deadpvp.deadpvpcore.authorizedcommands;

import net.deadpvp.deadpvpcore.DeadPVP_Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorizedCommandsConfig {

    private ArrayList<String> enableCommands;
    public final File file;
    public final FileConfiguration configuration;

    public AuthorizedCommandsConfig(DeadPVP_Core deadpvpCore){
        this.enableCommands = new ArrayList<>();
        this.file = new File(deadpvpCore.getDataFolder(),"AuthorizedCommands.yml");
        boolean exist = this.checkIfExist(this.file);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        this.updateEnableCommands();

        if(!exist){
            this.enableCommands.addAll(Arrays.asList("commands","spawn","hub"));
            this.save();
        }
    }

    public void updateEnableCommands() {
        this.enableCommands = new ArrayList<>(this.configuration.getStringList("enablecommands"));
    }

    private boolean checkIfExist(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
                Bukkit.getConsoleSender().sendMessage("§a§lFile "+file.getName()+".yml created !");
                return false;
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("§4§lError: "+file.getName()+".yml");
                e.printStackTrace();
            }
        }
        return true;
    }

    public void save(){
        try {
            this.configuration.set("enablecommands",this.enableCommands);
            this.configuration.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCommand(String command){
        this.enableCommands.add(command);
    }

    public void removeCommand(String command){
        this.enableCommands.remove(command);
    }

    public List<String> getEnableCommands() {
        return (List<String>) this.enableCommands.clone();
    }

    public boolean contaisCommand(String command) {
        return this.enableCommands.contains(command);
    }
}
