package gui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;

public class CurrentSessionDisplayPanel extends JPanel implements ActionListener {
    private static final int DISPLAY_PANEL_WIDTH = 550;
    private static final int DISPLAY_PANEL_HEIGHT = PANEL_STARTING_HEIGHT / 2 - 50;

    private static final String FONT_NAME = "Helvetica";

    private static final Color BUTTON_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color BUTTON_FG_COLOR = Color.BLACK;

    private static final Font BUTTON_FONT = new Font(FONT_NAME, Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 40);

    private SudokuSolverGUI mainFrame;
    private JPanel sidePanel;
    //private JScrollPane currentSessionPane;
    private JPanel currentSessionPane;
    private SudokuAnswerBoards currentListOfAnswerBoards;
    private final ArrayList<JButton> listOfButtons;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public CurrentSessionDisplayPanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        this.currentListOfAnswerBoards = mainFrame.currentListOfAnswerBoards;
        this.listOfButtons = new ArrayList<>();

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        currentSessionPane = new JPanel();
        currentSessionPane.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        displayCurrentSession();

        add(currentSessionPane);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void displayCurrentSession() {
        JLabel title = new JLabel("- - - Current Session - - -");
        title.setFont(LABEL_FONT);
        title.setForeground(Color.WHITE);
        add(title);
        displayCurrentBoard();
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void displayCurrentBoard() {
        JPanel viewPanel = new JPanel();
        try {
            for (SudokuAnswerBoard board : currentListOfAnswerBoards.getListOfAnswerBoards()) {
                String text = board.getName();
                String command = text;
                JButton button = createJButton(text, command);
                listOfButtons.add(button);
            }
            viewPanel.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));
            viewPanel.setAutoscrolls(true);
            for (JButton button : listOfButtons) {
                button.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, 50));
                viewPanel.add(button);
            }
            currentSessionPane.add(viewPanel);
        } catch (NullPointerException e) {
            currentSessionPane.add(viewPanel);
        }
    }

    //MODIFIES: this
    //EFFECTS: Constructs a JButton given its text and action command
    private JButton createJButton(String text, String command) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setActionCommand(command);
        button.setHorizontalAlignment(SwingConstants.LEFT);
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
        for (JButton button : listOfButtons) {
            String boardName = button.getText();
            if (command.equals(boardName)) {
                mainFrame.displaySudokuAnswerBoard(boardName, currentListOfAnswerBoards);
            }
        }
    }

}
