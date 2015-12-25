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
    private int R = 2;//range
    private int T = 5;//threshold
    private int C = 8;//states
    private int u;
    private boolean isMOORE = true;
    private CyclicAutomata game;
    private Color[] colorList;
    private Timer timer;
    public GameBoard(int x, int y, int u){
        if(isMOORE){
            game = new CyclicAutomata(x, y, R, T, C, CyclicAutomata.MOORE);
        }
        else{
            game = new CyclicAutomata(x, y, R, T, C, CyclicAutomata.NEUMAN);
        }
        int[][] map = new int[x][y];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = (int)(Math.random()*C);
//              map[i][j] = 0;
            }
        }
//        map[x/2][y/2] = 1;
//        map[x/2][y/2+1] = 2;
        game.setMap(map);


        colorList = new Color[C];
        for (int i = 0; i < C; i++) {
            int color = (int)((i*1.0/(1.0*C))*255);
            colorList[i] = new Color(color/C, color, 0);
        }

        this.x = x;
        this.y = y;
        this.u = u;
        timer = new Timer(1000/FPS, new BoardListener());
        timer.start();
    }

    public void paint(Graphics g)
    {

        super.paint(g);
        //draw the grid
        g.setColor(Color.white);
        g.drawRect(0, 0, x*u, y*u);
        for(int i = 0; i< y; i++)
        {
            g.drawLine(0, i*u, x*u, i*u);
        }
        for(int i = 0; i< x; i++)
        {
            g.drawLine(i*u,i*u,x*u,y*u);
        }

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
}
