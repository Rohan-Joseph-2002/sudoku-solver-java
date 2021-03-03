package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new SudokuSolver();
        } catch (FileNotFoundException e) {
            System.out.println("Application, unable to run: File not Found");
        }
    }

}
