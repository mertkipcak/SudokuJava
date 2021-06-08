package ui;

import model.SudokuGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public class Sudoku extends JFrame implements Observer {

    private final GamePanel gp;

    // constructs the frame that the game will be played on
    public Sudoku() {
        super("MineSweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        gp = new GamePanel(new SudokuGame());
        add(gp);
        addMouseListener(new MouseHandler());
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
    }

    /**
     * initializes the game
     */
    public static void main(String[] args) {
        new Sudoku();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    /**
     * mouse handler class:
     * every mouse action calls handleCLick function on the tile clicked
     */
    private class MouseHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int boardValue = e.getX() / gp.BOX_SIDE + e.getY() / gp.BOX_SIDE * 9;
            gp.game.handleClick(boardValue);
            gp.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int boardValue = e.getX() / gp.BOX_SIDE + e.getY() / gp.BOX_SIDE * 9;
            gp.game.handleClick(boardValue);
            gp.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int boardValue = e.getX() / gp.BOX_SIDE + e.getY() / gp.BOX_SIDE * 9;
            gp.game.handleClick(boardValue);
            gp.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            gp.game.handlePress(e.getKeyCode());
            gp.repaint();
        }
    }

}
