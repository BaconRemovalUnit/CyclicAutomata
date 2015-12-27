import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * University of Rochester
* Author: Shengqi Suizhu
        * Date: 2015/12/20
        * GameBoard
        */
public class GameBoard extends JPanel implements KeyListener{
//    private boolean toggleInactiveCells = true;
    private int FPS;
    private int x;
    private int y;
    private int R;//range
    private int T;//threshold
    private int C;//states
    private int u;
    private boolean isMOORE = true;
    private int timeLimit = 200;//maximum seconds
    private long beginTime;
    //each slot of the rules represents R,T,C,N,timeLimit
    //{3,5,19,1,240},{2,11,3,1,180},{4,21,2,0,60},{2,1,40,0,120},{2,5,3,0,60},{2,4,5,1,30},{2,5,8,1,60},{2,2,19,1,30},{4,6,19,1,50},{3,1,150,1,180},{3,1,100,1,30}
    private int[][] rules = {{3,5,19,1,240},{2,11,3,1,180},{4,21,2,0,60},{2,1,40,0,120},{2,5,3,0,60},{2,4,5,1,30},{2,5,8,1,60},{2,2,19,1,30},{4,6,19,1,50},{3,1,150,1,180},{3,1,100,1,30}};
    private CyclicAutomata game;
    private Color[] colorList;
    private Timer timer;

    public GameBoard(){
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.x = (int)screensize.getWidth()/3;
        this.y = (int)screensize.getHeight()/3;
        this.u = 3;
        this.FPS = 15;
        int currentRule = (int)(Math.random()*rules.length);
        this.R = rules[currentRule][0];
        this.T = rules[currentRule][1];
        this.C = rules[currentRule][2];
        this.isMOORE = rules[currentRule][3]==1;
        this.timeLimit = rules[currentRule][4];
    }

    public GameBoard(int x, int y, int u){
        this.x = x;
        this.y = y;
        this.u = u;
        this.R = rules[0][0];
        this.T = rules[0][1];
        this.C = rules[0][2];
        this.isMOORE = rules[0][3]==1;
        this.timeLimit = rules[0][4];

    }

    public void run(){
        game = new CyclicAutomata(x, y, R, T, C, isMOORE);

        //give each state a color
        colorList = new Color[C];
        for (int i = 0; i < C; i++) {
            int R = (int)(Math.random()*255);
            int G = (int)(Math.random()*255);
            int B = (int)(Math.random()*255);
            colorList[i] = new Color(R,G,B);
        }

        //start the simulation
        timer = new Timer(1000/FPS, new BoardListener());
        beginTime = System.currentTimeMillis();
        timer.start();

    }

    public void paintComponent(Graphics g) {
        boolean[][] update = game.getRefresh();
        int[][] map  = game.getMap();
        for(int i = 0; i< map.length; i++) {
            for(int j = 0; j<map[0].length; j++) {
                if(update[i][j]) {
                    Color currentColor = colorList[map[i][j]];
                    g.setColor(currentColor);
                    g.fillRect(i * u, j * u, u, u);
                }
//                else if(!toggleInactiveCells){
//                    g.setColor(Color.black);
//                    g.fillRect(i * u, j * u, u, u);
//                }
//                else {
//                    Color currentColor = colorList[map[i][j]];
//                    g.setColor(currentColor);
//                    g.fillRect(i * u, j * u, u, u);
//                }
            }
        }
    }

    private class BoardListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            game.update();

            int timeElapsed = (int)((System.currentTimeMillis() - beginTime)/1000);
            if(!game.isStopped()&&timeElapsed<timeLimit) {
                repaint();
            }
            else {
                timer.stop();
                resetRules();
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException err){
                    err.printStackTrace();
                }
                run();
            }
        }
    }

    private void resetRules(){
        int ruleNumber = (int)(Math.random()*rules.length);
        int[] rule = rules[ruleNumber];
        //each slot of the rules represents R,T,C,N,timeLimit
        this.R = rule[0];
        this.T = rule[1];
        this.C = rule[2];
        this.isMOORE = rule[3]==1;
        this.timeLimit = rule[4];
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                timer.stop();
                resetRules();
                run();
                break;
//            case KeyEvent.VK_CAPS_LOCK:
//                toggleInactiveCells = !toggleInactiveCells;
//                break;
            default:
                try{
                    Runtime.getRuntime().exec("RunDll32.exe user32.dll,LockWorkStation");
                }
                catch (IOException err){
                    err.printStackTrace();
                }
                System.exit(0);

        }
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
