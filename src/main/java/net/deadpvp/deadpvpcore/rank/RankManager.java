package net.deadpvp.deadpvpcore.rank;

import net.deadpvp.deadpvpcore.sql.SQLManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RankManager {

    private final SQLManager sqlManager;
    private final ArrayList<Rank> ranks;
    private String rankFormat;
    private String chatFormat;

    public RankManager(SQLManager sqlManager){
        this.ranks =  new ArrayList<>();
        this.sqlManager = sqlManager;

        this.updateData();
    }

    public void updateData() {
        this.ranks.clear();
        try {
            Connection connection = sqlManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT permission,longName,color,prefix,power FROM prefix ORDER BY power ASC");

            while (resultSet.next()){
                String permission = resultSet.getString("permission");
                String color = resultSet.getString("color");
                String prefix = resultSet.getString("prefix");
                int power = resultSet.getInt("power");
                String longName = resultSet.getString("longName");

                this.ranks.add(new Rank(this,power,permission,prefix,longName,color, permission.equals("deadpvp.rank.player")));
            }

            resultSet = statement.executeQuery("SELECT value FROM settings WHERE id = 'rank.format'");
            resultSet.next();

            this.rankFormat = resultSet.getString("value");

            resultSet = statement.executeQuery("SELECT value FROM settings WHERE id ='chat.format'");
            resultSet.next();
            this.chatFormat = resultSet.getString("value");

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public String getRankFormat() {
        return this.rankFormat;
    }

    public String getChatFormat() {
        return chatFormat;
    }
}
