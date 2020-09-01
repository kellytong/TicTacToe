import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JComponent implements MouseListener {
    private int boardSize;
    private static final int OFFSET = 30;
    private static final int CIRCLE_SIZE = 80;
    private static final int GRID_SIZE = 3;
    private Point[][] points;
    private Point[][] drawingGrid;
    private boolean playerTurn;

    public Board(int size) {
        this.addMouseListener(this);
        setPreferredSize(new Dimension(size,size));
        this.setFocusable(true);

        boardSize = size;
        points = new Point[GRID_SIZE][GRID_SIZE];
        drawingGrid = new Point[GRID_SIZE][GRID_SIZE];

        playerTurn = true;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (points[i][j] == null) {
                    points[i][j] = new Point(70 + (boardSize / 3) * j, 70 + (boardSize / 3) * i, false);
                }
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
            // lines
            g.setColor(Color.WHITE);
            g.drawLine(boardSize / 3, boardSize / 20, boardSize / 3, boardSize - (boardSize / 20));
            g.drawLine(boardSize - 200, boardSize / 20, boardSize - 200, boardSize - (boardSize / 20));
            g.drawLine(boardSize / 20, boardSize / 3, boardSize - (boardSize / 20), boardSize / 3);
            g.drawLine(boardSize / 20, boardSize - 200, boardSize - (boardSize / 20), boardSize - 200);

            Graphics2D g2 = (Graphics2D) g;

            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (drawingGrid[i][j] != null) {
                        if (!drawingGrid[i][j].isX()) {
                            g.setColor(Color.GRAY);
                            g2.setStroke(new BasicStroke(5));
                            g2.drawOval(points[i][j].getX(), points[i][j].getY(), CIRCLE_SIZE, CIRCLE_SIZE);
                        } else {
                            g.setColor(Color.BLUE);
                            g.setFont(new Font("Arial", Font.PLAIN, 100));
                            g.drawString("X", points[i][j].getX(), points[i][j].getY() + OFFSET * 2);
                        }
                    }
                }
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (Math.abs(e.getX() - points[i][j].getX()) <= CIRCLE_SIZE
                    && Math.abs(e.getY() - points[i][j].getY()) <= CIRCLE_SIZE) {
                    drawingGrid[i][j] = new Point(points[i][j].getX(), points[i][j].getY(), playerTurn);
                    playerTurn = !playerTurn;
                }
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}