import javax.swing.*;

/**
 * University of Rochester
 * Autho: Shengqi Suizhu
 * Date: 2015/12/20
 * Main
 */
public class Main {

    public static void main(String[] args) {
        int x = 500;//1000
        int x_adj = 40;//40
        int y = 500;//600
        int y_adj = 10;//100
        int u = 2;
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GameBoard(x,y,u));
        frame.setSize(x*u+x_adj,y*u+y_adj);
        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
        frame.setVisible(true);
    }
}
