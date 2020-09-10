import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Board extends JComponent implements MouseListener {
    private int boardSize;
    private static final int OFFSET = 30;
    private static final int CIRCLE_SIZE = 80;
    private static final int GRID_SIZE = 3;
    private Point[][] points;
    private Point[][] drawingGrid;
    private boolean playerTurn;
    private boolean isGameOver;
    private boolean xWinner;
    private boolean tie;

    public Board(int size) {
        this.addMouseListener(this);
        setPreferredSize(new Dimension(size,size));
        this.setFocusable(true);

        boardSize = size;
        points = new Point[GRID_SIZE][GRID_SIZE];
        drawingGrid = new Point[GRID_SIZE][GRID_SIZE];

        playerTurn = true;

        // center points on grid
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
        if (isGameOver) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            g.setColor(Color.PINK);
            if (tie) {
                g.drawString("Game Over -- tie", boardSize / 2 - OFFSET, boardSize / 2);
            } else if (xWinner) {
                g.drawString("Game Over -- X wins!", boardSize / 2 - OFFSET * 2, boardSize / 2);
            } else {
                g.drawString("Game Over -- O wins!", boardSize / 2 - OFFSET * 2, boardSize / 2);
            }
        } else {
            // draw grid lines
            g.setColor(Color.WHITE);
            g.drawLine(boardSize / 3, boardSize / 20, boardSize / 3, boardSize - (boardSize / 20));
            g.drawLine(boardSize - 200, boardSize / 20, boardSize - 200, boardSize - (boardSize / 20));
            g.drawLine(boardSize / 20, boardSize / 3, boardSize - (boardSize / 20), boardSize / 3);
            g.drawLine(boardSize / 20, boardSize - 200, boardSize - (boardSize / 20), boardSize - 200);

            Graphics2D g2 = (Graphics2D) g;

            // draw X/O
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
    }

    private boolean checkEndGame() {
        int count = 0;

        // checks rows and columns for X/O
        for (int i = 0; i < GRID_SIZE; i++) {
            if (drawingGrid[i][0] != null && drawingGrid[i][1] != null && drawingGrid[i][2] != null) {
                if (drawingGrid[i][0].isX() && drawingGrid[i][1].isX() && drawingGrid[i][2].isX()) {
                    xWinner = true;
                    return true;
                } else if (!drawingGrid[i][0].isX() && !drawingGrid[i][1].isX() && !drawingGrid[i][2].isX()) {
                    xWinner = false;
                    return true;
                }
            } else if (drawingGrid[0][i] != null && drawingGrid[1][i] != null && drawingGrid[2][i] != null) {
                if (drawingGrid[0][i].isX() && drawingGrid[1][i].isX() && drawingGrid[2][i].isX()) {
                    xWinner = true;
                    return true;
                } else if (!drawingGrid[0][i].isX() && !drawingGrid[1][i].isX() && !drawingGrid[2][i].isX()) {
                    xWinner = false;
                    return true;
                }
            }
        }

        // checks for diagonal
        int n = 1;

        if (drawingGrid[0][0] != null && drawingGrid[n][n] != null && drawingGrid[n + n][n + n] != null) {
            if (drawingGrid[0][0].isX() && drawingGrid[n][n].isX() && drawingGrid[n + n][n + n].isX()) {
                xWinner = true;
                return true;
            } else if (!drawingGrid[0][0].isX() && !drawingGrid[n][n].isX() && !drawingGrid[n + n][n + n].isX()) {
                xWinner = false;
                return true;
            }
        } else if (drawingGrid[0][n + n] != null && drawingGrid[n][n] != null && drawingGrid[n + n][0]!= null) {
            if (drawingGrid[0][n + n].isX() && drawingGrid[n][n].isX() && drawingGrid[n + n][0].isX()) {
                xWinner = true;
                return true;
            } else if (!drawingGrid[0][n + n].isX() && !drawingGrid[n][n].isX() && !drawingGrid[n + n][0].isX()) {
                xWinner = false;
                return true;
            }
        }

        for (int i = 0; i< GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (drawingGrid[i][j] != null) {
                    count++;
                }
            }
        }

        if (count >= 9) {
            tie = true;
            return true;
        } else {
            return false;
        }
    }

    private Point computerMove() {
        ArrayList<Point> possibleMoves = new ArrayList<Point>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (drawingGrid[i][j] == null) {
                    possibleMoves.add(new Point(i, j, false));
                }
            }
        }

        Random r = new Random();
        if (!possibleMoves.isEmpty()) {
            int n = r.nextInt(possibleMoves.size());
            return possibleMoves.get(n);
        }
        return null;
    }

    private Point bestMove() {
        int x = 0;
        int y = 0;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (drawingGrid[i][j] != null) {
                    if (drawingGrid[i][j].isX()) {
                        if (i + 1 < GRID_SIZE) {
                            x++;
                        } else {
                            x--;
                        }
                    } else {
                        if (j + 1 < GRID_SIZE) {
                            y--;
                        } else {
                            y++;
                        }
                    }
                }
            }
        }
        return new Point(x, y, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (Math.abs(e.getX() - points[i][j].getX()) <= CIRCLE_SIZE && Math.abs(e.getY() - points[i][j].getY()) <= CIRCLE_SIZE) {
                    if (drawingGrid[i][j] == null) {
                        drawingGrid[i][j] = new Point(points[i][j].getX(), points[i][j].getY(), playerTurn);
                        count++;
                    }
                    repaint();
                }
            }
        }
        isGameOver = checkEndGame();

        Point computer = computerMove();
        if (computer != null) {
            int x = computer.getX();
            int y = computer.getY();

            if (drawingGrid[x][y] == null) {
                if (count % 2 == 1) {
                    drawingGrid[x][y] = new Point(points[x][y].getX(), points[x][y].getY(), false);
                    count++;
                }
            }
        }

        isGameOver = checkEndGame();
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