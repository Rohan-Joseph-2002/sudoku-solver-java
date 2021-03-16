package gui;

import model.SudokuAnswerBoard;
import model.SudokuAnswerBoards;
import model.SudokuSolver9By9;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SudokuSolverGUI extends JFrame {
    protected static final int PANEL_STARTING_HEIGHT = 1750;
    private static final String JSON_STORAGE = "./data/answers.json";
    private static final String FONT_NAME = "Helvetica";
    private static final int BOARD_SIZE = 9;
    private static final int PANEL_STARTING_WIDTH = 2550;
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
    private final JsonWriter jsonWriter;
    protected final JsonReader jsonReader;

    private int[][] questionSudokuBoard;
    private int[][] answerSudokuBoard;
    private SudokuSolver9By9 solve9By9;
    protected SudokuAnswerBoards currentListOfAnswerBoards;
    private SudokuAnswerBoards savedListOfAnswerBoards;
    private SessionSidePanel sessionSidePanel;
    private SidePanel sidePanel;

    //private boolean shouldSave;
    //protected boolean shouldLoad;

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        sudokuGridPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        sudokuGridPanel.setPreferredSize(new Dimension(SUDOKU_PANEL_WIDTH, SUDOKU_PANEL_HEIGHT));

        styleTextFields();

        sessionSidePanel = new SessionSidePanel(this, mainPanel);

        sidePanel = new SidePanel(this, mainPanel);

        mainPanel.add(sessionSidePanel);
        mainPanel.add(sidePanel);
        mainPanel.add(sudokuGridPanel);

        createFrame();

        currentListOfAnswerBoards = new SudokuAnswerBoards("Current Session");
        savedListOfAnswerBoards = new SudokuAnswerBoards("Sudoku Solver Answers - GUI");

        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);

        //shouldLoad = false;
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
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

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void createFrame() {
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(PANEL_STARTING_WIDTH, PANEL_STARTING_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void solveSudokuQuestionBoard() {
        if (!checkValidInput()) {
            JLabel label = new JLabel("Invalid User Input. All values must be numbers  between 1 and 9");
            label.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(frame, label);
        } else {
            guiToSudokuQuestionBoard();
            if (solve9By9.solveBoard(questionSudokuBoard)) {
                sudokuAnswerBoardToGUI();
            } else {
                JLabel label = new JLabel("Unfortunately, a solution doesn't exist :(");
                label.setFont(LABEL_FONT);
                JOptionPane.showMessageDialog(frame, label);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void guiToSudokuQuestionBoard() {
        questionSudokuBoard = new int[9][9];
        getSudokuQuestionBoard(questionSudokuBoard);
        solve9By9 = new SudokuSolver9By9(questionSudokuBoard);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void sudokuAnswerBoardToGUI() {
        boolean shouldAdd = false;
        answerSudokuBoard = solve9By9.getSolvedBoard();
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex].setText(String.valueOf(answerSudokuBoard[rowIndex][columnIndex]));
            }
        }

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
                JLabel invalid = new JLabel("Invalid option!");
                invalid.setFont(LABEL_FONT);
                JOptionPane.showMessageDialog(frame, invalid);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    private void addToCurrentSession() {
        JLabel label = new JLabel("NOTE: Once you have set a name, it CANNOT be changed. "
                + "Please enter a name for your answer board: ");
        label.setFont(LABEL_FONT);
        String answerBoardName = JOptionPane.showInputDialog(frame, label);
        SudokuAnswerBoard sudokuAnswerBoard = new SudokuAnswerBoard(answerBoardName, answerSudokuBoard);
        currentListOfAnswerBoards.add(sudokuAnswerBoard);
        updateSession();
    }


    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void clearDisplayGrid() {
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                TEXT_FIELDS[rowIndex][columnIndex].setText("");
                TEXT_FIELDS[rowIndex][columnIndex].setForeground(DISPLAY_NUM_COLOR);
            }
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
//    public void saveToFile() {
//        shouldSave = true;
//        while (shouldSave) {
//            if (shouldLoad) {
//                saveToFile();
//                shouldSave = false;
//            } else {
//                fileNotLoadedShouldSave();
//            }
//        }
//    }

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
            JLabel save = new JLabel("Your Sudoku Answer Boards has been saved successfully to "
                    + JSON_STORAGE);
            save.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(frame, save);
            updateSession();
        } catch (FileNotFoundException e) {
            JLabel unableSave = new JLabel("Unable to save to destination file.");
            unableSave.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(frame, unableSave);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
//    private void fileNotLoadedShouldSave() {
//        JLabel notLoaded = new JLabel("You have not loaded in the file. "
//                + "If you save without loading, you will overwrite ALL existing data. "
//                + "Are you sure you want to proceed? ");
//        notLoaded.setFont(LABEL_FONT);
//        String userInput = JOptionPane.showInputDialog(frame, notLoaded);
//        if (userInput.equalsIgnoreCase("yes")) {
//            shouldLoad = true;
//        } else if (userInput.equalsIgnoreCase("no")) {
//            shouldSave = false;
//        } else {
//            JLabel invalid = new JLabel("Invalid option!");
//            invalid.setFont(LABEL_FONT);
//            JOptionPane.showMessageDialog(frame, invalid);
//        }
//    }

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
            JLabel unableLoad = new JLabel("Unable to load file from destination.");
            unableLoad.setFont(LABEL_FONT);
            JOptionPane.showMessageDialog(frame, unableLoad);
        }
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void updateSession() {
        mainPanel.remove(sessionSidePanel);
        sessionSidePanel = new SessionSidePanel(this, mainPanel);

        mainPanel.add(sessionSidePanel);
        mainPanel.add(sidePanel);
        mainPanel.add(sudokuGridPanel);

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

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
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
            replaceEmptyFieldWithZero(rowTextField);
            int num = Integer.parseInt(rowTextField[columnIndex].getText());
            row[columnIndex] = num;
        }
        return row;
    }

    //MODIFIES: this
    //EFFECTS: Replaces an empty textField with a 0 for the Sudoku Question Board
    private void replaceEmptyFieldWithZero(JTextField[] rowTextField) {
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
