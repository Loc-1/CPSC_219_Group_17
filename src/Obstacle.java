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
public class Obstacle {
    private int type;
    private int width;
    private int height;
    private int min_gap;

    public int[][] newRow() {
        return null;
    }

    public Boolean pathExists() {
        return null;
    }

    public int calculatePathSpacing(int difficulty) {
        return 0;
    }
}
