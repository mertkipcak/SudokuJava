package model;

import ui.GamePanel;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class SudokuSolver extends Observable {

    public SudokuGame game;
    public GamePanel gp;

    public SudokuSolver(SudokuGame game) {
        this.game = game;
        boolean solved = solve(0);
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
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

                    try {
                        TimeUnit.MICROSECONDS.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(game.isBoardViabile()) {
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
