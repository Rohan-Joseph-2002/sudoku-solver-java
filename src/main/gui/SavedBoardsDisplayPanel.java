package gui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;

public class SavedBoardsDisplayPanel extends JPanel implements ActionListener {
    private static final int DISPLAY_PANEL_WIDTH = 550;
    private static final int DISPLAY_PANEL_HEIGHT = PANEL_STARTING_HEIGHT / 2 - 50;

    private static final String FONT_NAME = "Helvetica";
    private static final String JSON_STORAGE = "./data/answers.json";

    private static final Color BUTTON_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color BUTTON_FG_COLOR = Color.BLACK;

    private static final Font BUTTON_FONT = new Font(FONT_NAME, Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 40);

    private final SudokuSolverGUI mainFrame;
    private final JPanel sidePanel;
    //private final JScrollPane savedBoardsScrollPane;
    private final JPanel savedBoardsScrollPane;
    //private boolean shouldLoad;
    private SudokuAnswerBoards savedListOfAnswerBoards;
    private ArrayList<JButton> listOfButtons;
    private JsonReader jsonReader;

    public SavedBoardsDisplayPanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        //this.shouldLoad = mainFrame.shouldLoad;
        savedListOfAnswerBoards = new SudokuAnswerBoards("Sudoku Solver Answers - GUI");
        listOfButtons = new ArrayList<>();
        jsonReader = new JsonReader(JSON_STORAGE);

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

//        savedBoardsScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        savedBoardsScrollPane.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        savedBoardsScrollPane = new JPanel();

        displaySavedBoards();

        add(savedBoardsScrollPane);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void displaySavedBoards() {
        JLabel title = new JLabel("- - - Saved Boards - - -");
        title.setFont(LABEL_FONT);
        title.setForeground(Color.WHITE);
        add(title);
        //if (shouldLoad) {
        displaySavedBoard();
        //}
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void displaySavedBoard() {
        try {
            savedListOfAnswerBoards = jsonReader.read();
            for (SudokuAnswerBoard board : savedListOfAnswerBoards.getListOfAnswerBoards()) {
                String text = board.getName();
                String command = text;
                JButton button = createJButton(text, command);
                listOfButtons.add(button);
            }
            JPanel viewPanel = new JPanel();
            viewPanel.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));
            viewPanel.setAutoscrolls(true);
            for (JButton button : listOfButtons) {
                button.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, 50));
                viewPanel.add(button);
            }
            savedBoardsScrollPane.add(viewPanel);
        } catch (IOException e) {
            JLabel unableLoad = new JLabel("Unable to load file from destination.");
            unableLoad.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(mainFrame, unableLoad);
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
    //EFFECTS: Processes the action command of buttons in this panel
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        for (JButton button : listOfButtons) {
            String boardName = button.getText();
            if (command.equals(boardName)) {
                mainFrame.displaySudokuAnswerBoard(boardName, savedListOfAnswerBoards);
            }
        }
    }
}
