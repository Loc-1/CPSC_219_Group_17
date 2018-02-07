public class RunGame {

    public static void main(String[] args) {
        System.out.println("Please enter your name.");
        String userHandle = UserInput.getUserHandle();
        int xLoc = 9;
        int yLoc = 5;
        Player p1 = new Player(xLoc, yLoc, 1, 001, userHandle);
        Board gameBoard = new Board(10, 10, 1, p1);

        String userPromt = "";
        boolean gameRunning = true;
        while (gameRunning == true) {
            gameBoard.printBoard();
            System.out.println("Move Up, Down, Left or Right.");
            userPromt = UserInput.getUserInput();

            if (userPromt.equals("up") && gameBoard.isValidMove(xLoc, yLoc + 1)) {
                p1.moveUp();
            }
            if (userPromt.equals("down") && gameBoard.isValidMove(xLoc, yLoc - 1)) {
                p1.moveDown();
            }
            if (userPromt.equals("left") && gameBoard.isValidMove(xLoc - 1, yLoc)) {
                p1.moveLeft();
            }
            if (userPromt.equals("right") && gameBoard.isValidMove(xLoc + 1, yLoc)) {
                p1.moveRight();
            }

        }

    }

}
