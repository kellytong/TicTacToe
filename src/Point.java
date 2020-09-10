public class Point {
    private int x;
    private int y;
    private boolean isX;

    public Point(int x, int y, boolean b) {
        this.x = x;
        this.y = y;
        this.isX = b;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isX() { return isX; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
