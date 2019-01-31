package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String name;
    private PlayerScore playerScore;
    private String match;

    public Player(){
        playerScore = new PlayerScore();
        match = "";
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public PlayerScore getPlayerScore(){
        return playerScore;
    }
    public void setPlayerScore(PlayerScore playerScore){
        this.playerScore = playerScore;
    }

    public List<String> getDisplay(){
        List<String> data = new ArrayList<>();
        String[] split = getMatch().split("-");
        int i = 0;
        if (!split[i].equals("")) while (i < split.length) if (i + 1 >= split.length && !split[i].equals("10")) {
            data.add(split[i] + " | ");
            break;
        } else if (split[i].equals("10")) {
            data.add("X");
            i++;
        } else if (Integer.parseInt(split[i]) + Integer.parseInt(split[i + 1]) == 10) {
            data.add(split[i] + " | /");
            i += 2;
        } else {
            data.add(split[i] + " | " + split[i + 1]);
            i += 2;
        }
        int j = data.size();
        while (j<=9) {
            data.add("");
            j++;
        }
        return data;
    }

    public String getMatch() {
        return match;
    }
    public void setMatch(String match) {
        this.match = match;
    }

    public int getTotalScore() {
        PlayerScore playerScore = new PlayerScore();
        getDisplay().stream().filter(s -> !s.equals("")).forEach(s -> {
            if (s.equals("X")) playerScore.roll(10);
            else {
                String num1 = s.split(" \\| ")[0];
                playerScore.roll(Integer.parseInt(num1));
                if (s.split("\\| ").length == 2) {
                    String num2 = s.split(" \\| ")[1];
                    if (num2.equals("/")) num2 = 10 - Integer.parseInt(num1) + "";
                    playerScore.roll(Integer.parseInt(num2));
                }
            }
        });
        saveDataLogfile(new StringBuilder("Score of " + getName() + " = " + playerScore.frames()
                .stream().mapToInt(PlayerScore.Frame::score).sum()));
        return playerScore.frames().stream().mapToInt(PlayerScore.Frame::score).sum();
    }

    public void roll(int pins){
        playerScore.roll(pins);
        setMatch(getMatch() + pins + "-");
        saveDataLogfile(new StringBuilder(getName() + " has thrown " + pins + " pins"));
    }

    private void saveDataLogfile(StringBuilder message){
        try(FileWriter fw = new FileWriter("src\\main\\java\\Data\\Log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
            out.println("- - - - - - - - - - - - - - - - - - - -");
        } catch (IOException ignored) {}
    }
}