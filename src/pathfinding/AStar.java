package pathfinding;

 

import java.util.*;

/**
 * 
 * Taken from  Taken from http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
 * Algorithm that determines the cost of travel from a starting node to an end node using the A* Algorithm
 * Scores nodes based on a number whether or not the node can be reached. 
 * The higher the score, the harder it is to reach the node. A score of 0 means the node is impossible to reach.
 */
public class AStar {
    public static final int DIAGONAL_COST = 1400; // Changed Diagonal cost to 1400 from 14 to not consider diagonal moves
    public static final int V_H_COST = 10;
    
    static class Cell{  
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent; 
        
        Cell(int i, int j){
            this.i = i;
            this.j = j; 
        }
        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }
    
    //Blocked cells are just null Cell values in grid
    static Cell [][] grid = new Cell[5][5];
    
    static PriorityQueue<Cell> open;
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
            
    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    public static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }
    
    public static void setEndCell(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
    public static void AStar(){ 
        
        //add the start location to open list.
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }  
            }
        } 
    }
    
    /*
    Params :
    tCase = test case No.
    x, y = Board's dimensions
    si, sj = start location's x and y coordinates
    ei, ej = end location's x and y coordinates
    int[][] blocked = array containing inaccessible cell coordinates
    */
    public static void test(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
           System.out.println("\n\nTest Case #"+tCase);
            //Reset
           grid = new Cell[x][y];
           closed = new boolean[x][y];
           open = new PriorityQueue<>((Object o1, Object o2) -> {
                Cell c1 = (Cell)o1;
                Cell c2 = (Cell)o2;

                return c1.finalCost<c2.finalCost?-1:
                        c1.finalCost>c2.finalCost?1:0;
            });
           //Set start position
           setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part
           
           //Set End Location
           setEndCell(ei, ej); 
           
           for(int i=0;i<x;++i){
              for(int j=0;j<y;++j){
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//                  System.out.print(grid[i][j].heuristicCost+" ");
              }
//              System.out.println();
           }
           grid[si][sj].finalCost = 0;
           
           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
           for(int i=0;i<blocked.length;++i){
               setBlocked(blocked[i][0], blocked[i][1]);
           }
           
           //Display initial map
           System.out.println("Grid: ");
            for(int i=0;i<x;++i){
                for(int j=0;j<y;++j){
                   if(i==si&&j==sj)System.out.print("SO  "); //Source
                   else if(i==ei && j==ej)System.out.print("DE  ");  //Destination
                   else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                   else System.out.print("BL  "); 
                }
                System.out.println();
            } 
            System.out.println();
           
           AStar(); 
           System.out.println("\nScores for cells: ");
           for(int i=0;i<x;++i){
               for(int j=0;j<y;++j){
                   if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                   else System.out.print("BL  ");
               }
               System.out.println();
           }
           System.out.println();
            
           if(closed[endI][endJ]){
               //Trace back the path 
                System.out.println("Path: ");
                Cell current = grid[endI][endJ];
                System.out.print(current);
                while(current.parent!=null){
                    System.out.print(" -> "+current.parent);
                    current = current.parent;
                } 
                System.out.println();
           }else System.out.println("No possible path");
    }
    
    /**
     * Set up the A* Algorithm for all other methods.
     * @param x the width of the Grid
     * @param y the height of the Grid
     * @param si the starting row location
     * @param sj the starting column location
     * @param ei the end row location
     * @param ej the end column location
     * @param blocked Array for coordinates of obstacles
     */
    public static void aStarSetUp(int x, int y, int si, int sj, int ei, int ej, int[][] blocked) {
        
   	//Reset
       grid = new Cell[x][y];
       closed = new boolean[x][y];
       open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;

            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
        });
       
       //Set start position
       setStartCell(si, sj); 
       
       //Set End Location
       setEndCell(ei, ej); 
       
       for(int i=0;i<x;++i){
          for(int j=0;j<y;++j){
              grid[i][j] = new Cell(i, j);
              grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
          }
       }
       
       grid[si][sj].finalCost = 0;
       
       /*
         Set blocked cells. Simply set the cell values to null
         for blocked cells.
       */
       for(int i=0;i<blocked.length;++i){
           setBlocked(blocked[i][0], blocked[i][1]);
       }
       
       AStar();
    }
    
    
    /**
     * @ Author: Lincoln inspired by the test method above
     * @param x the width of the Grid
     * @param y the height of the Grid
     * @param si the starting row location
     * @param sj the starting column location
     * @param ei the end row location
     * @param ej the end column location
     * @param blocked Array for coordinates of obstacles
     * @return The cost of traversing to the end location: 0 Indicates end location cannot be reached.
     */
    public static int aStarCost(int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
    	aStarSetUp(x, y, si, sj, ei, ej, blocked);
        if ( grid[ei][ej] == null) {
        	return 50000; // Returns large value for the cost of travelling to obstacles
        } else {
        	return grid[ei][ej].finalCost;
        }
 }
    
    /**
     * @author Lincoln
     * @param x the width of the Grid
     * @param y the height of the Grid
     * @param si the starting row location
     * @param sj the starting column location
     * @param ei the end row location
     * @param ej the end column location
     * @param blocked Array for coordinates of obstacles
     * @return 2D containing the coordinates of each point on the path of an object
     */
    public static List<int[]> pathfinding(int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
    	List<int[]> path = new ArrayList<int[]>();
    	int pathprogress = 0;
    	
        aStarSetUp(x, y, si, sj, ei, ej, blocked);
        if(closed[endI][endJ]){
             Cell current = grid[endI][endJ];
             while(current.parent!=null){
            	 int[] coords = {current.i, current.j};
            	 path.add(pathprogress, coords);
                 current = current.parent;
             } 
        }else return null;
        
        return path;
 }
    /**
     * @author Lincoln
     * @param x the width of the Grid
     * @param y the height of the Grid
     * @param si the starting row location
     * @param sj the starting column location
     * @param ei the end row location
     * @param ej the end column location
     * @param blocked Array for coordinates of obstacles
     * @param minTravelCost The minimum cost of travel for chosen destination
     * @param maxTravelCost The maximum cost of travel for chosen destination
     * @return int[] containing coordinates of a random choice of destination within travel costs.
     */
    public static int[] chooseDestination(int x, int y, int si, int sj, int ei, int ej, int[][] blocked, int minTravelCost, int maxTravelCost){        
        int numPossibleDestinations = 0;
        List<int[]> destinations = new ArrayList<int[]>();
        
    	aStarSetUp(x, y, si, sj, ei, ej, blocked);
        for(int i = 0; i < x; ++i){
            for(int j = 0; j < y; ++j){
                if((grid[i][j]!=null) && (grid[i][j].finalCost > minTravelCost) && (grid[i][j].finalCost < maxTravelCost)) {
                	int[] coords = {i, j};
                	destinations.add(numPossibleDestinations, coords);
                	
                	numPossibleDestinations += 1;
                }
                }
        }
       
        int[] theDestination = destinations.get((int) (generateRandomDouble() * destinations.size()));
        
        return theDestination;
 }
    
    public static int[][] append(int[][] arrayOne, int[][] arrayTwo) {
    	int[][] summedArray = new int[arrayOne.length + arrayTwo.length][];
    	return summedArray;
    }
    
    /**
     * 
     * @return a random double.
     */
    public static double generateRandomDouble() {
        Random randomNum = new Random();
        return randomNum.nextDouble();
    }
     
    public static void main(String[] args) throws Exception{   
        //test(1, 7, 5, 0, 0, 6, 3, new int[][]{{6,2},{5,2},{4,3},{3,3},{3,4},{4,2}}); 
        test(2, 5, 5, 0, 0, 3, 2, new int[][]{{0,4},{2,2},{3,1},{3,3}});   
        //test(3, 7, 7, 2, 1, 5, 4, new int[][]{{4,1},{4,3},{5,3},{2,3}});
        
        //test(1, 5, 5, 0, 0, 4, 4, new int[][]{{3,4},{3,3},{4,3}});
        

        int[] endpoints = chooseDestination(5,5,0,0,3,2, new int[][]{{0,4},{2,2},{3,1},{3,3}}, 28, 909);
        System.out.println(Arrays.toString(endpoints));
        
        List<int[]> lincolnTest = pathfinding(5,5,0,0,endpoints[0],endpoints[1], new int[][]{{0,4},{2,2},{3,1},{3,3}});
        System.out.println(Arrays.deepToString(lincolnTest.toArray()));

    }
}