import javax.swing.*;
import java.awt.*;

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

        //r3t5c20
        JFrame frame = new JFrame("Cyclic Automata");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameBoard board = new GameBoard();
        board.run();

        frame.getContentPane().add(board);
//        frame.setSize(x*u+x_adj,y*u+y_adj);
//        frame.setLocationRelativeTo(null);
//        frame.setLayout(new FlowLayout());
        frame.addKeyListener(board);
        frame.setUndecorated(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(d.width, d.height);
        frame.setVisible(true);
    }
}
