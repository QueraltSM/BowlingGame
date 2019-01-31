import Model.Game;
import Model.Player;
import Model.Serialization;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Serialization_ {
    private static Game game;

    @Before
    public void init() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setName("Ana");
        player2.setName("Daniel");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        game = new Game();
        game.setLane(1);
        game.setTeamName("The Bowling Stones");
        game.setDatetime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        game.setPlayers(players);
    }

    @Test
    public void save_a_game_must_create_a_file() throws TransformerException, ParserConfigurationException,
            IOException {
        Serialization.saveGame(game);
        assert(new File("src\\main\\java\\Data\\Display1.xml").isFile());
    }

    @Test
    public void save_a_game_successfully() throws TransformerException, ParserConfigurationException, IOException {
        Serialization.saveGame(game);
        assert(game.equals(Serialization.getDisplay(1)));
    }

    @Test
    public void roll_saved_successfully() throws TransformerException, ParserConfigurationException, IOException {
        Serialization.saveGame(game);
        Serialization.saveRoll(1,"Ana", 5);
        assert(Serialization.getDisplay(1).getPlayers().get(0).getMatch().equals("5-"));
    }

    @Test
    public void check_total_score_after_a_roll_saved() throws TransformerException, ParserConfigurationException,
            IOException {
        Serialization.saveGame(game);
        Serialization.saveRoll(1,"Daniel", 4);
        Serialization.saveRoll(1,"Daniel", 3);
        assert(Serialization.getDisplay(1).getPlayers().get(1).getTotalScore()==7);
    }
}