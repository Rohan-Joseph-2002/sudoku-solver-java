package gui;

import javax.swing.*;
import java.awt.*;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;
import static gui.SudokuSolverGUI.PANEL_STARTING_WIDTH;

//Class - SessionSidePanel
public class SessionSidePanel extends JPanel {
    protected static final int DISPLAY_PANEL_WIDTH = PANEL_STARTING_WIDTH / 5;
    protected static final int DISPLAY_PANEL_HEIGHT = PANEL_STARTING_HEIGHT / 2;

    private SudokuSolverGUI mainFrame;
    private JPanel sessionSidePanel;
    protected CurrentSessionPanel currentSessionPanel;
    protected SavedBoardsPanel savedBoardsPanel;

    //REQUIRES: mainFrame
    //MODIFIES: this
    //EFFECTS: Creates a side panel that displays the boards in the current session,
    //         as well as the already saved boards.
    public SessionSidePanel(SudokuSolverGUI mainFrame, JPanel sessionSidePanel) {
        this.mainFrame = mainFrame;
        this.sessionSidePanel = sessionSidePanel;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT));

        currentSessionPanel = new CurrentSessionPanel(mainFrame, sessionSidePanel);

        savedBoardsPanel = new SavedBoardsPanel(mainFrame, sessionSidePanel);

        add(currentSessionPanel);
        add(savedBoardsPanel);
    }
}
