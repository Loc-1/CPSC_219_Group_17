/**
 * The trick to this will be ensuring a path is always available. The route can never be completely blocked as the
 * player will always need to get through. We could use something like:
 * <p>
 * A 200 * 200 board with 'pipes' (see: https://gamedev.stackexchange.com/questions/103054/how-to-randomly-spawn-obstacles-for-infinite-runner
 * (the first answer). There is always a 50px gap (we could scale this with with difficulty and i think a 1980 * 1060
 * game board would work well) and we just randomize the gap within * a range no larger than a player can fit through (i.e.
 * if the player is 25px, the gap must never shift more than 25px is either direction.). We could even use multiple
 * pipes all following the same rules.
 */
public class Obstacle extends Board {
    private int type;
    private int width;
    private int height;
    private double difficulty;
    private int minGap; // This will set the minimum distance between obstacle objects. So many obstacles may be placed.

    /**
     * This takes a board and adds a new obstacle, I think we should allow obstacles to be added 'off-canvas' so
     * we can generate interesting obstacles
     * @param board The game board in to add an obstacle to.
     */

    public Obstacle(double difficulty, int type) {
        this.difficulty = difficulty;
        this.type = type;
        this.minGap = calculateMinGap();
    }

    public void create(Board board) {
    }

    public int calculateMinGap() {
        this.minGap = (int) this.difficulty * 3; // (int) is used to round to even numbers.
        return this.minGap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getMinGap() {
        return minGap;
    }

    public void setMinGap(int minGap) {
        this.minGap = minGap;
    }
}
