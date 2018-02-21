/**
 * Class Owner: Lachlan / Josh 
 * <p>
 * The RunGame class creates and runs the game
 */

import pathfinding.*;

public class RunGame {

    public static void main(String[] args) {
        System.out.println("Please enter your name.");
        String userHandle = UserInput.getUserHandle();
    	
        int xLoc = 22;
        int yLoc = 32 / 2;
        Player p1 = new Player(xLoc, yLoc, 1, 001, userHandle);
        Board gameBoard = new Board(24, 32, 1, p1);
        
        // Remake the board if it is impossible
        while (!gameBoard.isTraversable()){
        	gameBoard.printBoard();
        	System.out.println("The board is not traversable, regenerating.");
        	gameBoard = new Board(24,32,1,p1);
        	
        }
        
        
        String userPrompt = "";
        while (p1.isAlive()) {
            gameBoard.printBoard();         
            System.out.println("Move Up, Down, Left or Right.");
            userPrompt = UserInput.getUserInput();

            if (userPrompt.equals("up")) {
            	p1.moveUp();
            	if (!gameBoard.isValidMove(p1.getxLocation(), p1.getyLocation())) {
                    p1.moveDown();
            	}
            	gameBoard.refresh();
            }
            if (userPrompt.equals("down")) {
                p1.moveDown();
            	if (!gameBoard.isValidMove(p1.getxLocation(), p1.getyLocation())) {
                    p1.moveUp();
            	}
                gameBoard.refresh();
            }
            if (userPrompt.equals("left")) {
                p1.moveLeft();
            	if (!gameBoard.isValidMove(p1.getxLocation(), p1.getyLocation())) {
                    p1.moveRight();
            	}
                gameBoard.refresh();
            }
            if (userPrompt.equals("right")) {
                p1.moveRight();
            	if (!gameBoard.isValidMove(p1.getxLocation(), p1.getyLocation())) {
                    p1.moveLeft();
            	}
                gameBoard.refresh();
            }

        }

        System.out.println("Sorry " + p1.getUserHandle() + ", you died. Please try again.");

    }

}
