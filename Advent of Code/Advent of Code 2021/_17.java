class Main {
  static int[] xR = {137, 171};
  static int[] yR = {-73, -98};

  public static void main(String[] args) {
    System.out.println(getMaxVy());

    int total = 0;

    for (int Vx = 0; Vx < 200; Vx++) {
      for (int Vy = -100; Vy < 100; Vy++) {
        if (makesIt(Vx, Vy)) total++;
      }
    }

    System.out.println(total);
  }

  public static boolean makesIt(int Vx, int Vy) {
    int x = 0;
    int y = 0;
    
    while (x < xR[0] || y > yR[0]) {
      x += Vx;
      y += Vy;
      if (x > xR[1] || y < yR[1]) return false;

      if (Vx > 0) Vx--;
      Vy--;
    }

    return true;
  }

  public static int getMaxVy() {
    int y;
    int maxVy = 0;

    for (int i = 0; i<1000; i++) {
      y = 0;
      int v = i;

      while (y >= yR[1]) {
        y += v;
        v -= 1;
      }

      if (y-v-1 <= yR[0] && y-v-1 >= yR[1]) maxVy = Math.max(maxVy, i);
    }

    return maxVy;
  }
}