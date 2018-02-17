import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

/**
 * Class Owner: Lachlan
 * 
 * This class is a frame to display the menus and game windows. Includes a GUI
 * to display game to users.
 *
 */
public class GameWindow {

    private JFrame frame;
    private JTextField nameField;
    private JTextField rowField;
    private JTextField colField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameWindow window = new GameWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GameWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 522, 557);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 13, 236, 369);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblPlayerName = new JLabel("Player Name:");
        lblPlayerName.setBounds(12, 13, 89, 16);
        panel.add(lblPlayerName);

        nameField = new JTextField();
        nameField.setBounds(12, 39, 116, 22);
        panel.add(nameField);
        nameField.setColumns(10);

        JLabel lblDifficulty = new JLabel("Difficulty: ");
        lblDifficulty.setBounds(12, 74, 74, 16);
        panel.add(lblDifficulty);

        JComboBox difficultyBox = new JComboBox();
        difficultyBox.setBounds(12, 103, 116, 22);
        panel.add(difficultyBox);

        JLabel lblMapSize = new JLabel("Map Size: ");
        lblMapSize.setBounds(12, 138, 89, 16);
        panel.add(lblMapSize);

        JComboBox mapSizeBox = new JComboBox();
        mapSizeBox.setBounds(12, 167, 116, 22);
        panel.add(mapSizeBox);

        rowField = new JTextField();
        rowField.setText("Rows");
        rowField.setBounds(12, 334, 56, 22);
        panel.add(rowField);
        rowField.setColumns(10);

        colField = new JTextField();
        colField.setText("Col");
        colField.setBounds(80, 334, 56, 22);
        panel.add(colField);
        colField.setColumns(10);

        JLabel lblCustomMapSize = new JLabel("Custom Map Size: ");
        lblCustomMapSize.setBounds(12, 305, 116, 16);
        panel.add(lblCustomMapSize);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(260, 13, 232, 369);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JTextPane highScorePane = new JTextPane();
        highScorePane.setBounds(12, 37, 208, 319);
        panel_1.add(highScorePane);

        JLabel lblHighScores = new JLabel("High Scores: ");
        lblHighScores.setBounds(12, 13, 82, 16);
        panel_1.add(lblHighScores);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(12, 395, 480, 102);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(371, 64, 97, 25);
        panel_2.add(btnStart);

        JButton btnExit = new JButton("Exit ");
        btnExit.setBounds(262, 64, 97, 25);
        panel_2.add(btnExit);

        JButton btnTutorial = new JButton("Tutorial");
        btnTutorial.setBounds(12, 64, 97, 25);
        panel_2.add(btnTutorial);
    }
}
