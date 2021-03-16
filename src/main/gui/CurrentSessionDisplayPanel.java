package gui;

import model.SudokuAnswerBoards;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;

public class CurrentSessionDisplayPanel extends JPanel implements ActionListener {
    private static final int DISPLAY_PANEL_WIDTH = 550;
    private static final int DISPLAY_PANEL_HEIGHT = PANEL_STARTING_HEIGHT / 2 - 50;

    private static final String FONT_NAME = "Helvetica";
    private static final String JSON_STORAGE = "./data/answers.json";

    private static final Color BUTTON_BG_COLOR = Color.LIGHT_GRAY;
    private static final Color BUTTON_FG_COLOR = Color.BLACK;

    private static final Font BUTTON_FONT = new Font(FONT_NAME, Font.BOLD, 10);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 40);

    private SudokuSolverGUI mainFrame;
    private JPanel sidePanel;
    private JScrollPane currentSessionScrollPane;
    private SudokuAnswerBoards currentListOfAnswerBoards;
    private JsonReader jsonReader;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public CurrentSessionDisplayPanel(SudokuSolverGUI mainFrame, JPanel sidePanel) {
        this.mainFrame = mainFrame;
        this.sidePanel = sidePanel;
        currentListOfAnswerBoards = new SudokuAnswerBoards("Current Session");
        jsonReader = new JsonReader(JSON_STORAGE);

        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        currentSessionScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        currentSessionScrollPane.setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));
        displayCurrentSession();

        add(currentSessionScrollPane);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void displayCurrentSession() {
        JLabel title = new JLabel("- - - Current Session - - -");
        title.setFont(LABEL_FONT);
        title.setForeground(Color.WHITE);
        add(title);
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
        //stub
    }

}
