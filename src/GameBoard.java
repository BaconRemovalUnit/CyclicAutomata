import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * University of Rochester
* Author: Shengqi Suizhu
        * Date: 2015/12/20
        * GameBoard
        */
public class GameBoard extends JPanel{
    private boolean stopped;
    private int FPS = 60;
    private int x;
    private int y;
    private int R;//range
    private int T;//threshold
    private int C;//states
    private int u;
    private boolean isMOORE = true;
    private CyclicAutomata game;
    private Color[] colorList;
    private Timer timer;

    public GameBoard(int x, int y, int u){
        this.x = x;
        this.y = y;
        this.u = u;
    }

    public void run(){
        game = new CyclicAutomata(x, y, R, T, C, isMOORE);

        //setting a random map
        int[][] map = new int[x][y];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = (int)(Math.random()*C);
            }
        }
        game.setMap(map);

        //give each state a color
        colorList = new Color[C];
        for (int i = 0; i < C; i++) {
            int color = (int)((i*1.0/(1.0*C))*255);
            colorList[i] = new Color(color/C, color, 0);
        }

        //start the simulation
        timer = new Timer(1000/FPS, new BoardListener());
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        drawBlocks(g);
    }

    public void drawBlocks(Graphics g) {
        int[][] map  = game.getMap();
        for(int i = 0; i< map.length; i++)
        {
            for(int j = 0; j<map[0].length; j++)
            {
                    Color currentColor = colorList[map[i][j]];
                     g.setColor(currentColor);
                    g.fillRect(i*u, j*u, u, u);

            }
        }

    }

    private class BoardListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            game.update();
            if(!game.isStopped())
            repaint();
            else {
                System.out.println("stopped");
                timer.stop();
            }
        }
    }

    public int getR() {
        return R;
    }

    public GameBoard setR(int r) {
        R = r;
        return this;
    }

    public int getT() {
        return T;
    }

    public GameBoard setT(int t) {
        T = t;
        return this;
    }

    public int getC() {
        return C;
    }

    public GameBoard setC(int c) {
        C = c;
        return this;
    }

    public int getU() {
        return u;
    }

    public GameBoard setU(int u) {
        this.u = u;
        return this;
    }

    public int getFPS() {
        return FPS;
    }

    public GameBoard setFPS(int FPS) {
        this.FPS = FPS;
        return this;
    }


    public void setWidth(int x) {
        this.x = x;
    }


    public void setHeight(int y) {
        this.y = y;
    }

    public boolean isMOORE() {
        return isMOORE;
    }

    public GameBoard setIsMOORE(boolean isMOORE) {
        this.isMOORE = isMOORE;
        return this;
    }
}
