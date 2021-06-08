package model;

import ui.GamePanel;

public class SudokuSolver {

    public SudokuGame game;
    public GamePanel gp;

    public SudokuSolver(SudokuGame game) {
        this.game = game;
        gp = game.gp;
    }

    public boolean visualSolve(int i) {
        if(game.complete()) {
            return true;
        } else {
            if(game.board[i].locked) {
                return visualSolve(i +1);
            } else {
                for(int n = 1; n < 10; n++) {
                    game.board[i].setValue(n);
                    gp.paintComponent(gp.getGraphics());
                    if(game.isBoardViable()) {
                        if(visualSolve(i + 1)) {
                            return visualSolve(i + 1);
                        }
                    }
                    game.board[i].setValue(0);
                }
                return false;
            }
        }
    }

    public boolean solve(int i) {
        if(game.complete()) {
            return true;
        } else {
            if(game.board[i].locked) {
                return solve(i +1);
            } else {
                for(int n = 1; n < 10; n++) {
                    game.board[i].setValue(n);
                    if(game.isBoardViable()) {
                        if(solve(i + 1)) {
                            return solve(i + 1);
                        }
                    }
                    game.board[i].setValue(0);
                }
                return false;
            }
        }
    }
}
