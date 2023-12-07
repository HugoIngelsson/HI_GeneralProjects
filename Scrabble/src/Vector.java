public class Vector {
    int x;
    int y;
    boolean down;

    public Vector(int x, int y, boolean down) {
        this.x = x;
        this.y = y;
        this.down = down;
    }

    public String toString() {
        return "("+(x+1)+","+(y+1)+") " + (down ? "down" : "across");
    }
}
