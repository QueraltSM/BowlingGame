import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Player_ {
    private Player player;

    @Before
    public void setUp() {
        player = new Player();
        player.setName("Daniel");
    }

    @Test
    public void given_no_rolls_match_should_be_empty(){
        player = mock(Player.class);
        when(player.getMatch()).thenReturn("");
    }

    @Test
    public void given_no_rolls_total_score_should_be_0(){
        player = mock(Player.class);
        when(player.getTotalScore()).thenReturn(0);
    }

    @Test
    public void given_pins_3_score_should_be_0(){
        player = mock(Player.class);
        player.roll(3);
        when(player.getTotalScore()).thenReturn(0);
    }

    @Test
    public void given_pins_3_and_4_match_should_be_them_with_dash(){
        player = mock(Player.class);
        player.roll(3);
        player.roll(4);
        when(player.getMatch()).thenReturn("3-4-");
    }

    @Test
    public void given_no_rolls_display_should_be_empty(){
        assert(player.getDisplay().get(0).equals(""));
    }

    @Test
    public void given_pins_5_2_display_should_be_them_with_vertical_bar(){
        player.roll(5);
        player.roll(2);
        assert(player.getDisplay().get(0).equals("5 | 2"));
    }

    @Test
    public void given_pins_10_display_should_be_X(){
        player.roll(10);
        assert(player.getDisplay().get(0).equals("X"));
    }

    @Test
    public void given_pins_5_and_5_display_should_be_5_and_slash_bar(){
        player.roll(5);
        player.roll(5);
        assert(player.getDisplay().get(0).equals("5 | /"));
    }

    @Test
    public void given_pins_3_and_4_total_score_should_be_7(){
        player = mock(Player.class);
        player.roll(3);
        player.roll(4);
        when(player.getTotalScore()).thenReturn(7);
    }

    @Test
    public void given_pins_5_and_1_and_2_total_score_should_be_6(){
        player = mock(Player.class);
        player.roll(5);
        player.roll(1);
        player.roll(2);
        when(player.getTotalScore()).thenReturn(6);
    }
}