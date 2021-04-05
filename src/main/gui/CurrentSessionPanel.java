package gui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static gui.SessionSidePanel.DISPLAY_PANEL_HEIGHT;
import static gui.SessionSidePanel.DISPLAY_PANEL_WIDTH;

//Class - CurrentSessionDisplayPanel
public class CurrentSessionPanel extends JPanel implements ActionListener {
    private static final String FONT_NAME = "Helvetica";

    private static final Color BUTTON_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color BUTTON_FG_COLOR = Color.BLACK;

    private static final Font BUTTON_FONT = new Font(FONT_NAME, Font.BOLD, 30);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 40);

    private SudokuSolverGUI mainFrame;
    private JPanel sidePanel;
    private JPanel currentSessionPane;
    private SudokuAnswerBoards currentListOfAnswerBoards;
    private final ArrayList<JButton> listOfButtons;

    //REQUIRES: mainFrame
    //MODIFIES: this
    //EFFECTS: Displays all current session boards in a panel
    public CurrentSessionPanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        this.currentListOfAnswerBoards = mainFrame.currentListOfAnswerBoards;
        this.listOfButtons = new ArrayList<>();

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        currentSessionPane = new JPanel();

        displayCurrentSession();

        JScrollPane currentSessionScrollPane = new JScrollPane(currentSessionPane,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        currentSessionScrollPane.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT - 75));

        add(currentSessionScrollPane);
    }

    //MODIFIES: this
    //EFFECTS: Displays title
    public void displayCurrentSession() {
        JLabel title = new JLabel("- - - Current Session - - -");
        title.setFont(LABEL_FONT);
        title.setForeground(Color.WHITE);
        add(title);
        displayCurrentBoard();
    }

    //MODIFIES: this
    //EFFECTS: Displays all current boards with clickable buttons that, on click,
    //         displays the board.
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
            for (JButton button : listOfButtons) {
                button.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, 75));
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
