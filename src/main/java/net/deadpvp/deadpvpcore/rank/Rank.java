package net.deadpvp.deadpvpcore.rank;

import org.bukkit.entity.Player;

public class Rank {

    private final int power;
    private final String permission;
    private final String prefix;
    private final String longName;
    private final String color;
    private final RankManager rankManager;
    private final boolean defaultRank;

    public Rank(RankManager rankManager, int power, String permission, String prefix, String longName, String color, boolean defaultRank) {
        this.rankManager = rankManager;
        this.power = power;
        this.permission = permission;
        this.longName = longName;
        this.prefix = prefix;
        this.color = color;
        this.defaultRank = defaultRank;
    }

    public int getPower() {
        return power;
    }

    public String getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPermission() {
        return permission;
    }

    public String getLongName() {
        return longName;
    }

    public boolean hasPermission(Player p) {
        return p.hasPermission(this.permission);
    }

    public String rankToFormat(){
        if(this.prefix.isEmpty()){
            return this.color;
        }
        return rankManager.getRankFormat()
                .replace("{rankcolor}",this.color)
                .replace("{rank}",this.prefix)
                .replace("{longname}",this.longName);
    }

    public boolean isDefaultRank() {
        return defaultRank;
    }
}
