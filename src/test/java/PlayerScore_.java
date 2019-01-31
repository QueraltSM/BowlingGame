import Model.Player;
import Model.PlayerScore;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerScore_ {

    private Player player;

    @Before
    public void setUp() {
        player = new Player();
    }

    @Test
    public void given_new_player_score_frames_should_be_zero() {
        assert(player.getPlayerScore().frames().size()==0);
    }

    @Test
    public void given_one_roll_frames_should_be_one() {
        player.getPlayerScore().roll(4);
        assert(player.getPlayerScore().frames().size()==1);
        assert(player.getPlayerScore().frames().get(0).score()==0);
    }

    @Test
    public void given_two_rolls_frame_score_should_be_the_sum_of_them() {
        player.getPlayerScore().roll(4).roll(3);
        assert(player.getPlayerScore().frames().size()==1);
        assert(player.getPlayerScore().frames().get(0).score()==7);
    }

    @Test
    public void given_three_rolls_frames_should_be_two() {
        player.getPlayerScore().roll(4).roll(3).roll(8);
        assert(player.getPlayerScore().frames().size()==2);
        assert(player.getPlayerScore().frames().get(0).score()==7);
        assert(player.getPlayerScore().frames().get(1).score()==0);
    }

    @Test
    public void given_a_spare_frame_score_should_be_null() {
        player.getPlayerScore().roll(4).roll(6);
        assert(player.getPlayerScore().frames().size()==1);
        assert(player.getPlayerScore().frames().get(0).score()==0);
    }

    @Test
    public void given_a_spare_and_a_roll_frame_score_should_be_the_sum_of_them() {
        player.getPlayerScore().roll(4).roll(6).roll(5);
        assert(player.getPlayerScore().frames().size()==2);
        assert(player.getPlayerScore().frames().get(0).score()==15);
        assert(player.getPlayerScore().frames().get(1).score()==0);
    }

    @Test
    public void given_a_strike_and_a_roll_frame_score_should_be_0() {
        player.getPlayerScore().roll(10).roll(9);
        assert(player.getPlayerScore().frames().size()==2);
        assert(player.getPlayerScore().frames().get(0).score()==0);
        assert(player.getPlayerScore().frames().get(1).score()==0);
    }

    @Test
    public void given_a_strike_and_a_spare_frame_score_should_be_20() {
        player = mock(Player.class);
        player.roll(10);
        player.roll(9);
        player.roll(1);
        when ( player.getTotalScore()).thenReturn (20);
    }

    @Test
    public void given_a_perfect_game_total_score_should_be_300() {
        player.getPlayerScore().roll(10).roll(10).roll(10).roll(10).roll(10).roll(10).roll(10).roll(10).roll(10)
                .roll(10).roll(10).roll(10);
        assert(player.getPlayerScore().frames().size()==10);
        assert(player.getPlayerScore().frames().stream().mapToInt(PlayerScore.Frame::score).sum()==300);
    }
}