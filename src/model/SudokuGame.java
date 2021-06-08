package model;

import ui.GamePanel;

import java.awt.event.KeyEvent;

public class SudokuGame {

    // this is an array of numbers that together make up a group in sudoku, in order of: rows -> cols -> squares
    public final int[][] GROUPS = {
            { 0,  1,  2,  3,  4,  5,  6,  7,  8}, // rows
            { 9, 10, 11, 12, 13, 14, 15, 16, 17},
            {18, 19, 20, 21, 22, 23, 24, 25, 26},
            {27, 28, 29, 30, 31, 32, 33, 34, 35},
            {36, 37, 38, 39, 40, 41, 42, 43, 44},
            {45, 46, 47, 48, 49, 50, 51, 52, 53},
            {54, 55, 56, 57, 58, 59, 60, 61, 62},
            {63, 64, 65, 66, 67, 68, 69, 70, 71},
            {72, 73, 74, 75, 76, 77, 78, 79, 80}, // end of rows
            { 0,  9, 18, 27, 36, 45, 54, 63, 72}, // cols
            { 1, 10, 19, 28, 37, 46, 55, 64, 73},
            { 2, 11, 20, 29, 38, 47, 56, 65, 74},
            { 3, 12, 21, 30, 39, 48, 57, 66, 75},
            { 4, 13, 22, 31, 40, 49, 58, 67, 76},
            { 5, 14, 23, 32, 41, 50, 59, 68, 77},
            { 6, 15, 24, 33, 42, 51, 60, 69, 78},
            { 7, 16, 25, 34, 43, 52, 61, 70, 79},
            { 8, 17, 26, 35, 44, 53, 62, 71, 80}, // end of cols
            { 0,  1,  2,  9, 10, 11, 18, 19, 20}, // squares
            { 3,  4,  5, 12, 13, 14, 21, 22, 23},
            { 6,  7,  8, 15, 16, 17, 24, 25, 26},
            {27, 28, 29, 36, 37, 38, 45, 46, 47},
            {30, 31, 32, 39, 40, 41, 48, 49, 50},
            {33, 34, 35, 42, 43, 44, 51, 52, 53},
            {54, 55, 56, 63, 64, 65, 72, 73, 74},
            {57, 58, 59, 66, 67, 68, 75, 76, 77},
            {60, 61, 62, 69, 70, 71, 78, 79, 80} // finally
    };

    // represents the board
    public Box[] board;

    public SudokuSolver solver;
    public GamePanel gp;

    // represents the selected square
    public int selected = 0;

    public SudokuGame() {
        board = new Box[81];
        for(int i = 0; i < 81; i++) {
            board[i] = new Box();
        }
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void handleClick(int i) {
        selected = i;
    }

    public void handlePress(int i) {
        switch (i) {
            case KeyEvent.VK_0:
                board[selected].setValue(0);
                break;
            case KeyEvent.VK_1:
                board[selected].setValue(1);
                break;
            case KeyEvent.VK_2:
                board[selected].setValue(2);
                break;
            case KeyEvent.VK_3:
                board[selected].setValue(3);
                break;
            case KeyEvent.VK_4:
                board[selected].setValue(4);
                break;
            case KeyEvent.VK_5:
                board[selected].setValue(5);
                break;
            case KeyEvent.VK_6:
                board[selected].setValue(6);
                break;
            case KeyEvent.VK_7:
                board[selected].setValue(7);
                break;
            case KeyEvent.VK_8:
                board[selected].setValue(8);
                break;
            case KeyEvent.VK_9:
                board[selected].setValue(9);
                break;
            case KeyEvent.VK_L:
                lockState();
                break;
            case KeyEvent.VK_K:
                unlockState();
                break;
            case KeyEvent.VK_SPACE:
                solver = new SudokuSolver(this);
                solver.solve(0);
                break;
            case KeyEvent.VK_BACK_SPACE:
                solver = new SudokuSolver(this);
                solver.visualSolve(0);
                break;
            case KeyEvent.VK_R:
                for(int n = 0; n < 81; n++) {
                    if(!board[n].locked) {
                        board[n] = new Box();
                    }
                }
                break;
            case KeyEvent.VK_T:
                board = new Box[81];
                for(int n = 0; n < 81; n++) {
                    board[n] = new Box();
                }
                break;
            case KeyEvent.VK_P:
                printBoard();
                break;
            case KeyEvent.VK_Z:
                setBoard0();
                break;
            default:
                break;
        }
        isBoardViable();
    }

    /**
     * locks the boxes that currently have a value
     */
    private void lockState() {
        for(Box box: board) {
            if (box.value != 0) {
                box.locked = true;
            }
        }
    }

    private void unlockState() {
        for(Box box: board) {
            if (box.value != 0) {
                box.locked = false;
            }
        }
    }

    public boolean isBoardViable() {
        setAllViable();
        for(int[] group: GROUPS) {
            for(int n0 = 0; n0 < 9; n0++) {
                for(int n1 = 0; n1 < 9; n1++) {
                    if(n0 != n1) {
                        boolean toReturn = (board[group[n0]].value != board[group[n1]].value)
                                || (board[group[n0]].value == 0) || (board[group[n1]].value == 0);
                        if(!toReturn) {
                            board[group[n0]].viable = false;
                            board[group[n1]].viable = false;
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void setAllViable() {
        for(Box box: board) {
            box.viable = true;
        }
    }

    public boolean complete() {
        return isBoardViable() && boardFilled();
    }

    private boolean boardFilled() {
        for(Box box: board) {
            if(box.value == 0) return false;
        }
        return true;
    }

    private void printBoard() {
        for(int i = 0; i < 81; i++) {
            System.out.printf("board[%d].setValue(%d);\n", i, board[i].value);
        }
    }

    private void setBoard0() {
        board[0].setValue(0);
        board[1].setValue(0);
        board[2].setValue(0);
        board[3].setValue(2);
        board[4].setValue(6);
        board[5].setValue(0);
        board[6].setValue(7);
        board[7].setValue(0);
        board[8].setValue(1);
        board[9].setValue(6);
        board[10].setValue(8);
        board[11].setValue(0);
        board[12].setValue(0);
        board[13].setValue(7);
        board[14].setValue(0);
        board[15].setValue(0);
        board[16].setValue(9);
        board[17].setValue(0);
        board[18].setValue(1);
        board[19].setValue(9);
        board[20].setValue(0);
        board[21].setValue(0);
        board[22].setValue(0);
        board[23].setValue(4);
        board[24].setValue(5);
        board[25].setValue(0);
        board[26].setValue(0);
        board[27].setValue(8);
        board[28].setValue(2);
        board[29].setValue(0);
        board[30].setValue(1);
        board[31].setValue(0);
        board[32].setValue(0);
        board[33].setValue(0);
        board[34].setValue(4);
        board[35].setValue(0);
        board[36].setValue(0);
        board[37].setValue(0);
        board[38].setValue(4);
        board[39].setValue(6);
        board[40].setValue(0);
        board[41].setValue(2);
        board[42].setValue(9);
        board[43].setValue(0);
        board[44].setValue(0);
        board[45].setValue(0);
        board[46].setValue(5);
        board[47].setValue(0);
        board[48].setValue(0);
        board[49].setValue(0);
        board[50].setValue(3);
        board[51].setValue(0);
        board[52].setValue(2);
        board[53].setValue(8);
        board[54].setValue(0);
        board[55].setValue(0);
        board[56].setValue(9);
        board[57].setValue(3);
        board[58].setValue(0);
        board[59].setValue(0);
        board[60].setValue(0);
        board[61].setValue(7);
        board[62].setValue(4);
        board[63].setValue(0);
        board[64].setValue(4);
        board[65].setValue(0);
        board[66].setValue(0);
        board[67].setValue(5);
        board[68].setValue(0);
        board[69].setValue(0);
        board[70].setValue(3);
        board[71].setValue(6);
        board[72].setValue(7);
        board[73].setValue(0);
        board[74].setValue(3);
        board[75].setValue(0);
        board[76].setValue(1);
        board[77].setValue(8);
        board[78].setValue(0);
        board[79].setValue(0);
        board[80].setValue(0);
        lockState();
    }
}
