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

import static gui.SessionSidePanel.DISPLAY_PANEL_HEIGHT;
import static gui.SessionSidePanel.DISPLAY_PANEL_WIDTH;

//Class - SavedBoardsDisplayPanel
public class SavedBoardsPanel extends JPanel implements ActionListener {

    private static final String FONT_NAME = "Helvetica";
    private static final String JSON_STORAGE = "./data/answers.json";

    private static final Color BUTTON_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color BUTTON_FG_COLOR = Color.BLACK;

    private static final Font BUTTON_FONT = new Font(FONT_NAME, Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 40);

    private final SudokuSolverGUI mainFrame;
    private final JPanel sidePanel;
    private final JPanel savedBoardsPane;
    private SudokuAnswerBoards savedListOfAnswerBoards;
    private final ArrayList<JButton> listOfButtons;
    private final JsonReader jsonReader;

    //REQUIRES: mainFrame
    //MODIFIES: this
    //EFFECTS: Displays all saved boards in a panel
    public SavedBoardsPanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        this.savedListOfAnswerBoards = new SudokuAnswerBoards("Sudoku Solver Answers - GUI");
        this.listOfButtons = new ArrayList<>();
        this.jsonReader = new JsonReader(JSON_STORAGE);

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        savedBoardsPane = new JPanel();

        displaySavedBoards();

        JScrollPane savedBoardsScrollPane = new JScrollPane(savedBoardsPane,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        savedBoardsScrollPane.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT - 75));

        add(savedBoardsScrollPane);
    }

    //MODIFIES: this
    //EFFECTS: Displays title
    public void displaySavedBoards() {
        JLabel title = new JLabel("- - - Saved Boards - - -");
        title.setFont(LABEL_FONT);
        title.setForeground(Color.WHITE);
        add(title);
        displaySavedBoard();
    }

    //MODIFIES: this
    //EFFECTS: Displays all saved boards - from file - with clickable buttons that, on click,
    //         displays the saved board.
    private void displaySavedBoard() {
        JPanel viewPanel = new JPanel();
        try {
            savedListOfAnswerBoards = jsonReader.read();
            for (SudokuAnswerBoard board : savedListOfAnswerBoards.getListOfAnswerBoards()) {
                String text = board.getName();
                String command = text;
                JButton button = createJButton(text, command);
                listOfButtons.add(button);
            }
            viewPanel.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));
            for (JButton button : listOfButtons) {
                button.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, 75));
                viewPanel.add(button);
            }
            savedBoardsPane.add(viewPanel);
        } catch (IOException e) {
            JLabel unableLoad = new JLabel("Unable to load file from destination.");
            unableLoad.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(mainFrame, unableLoad);
        } catch (NullPointerException e) {
            savedBoardsPane.add(viewPanel);
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
