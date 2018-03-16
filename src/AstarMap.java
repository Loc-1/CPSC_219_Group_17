import java.util.*;

/**
 * Class Owner: Ashton
 * <p>
 * Class is a pathfinding class to ensure there is a traversable path for the obstacleMap blocked nodes array
 * passed
 */




public class AstarMap {

    public static final int DiaCost = 14;
    public static final int SideCost = 10;

    static class Cell{
        int HCost = 0;
        int finalCost = 0;
        int i, j;
        Cell parent;

        Cell(int i, int j){
            this.i = i;
            this.j = j;
        }
    }

    static Cell [][] grid = new Cell[5][5];

    static PriorityQueue<Cell> open;

    static boolean closed[][];

    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j) { grid[i][j] = null; }

    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }


    static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j]){ return; }
        int tFinalCost = t.HCost + cost;

        boolean inOpen = open.contains(t);
        if(!inOpen || tFinalCost < t.finalCost){
            t.finalCost = tFinalCost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }


    public static void AStarMap(){

        open.add(grid[startI][startJ]);

        Cell current;

        while(true){
            current = open.poll();
            if(current == null)break;
            closed[current.i][current.j] = true;

            if(current.equals(grid[endI][endJ])){
                return;
            }

            Cell t;

            if(current.i - 1 >= 0){
                t = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + SideCost);
            }


            if(current.j - 1 >= 0){
                t = grid[current.i][current.j - 1];
                checkAndUpdateCost(current, t, current.finalCost + SideCost);
            }

            if(current.j + 1 < grid[0].length){
                t = grid[current.i][current.j + 1];
                checkAndUpdateCost(current, t, current.finalCost + SideCost);
            }


            if(current.i + 1 < grid.length){
                t = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + SideCost);
            }
        }

    }



    public static int test( int x, int y, int si, int sj, int ei, int ej, int[][] blocked){


        grid = new Cell[x][y];
        closed = new boolean[x][y];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.finalCost < c2.finalCost?-1:
                    c1.finalCost > c2.finalCost?1:0;
        });


        setStartCell(si, sj);
        setEndCell(ei, ej);

        for(int i = 0; i < x; ++i){
            for(int j = 0; j < y; ++j){
                grid[i][j] = new Cell(i,j);
                grid[i][j].HCost = Math.abs(i - endI)+Math.abs(j - endJ);
            }
        }

        grid[si][sj].finalCost = 0;

        for(int i = 0; i < blocked.length; ++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }


        AStarMap();

        if(closed[endI][endJ]){
            Cell current = grid[endI][endJ];
            return current.j;
        }
        else return -1;
    }


}
