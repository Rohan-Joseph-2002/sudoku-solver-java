package gui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import model.SudokuSolver9By9;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolverGUI extends JFrame {
    protected static final int PANEL_STARTING_HEIGHT = 1750;
    protected static final int PANEL_STARTING_WIDTH = 2550;
    private static final String JSON_STORAGE = "./data/answers.json";
    private static final String FONT_NAME = "Helvetica";
    private static final int BOARD_SIZE = 9;
    private static final int SUDOKU_PANEL_WIDTH = 1750;
    private static final int SUDOKU_PANEL_HEIGHT = PANEL_STARTING_HEIGHT;

    private static final Color DISPLAY_NUM_COLOR = Color.GREEN;
    private static final Color BORDER_COLOR = Color.BLACK;

    private static final Font INPUT_FONT = new Font(FONT_NAME, Font.BOLD, 75);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 25);

    private static final Border PANEL_BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 1);
    private static final JTextField[][] TEXT_FIELDS = new JTextField[BOARD_SIZE][BOARD_SIZE];

    private final JFrame frame;
    private final JPanel mainPanel;
    private final JPanel sudokuGridPanel;
    private final SidePanel sidePanel;
    private final JsonWriter jsonWriter;
    protected final JsonReader jsonReader;

    private int[][] questionSudokuBoard;
    private int[][] answerSudokuBoard;
    private SudokuSolver9By9 solve9By9;
    protected SudokuAnswerBoards currentListOfAnswerBoards;
    private SudokuAnswerBoards savedListOfAnswerBoards;
    private SessionSidePanel sessionSidePanel;

    //MODIFIES: this
    //EFFECTS: SudokuSolverGUI constructor
    public SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        sudokuGridPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        sudokuGridPanel.setPreferredSize(new Dimension(SUDOKU_PANEL_WIDTH, SUDOKU_PANEL_HEIGHT));

        styleTextFields();

        sessionSidePanel = new SessionSidePanel(this, mainPanel);

        sidePanel = new SidePanel(this, mainPanel);

        mainPanel.add(sidePanel);
        mainPanel.add(sudokuGridPanel);
        mainPanel.add(sessionSidePanel);

        createFrame();

        currentListOfAnswerBoards = new SudokuAnswerBoards("Current Session");
        savedListOfAnswerBoards = new SudokuAnswerBoards("Sudoku Solver Answers - GUI");

        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
    }

    //REQUIRES: JTextField[9][9]
    //MODIFIES: this
    //EFFECTS: Styles the width, height, font, size and color of every JTextField
    private void styleTextFields() {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex] = new JTextField();
                TEXT_FIELDS[rowIndex][columnIndex].setForeground(DISPLAY_NUM_COLOR);
                TEXT_FIELDS[rowIndex][columnIndex].setFont(INPUT_FONT);
                TEXT_FIELDS[rowIndex][columnIndex].setHorizontalAlignment(JTextField.CENTER);
                TEXT_FIELDS[rowIndex][columnIndex].setBorder(PANEL_BORDER);
                sudokuGridPanel.add(TEXT_FIELDS[rowIndex][columnIndex]);
            }
        }
    }

    //REQUIRES: mainPanel
    //MODIFIES: this
    //EFFECTS: Creates the GUI Frame
    private void createFrame() {
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(PANEL_STARTING_WIDTH, PANEL_STARTING_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //REQUIRES: JTextField[9][9]
    //MODIFIES: this
    //EFFECTS: Takes a user input Sudoku Question Board, turns it in a 9 by 9 matrix,
    //         puts it through the Sudoku9by9Solver.
    //         If solvable is true, displays the solved Sudoku Board, or a
    //         JOptionPane indicating that the board cannot be solved.
    public void solveSudokuQuestionBoard() {
        if (!checkValidInput()) {
            showMessage("Invalid User Input. All values must be numbers  between 1 and 9");
        } else {
            questionSudokuBoard = new int[BOARD_SIZE][BOARD_SIZE];
            getSudokuQuestionBoard(questionSudokuBoard);
            solve9By9 = new SudokuSolver9By9(questionSudokuBoard);
            try {
                sudokuAnswerBoardToGUI();
            } catch (NullPointerException e) {
                for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
                    for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                        if (TEXT_FIELDS[rowIndex][columnIndex].getText().equals("0")) {
                            TEXT_FIELDS[rowIndex][columnIndex].setText("");
                        }
                    }
                }
                showMessage("Unfortunately, a solution doesn't exist :(");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Gets the solved Sudoku Answer Board and outputs it on the Sudoku Grid Panel
    //         Prompts the user whether they want to add the answer board to the current session
    //         or not.
    private void sudokuAnswerBoardToGUI() {
        boolean shouldAdd = false;
        answerSudokuBoard = solve9By9.getSolvedBoard();
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex].setText(String.valueOf(answerSudokuBoard[rowIndex][columnIndex]));
            }
        }
        playPopSound();
        while (!shouldAdd) {
            JLabel choice = new JLabel("Would you like to add this board to the current session? ");
            choice.setFont(LABEL_FONT);
            String choiceAdd = JOptionPane.showInputDialog(frame, choice);
            if (choiceAdd.equalsIgnoreCase("yes")) {
                addToCurrentSession();
                shouldAdd = true;
            } else if (choiceAdd.equalsIgnoreCase("no")) {
                shouldAdd = true;
            } else {
                showMessage("Invalid option!");
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds the Sudoku Answer Board to the current session
    private void addToCurrentSession() {
        boolean sameName = true;
        List<String> keyList = new ArrayList<>();
        returnBoardNamesAndAnswerBoards(keyList);
        while (sameName) {
            String boardName = promptForBoardName();
            if (keyList.contains(boardName)) {
                showMessage("This name is being used. Please try again!");
            } else {
                SudokuAnswerBoard sudokuAnswerBoard = new SudokuAnswerBoard(boardName, answerSudokuBoard);
                currentListOfAnswerBoards.add(sudokuAnswerBoard);
                updateSession();
                sameName = false;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Displays a given String in a JOptionPane.showMessageDialog box
    private void showMessage(String s) {
        JLabel message = new JLabel(s);
        message.setFont(LABEL_FONT);
        JOptionPane.showMessageDialog(frame, message);
    }

    //EFFECTS: Adds every single board name - in both the current session and saved boards -
    //         to a list of board names (keyList)
    private void returnBoardNamesAndAnswerBoards(List<String> keyList) {
        for (SudokuAnswerBoard savedBoard : savedListOfAnswerBoards.getListOfAnswerBoards()) {
            keyList.add(savedBoard.getName());
        }
        for (SudokuAnswerBoard currentBoard : currentListOfAnswerBoards.getListOfAnswerBoards()) {
            keyList.add(currentBoard.getName());
        }
    }

    //MODIFIES: this
    //EFFECTS: Prompts the user for name for a Sudoku Answer Board
    private String promptForBoardName() {
        JLabel label = new JLabel("NOTE: Once you have set a name, it CANNOT be changed. "
                + "Please enter a name for your answer board: ");
        label.setFont(LABEL_FONT);
        return JOptionPane.showInputDialog(frame, label);
    }

    //EFFECTS: Plays pop sound
    private void playPopSound() {
        String popSound = "./data/pop-sound.wav";
        try {
            File path = new File(popSound);
            AudioInputStream input = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip();
            clip.open(input);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MODIFIES: this
    //EFFECTS: Clears ever JTextField in the Sudoku Grid Panel
    public void clearDisplayGrid() {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex].setText("");
                TEXT_FIELDS[rowIndex][columnIndex].setForeground(DISPLAY_NUM_COLOR);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Saves the SudokuAnswerBoards to File
    public void saveAnswerBoards() {
        for (SudokuAnswerBoard board : currentListOfAnswerBoards.getListOfAnswerBoards()) {
            savedListOfAnswerBoards.add(board);
        }
        currentListOfAnswerBoards = null;
        try {
            jsonWriter.openWriter();
            jsonWriter.write(savedListOfAnswerBoards);
            jsonWriter.closeWriter();
            showMessage("Your Sudoku Answer Boards has been saved successfully to "
                    + JSON_STORAGE);
            updateSession();
        } catch (FileNotFoundException e) {
            showMessage("Unable to save to destination file.");
        }
    }

    //MODIFIES: this
    //EFFECTS: Loads SudokuAnswerBoards from File
    public void loadAnswerBoards() {
        try {
            savedListOfAnswerBoards = jsonReader.read();
            JLabel load = new JLabel("Loaded " + savedListOfAnswerBoards.getName() + " from " + JSON_STORAGE);
            load.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(frame, load);
            //shouldLoad = true;
        } catch (IOException e) {
            showMessage("Unable to load file from destination.");
        }
    }

    //REQUIRES: mainPanel
    //MODIFIES: this
    //EFFECTS: Updates the mainPanel after an event click
    public void updateSession() {
        mainPanel.remove(sessionSidePanel);
        sessionSidePanel = new SessionSidePanel(this, mainPanel);

        mainPanel.add(sidePanel);
        mainPanel.add(sudokuGridPanel);
        mainPanel.add(sessionSidePanel);

        revalidate();

        createFrame();
    }

    //MODIFIES: this
    //EFFECTS: Displays a SudokuAnswerBoard on the SudokuPanel, based on a given board name
    public void displaySudokuAnswerBoard(String boardName, SudokuAnswerBoards listOfBoards) {
        int[][] answerBoard = new int[BOARD_SIZE][BOARD_SIZE];
        for (SudokuAnswerBoard board : listOfBoards.getListOfAnswerBoards()) {
            if (boardName.equals(board.getName())) {
                answerBoard = board.returnAnswerBoard();
            }
        }
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex].setText(String.valueOf(answerBoard[rowIndex][columnIndex]));
            }
        }
    }

    //EFFECTS: Checks if the user input is valid
    private boolean checkValidInput() {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                String entry = TEXT_FIELDS[rowIndex][columnIndex].getText();
                boolean invalidEntry = entry.equals("");
                if (!invalidEntry) {
                    try {
                        int num = Integer.parseInt(entry);
                        if (num < 1 || num > BOARD_SIZE) {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //EFFECTS: Returns a textField as a 9 by 9 matrix
    private void getSudokuQuestionBoard(int[][] questionSudokuBoard) {
        questionSudokuBoard[0] = getRowOne();
        questionSudokuBoard[1] = getRowTwo();
        questionSudokuBoard[2] = getRowThree();
        questionSudokuBoard[3] = getRowFour();
        questionSudokuBoard[4] = getRowFive();
        questionSudokuBoard[5] = getRowSix();
        questionSudokuBoard[6] = getRowSeven();
        questionSudokuBoard[7] = getRowEight();
        questionSudokuBoard[8] = getRowNine();
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowOne() {
        JTextField[] rowTextField = TEXT_FIELDS[0];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowTwo() {
        JTextField[] rowTextField = TEXT_FIELDS[1];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowThree() {
        JTextField[] rowTextField = TEXT_FIELDS[2];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowFour() {
        JTextField[] rowTextField = TEXT_FIELDS[3];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowFive() {
        JTextField[] rowTextField = TEXT_FIELDS[4];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowSix() {
        JTextField[] rowTextField = TEXT_FIELDS[5];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowSeven() {
        JTextField[] rowTextField = TEXT_FIELDS[6];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowEight() {
        JTextField[] rowTextField = TEXT_FIELDS[7];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //REQUIRES: Number in [1, 9]
    //MODIFIES: this
    //EFFECTS: Returns the row specific textField as an int[]
    private int[] getRowNine() {
        JTextField[] rowTextField = TEXT_FIELDS[8];
        int[] row = new int[BOARD_SIZE];
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            replaceEmptyFieldWithEmpty(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //MODIFIES: this
    //EFFECTS: Replaces an empty textField with a 0 for the Sudoku Question Board
    private void replaceEmptyFieldWithEmpty(JTextField[] rowTextField) {
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            String emptyField = "";
            JTextField field = rowTextField[columnIndex];
            String text = field.getText();
            if (emptyField.equals(text)) {
                field.setText("0");
            }
        }
    }

}
