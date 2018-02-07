public class RunGame {
    
    public static void main(String[] args) {
        System.out.println("Please enter your name.");
        String userHandle = "";
        Player p1 = new Player(10, 3, 1, 001,userHandle);
        Board gameBoard = new Board(5, 10, 1, p1);

        boolean gameRunning = true;
        while (gameRunning == true) {
            gameBoard.printBoard();
            
        }
        
        
    }
    
}
