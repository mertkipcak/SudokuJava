package ui;

import model.SudokuGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static final int BOX_SIDE = 30;

    public static final Color BACKGROUND = new Color(255, 244, 147);

    public SudokuGame game;
    public int panelSide;

    // Constructs a game panel
    // EFFECTS: sets size and background colour of panel
    //          updates this with the game to be displayed
    //          also imports images
    public GamePanel(SudokuGame game) {
        panelSide = BOX_SIDE * 9;
        setPreferredSize(new Dimension(panelSide, panelSide));
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        drawBackground(g);
        drawGrid(g);
    }

    private void drawBackground(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, panelSide, panelSide);
        g.setColor(savedCol);
    }

    private void drawGrid(Graphics g) {
        Color savedCol = g.getColor();
        for(int x = 0; x < panelSide; x++) {
            for(int y = 0; y < panelSide; y++) {
                g.setColor(Color.gray);
                g.drawRect(x* BOX_SIDE, y* BOX_SIDE, BOX_SIDE -1, BOX_SIDE -1 );
            }
        }
        g.setColor(Color.black);
        g.drawLine(panelSide/3, 0, panelSide/3 ,panelSide);
        g.drawLine(2*panelSide/3, 0, 2*panelSide/3 ,panelSide);
        g.drawLine(0, panelSide/3, panelSide, panelSide/3);
        g.drawLine(0, 2*panelSide/3, panelSide, 2*panelSide/3);
        g.setColor(savedCol);
    }

}
