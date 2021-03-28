package gui;

import javax.swing.*;
import java.awt.*;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;
import static gui.SudokuSolverGUI.PANEL_STARTING_WIDTH;

public class SessionSidePanel extends JPanel {
    protected static final int DISPLAY_PANEL_WIDTH = PANEL_STARTING_WIDTH / 5;
    protected static final int DISPLAY_PANEL_HEIGHT = PANEL_STARTING_HEIGHT / 2;

    private SudokuSolverGUI mainFrame;
    private JPanel sessionSidePanel;
    protected CurrentSessionDisplayPanel currentSessionDisplayPanel;
    protected SavedBoardsDisplayPanel savedBoardsDisplayPanel;

    //REQUIRES: mainFrame
    //MODIFIES: this
    //EFFECTS: Creates a side panel that displays the boards in the current session,
    //         as well as the already saved boards.
    public SessionSidePanel(SudokuSolverGUI mainFrame, JPanel sessionSidePanel) {
        this.mainFrame = mainFrame;
        this.sessionSidePanel = sessionSidePanel;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        currentSessionDisplayPanel = new CurrentSessionDisplayPanel(mainFrame, sessionSidePanel);

        savedBoardsDisplayPanel = new SavedBoardsDisplayPanel(mainFrame, sessionSidePanel);

        add(currentSessionDisplayPanel);
        add(savedBoardsDisplayPanel);
    }
}
