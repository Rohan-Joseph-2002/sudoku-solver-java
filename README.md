# Sudoku Solver - CPSC 210 Term Project 

## Proposal

For my CPSC 210 Term Project, I propose to build a Sudoku Solver. My application
will be able to take user sudoku board inputs, check if it solvable and then solve 
sudoku board, displaying the solved board as a result. If the sudoku board is 
not solvable, the application will print out a statement detailing this. It is
important to note that the current iteration of my application will be limited to
9 x 9 sudoku boards.

This application can be used by anyone with an interest in sudoku but needs help
in solving a sudoku board. This application serves to check whether a solvable, 
as well as outputting the solved board for the user.  

Personally, this project is off interest to me because I have been solving sudoku
for many years now, and I wanted to incorporate my own person interest in
sudoku into coding, and this term project serves as an effective method for me
to do just that. In addition, my application is also inspired by the sudoku 
solver that I saw in CPSC 110.

## User Stories

1. As a user, I want to be able to input numbers (1-9) into specific positions of an
empty sudoku board.
2. As a user, I want to be able represent spaces in an inputted sudoku question 
board.
3. As a user, I want to be able to know if my sudoku board is solvable.
4. As a user, I want to be able to see the result of solving a given sudoku
question board (i.e. the solved sudoku board) if the inputted sudoku board is
solvable.
5. As a user, I want to be able to have the option to try another sudoku board to solve,
without having to re-run the console application.
6. As a user, I want to be able to save an SudokuAnswerBoard to file.
7. As a user, I want to be able load a SudokuAnswerBoard from file.
8. As a user, I want to be able to choose whether I want to save an answer board 
(after each answer) board is generated, or not.
9. As a user, I want to be prompted when I am choosing a name for an SudokuAnswerBoard,
if a given name has already been used.
10. As a user, I want to be notified if a given Sudoku question board has already
been answered, and be directed to the name of the saved file that contains
the SudokuAnswerBoard.

## Phase 4: Task 2
**Task Completed:** "Test and design a class in your model package that is robust"

**Class (in the model Package):** SudokuSolver9By9

**Methods that achieve this:**
- solveBoard(int[][] board) *=> on line 29*
- getAssignment(int[][] board, int r, int c) *=> on line 69*
- canAddNum(int[][] board, int rowIndex, int columnIndex, int num) *=> on line 92*
- inSameRow(int[][] board, int rowIndex, int num) *=> on line 102*
- inSameColumn(int[][] board, int columnIndex, int num) *=> on line 116*
- inSameSubGrid(int[][] board, int rowIndex, int columnIndex, int num) *=> on line 130*

## Phase 4: Task 3
**Reflecting on my design, based on the UML Class Diagram**

See *UML_Design_Diagram.png*

I personally believe that there is no further need for me to refactor my design,
as my design, as it is, is cohesive, and thus negates the need for refactoring.