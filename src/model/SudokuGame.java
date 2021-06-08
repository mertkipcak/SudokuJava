package model;

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

    // represents the selected square
    public int selected = 0;

    public SudokuGame() {
        board = new Box[81];
        for(int i = 0; i < 81; i++) {
            board[i] = new Box();
        }
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
                new SudokuSolver(this);
                break;
            default:
                break;
        }
        isBoardViabile();
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

    public boolean isBoardViabile() {
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
        return isBoardViabile() && boardFilled();
    }

    private boolean boardFilled() {
        for(Box box: board) {
            if(box.value == 0) return false;
        }
        return true;
    }

}
