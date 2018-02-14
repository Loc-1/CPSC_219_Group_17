import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * Class Owner: Lachlan
 * 
 * The UserInput class gets the users input and returns edited values so those
 * commands can be issued.
 */

public class UserInput {

    /**
     * This method will get the users input and return "up","down","left","right" or
     * "invaild"
     */
    public static String getUserInput() {
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
     * This method gets the users handle from the users input
     */
    public static String getUserHandle() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }

    /**
     * This method gets the constant movement commands from the user ie. arrow keys
     * or wasd
     * 
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT || key == 68) {

        }
        if (key == KeyEvent.VK_LEFT || key == 65) {

        }
        if (key == KeyEvent.VK_DOWN || key == 83) {

        }
        if (key == KeyEvent.VK_UP || key == 87) {

        }
    }

}
