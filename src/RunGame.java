public class RunGame {

    public static void main(String[] args) {
        System.out.println("Please enter your name.");
        String userHandle = UserInput.getUserHandle();
        int xLoc = 9;
        int yLoc = 5;
        Player p1 = new Player(xLoc, yLoc, 1, 001, userHandle);
        Board gameBoard = new Board(10, 10, 1, p1);

        String userPrompt = "";
        while (p1.getAlive()) {
            gameBoard.printBoard();
            System.out.println("Move Up, Down, Left or Right.");
            userPrompt = UserInput.getUserInput();

            if (userPrompt.equals("up") && gameBoard.isValidMove(xLoc, yLoc + 1)) {
                p1.moveUp();
                gameBoard.refresh();
            }
            if (userPrompt.equals("down") && gameBoard.isValidMove(xLoc, yLoc - 1)) {
                p1.moveDown();
                gameBoard.refresh();
            }
            if (userPrompt.equals("left") && gameBoard.isValidMove(xLoc - 1, yLoc)) {
                p1.moveLeft();
                gameBoard.refresh();
            }
            if (userPrompt.equals("right") && gameBoard.isValidMove(xLoc + 1, yLoc)) {
                p1.moveRight();
                gameBoard.refresh();
            }

        }

        System.out.println("Sorry " + p1.getUserHandle() + " you died. Please try again.");

    }

}
