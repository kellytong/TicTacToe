import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final int BOARD_SIZE = 600;
    private static final Color BOARD_COLOR = Color.BLACK;
    private static final int NUM_BOXES = 9;

    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame
        f.getContentPane().setBackground(BOARD_COLOR);

        f.setSize(BOARD_SIZE, BOARD_SIZE);
        f.setResizable(false);

        Board board = new Board(BOARD_SIZE);
        f.add(board);

        f.setVisible(true);//making the frame visible
    }
}