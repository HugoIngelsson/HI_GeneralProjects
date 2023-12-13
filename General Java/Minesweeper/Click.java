package Minesweeper;

class Click {
  int x;
  int y;
  boolean left;

  public Click(int x, int y, boolean left) {
    this.x = x;
    this.y = y;
    this.left = left;
  }

  public String toString() {
    return x + " " + y + ": " + left;
  }
}