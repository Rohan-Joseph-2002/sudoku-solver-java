package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.SudokuSolverGUI.*;

//Class - SidePanel
public class SidePanel extends JPanel implements ActionListener {
    private static final int SIDE_PANEL_WIDTH = 100;

    private static final Color BUTTON_BG_COLOR = Color.BLACK;
    private static final Color BUTTON_FG_COLOR = Color.WHITE;

    private static final Font BUTTON_FONT = new Font("Sans Serif", Font.BOLD, 30);

    private final SudokuSolverGUI mainFrame;
    private final JPanel sidePanel;

    //EFFECTS: Constructs a side panel which is always displayed to the user
    public SidePanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, PANEL_STARTING_HEIGHT));

        JButton clearGrid = createJButton("Clear", "clear");
        JButton solveSudoku = createJButton("Solve", "solve");
        JButton saveCurrentSession = createJButton("Save", "save");

        add(clearGrid);
        add(solveSudoku);
        add(saveCurrentSession);

        setLayout(new GridLayout(3, 1));
    }

    //MODIFIES: this
    //EFFECTS: Constructs a JButton given its text and action command
    private JButton createJButton(String text, String command) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setActionCommand(command);
        button.setBackground(BUTTON_BG_COLOR);
        button.setForeground(BUTTON_FG_COLOR);
        button.setFont(BUTTON_FONT);
        return button;
    }

    //MODIFIES: this
    //EFFECTS: Processes the action command of buttons in this side panel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "clear":
                mainFrame.clearDisplayGrid();
                break;
            case "solve":
                mainFrame.playPopSound();
                mainFrame.solveSudokuQuestionBoard();
                break;
            case "save":
                mainFrame.saveAnswerBoards();
                break;
        }
    }
}
