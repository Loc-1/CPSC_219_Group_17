/**
 * Class Owner: Lachlan / Josh 
 * <p>
 * The RunGame class creates and runs the game
 */
public class RunGame {

    public static void main(String[] args) {
        System.out.println("Please enter your name.");
        String userHandle = UserInput.getUserHandle();
        int xLoc = 23;
        int yLoc = 32 / 2;
        Player p1 = new Player(xLoc, yLoc, 1, 001, userHandle);
        Board gameBoard = new Board(24, 32, 1, p1);

        String userPrompt = "";
        while (p1.isAlive()) {
            gameBoard.printBoard();
            System.out.println("Move Up, Down, Left or Right.");
            userPrompt = UserInput.getUserInput();

            if (userPrompt.equals("up")) {
                p1.moveUp();
                gameBoard.refresh();
            }
            if (userPrompt.equals("down")) {
                p1.moveDown();
                gameBoard.refresh();
            }
            if (userPrompt.equals("left")) {
                p1.moveLeft();
                gameBoard.refresh();
            }
            if (userPrompt.equals("right")) {
                p1.moveRight();
                gameBoard.refresh();
            }

        }

        System.out.println("Sorry " + p1.getUserHandle() + ", you died. Please try again.");

    }

}
