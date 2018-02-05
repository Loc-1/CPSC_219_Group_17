import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * The UserInput class gets the users input and returns edited values so those
 * commands can be issued.
 */

public class UserInput {

    /**
     * This method will get the users input and return "up","down","left","right" or
     * "invaild"
     */
    public String getUserInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        input = input.toLowerCase();

        if (input.equals("up") || input.equals("down") || input.equals("left") || input.equals("right")) {
            return input;
        } else {
            System.out.println("Invaild value");
            return "invaild";
        }
    }

    /**
     * :TODO: Method to be implemeted after text based demo
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT) {

        }
        if (key == KeyEvent.VK_LEFT) {

        }
        if (key == KeyEvent.VK_DOWN) {

        }
        if (key == KeyEvent.VK_UP) {

        }
    }

}
