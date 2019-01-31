package Model;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {
    private int lane;
    private String teamName;
    private String datetime;
    private List<Player> players;

    public int getLane(){return lane;}
    public void setLane(int lane) { this.lane = lane; }

    public String getTeamName(){return teamName;}
    public void setTeamName(String teamName){this.teamName = teamName;}

    public String getDatetime(){return datetime;}
    public void setDatetime(String datetime){this.datetime = datetime;}

    public List<Player> getPlayers(){return players;}
    public void setPlayers(List<Player> players){
        this.players = players;
    }

    public boolean equals(Game game) {
        int count = 0;
        for (Player player : game.getPlayers()) {
            if (!player.getName().equals(players.get(count).getName())) return false;
            count++;
        }
        return game.getLane() == lane && game.getTeamName().equals(teamName) && game.getDatetime().equals(datetime);
    }
}