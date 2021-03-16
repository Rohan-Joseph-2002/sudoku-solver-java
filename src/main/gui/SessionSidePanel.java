package gui;

import javax.swing.*;
import java.awt.*;

import static gui.SudokuSolverGUI.PANEL_STARTING_HEIGHT;

public class SessionSidePanel extends JPanel {
    static final int DISPLAY_PANEL_WIDTH = 550;

    private SudokuSolverGUI mainFrame;
    private JPanel sessionSidePanel;
    protected CurrentSessionDisplayPanel currentSessionDisplayPanel;
    protected SavedBoardsDisplayPanel savedBoardsDisplayPanel;

    public SessionSidePanel(SudokuSolverGUI mainFrame, JPanel sessionSidePanel) {
        this.mainFrame = mainFrame;
        this.sessionSidePanel = sessionSidePanel;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(DISPLAY_PANEL_WIDTH, PANEL_STARTING_HEIGHT));

        currentSessionDisplayPanel = new CurrentSessionDisplayPanel(mainFrame, sessionSidePanel);

        savedBoardsDisplayPanel = new SavedBoardsDisplayPanel(mainFrame, sessionSidePanel);

        add(currentSessionDisplayPanel);
        add(savedBoardsDisplayPanel);
    }
}
