package Model;

import java.util.*;

public class PlayerScore {
    private static List<Integer> rolls;

    public PlayerScore() {
        rolls = new ArrayList<>();
    }

    public List<Frame> frames() {
        List<Frame> frames = new ArrayList<>();
        for (int i = 0; i<rolls.size() && frames.size()<10; i += new Frame(i).isStrike() ? 1 : 2) {
            frames.add(new Frame(i));
        }
        return frames;
    }

    public PlayerScore roll(int pins) {
        rolls.add(pins);
        return this;
    }

    public class Frame {
        private final int offset;

        Frame(int offset) {
            this.offset = offset;
        }

        public Integer score() { return isComplete() ? sumOfRolls() : 0; }

        private int sumOfRolls() { return roll(0) + roll(1) + (isStrike() || isSpare() ? roll(2) : 0); }

        private boolean isComplete() {
            return rollsDone() >= rollsToComplete();
        }

        private int rollsDone() {
            return rolls.size() - offset;
        }

        private int rollsToComplete() {
            return isStrike() || isSpare() ? 3 : 2;
        }

        private boolean isSpare() {
            return roll(0) + roll(1) == 10;
        }

        boolean isStrike() {
            return roll(0) == 10;
        }

        private Integer roll(int i) {
            return isOutOfBounds(i) ? rolls.get(i + offset) : 0;
        }

        private boolean isOutOfBounds(int i) {
            return i < rolls.size() - offset;
        }
    }
}