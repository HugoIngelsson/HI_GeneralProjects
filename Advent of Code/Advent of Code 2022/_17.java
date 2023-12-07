import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }
  
  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);

      ArrayList<Rock> rocks = makeRocks();
      boolean[][] map = new boolean[7][70000];
      ArrayList<Boolean[]> map2 = new ArrayList<Boolean[]>();
      Boolean[] floor = {true,true,true,true,true,true,true};
      map2.add(floor);
      map[0][0] = true; map[1][0] = true; map[2][0] = true; map[3][0] = true; map[4][0] = true; map[5][0] = true; map[6][0] = true;
      int maxY = 0;
      int nR = 0;

      String comm = in.nextLine();
      double leftOver = (1000000000000.0 - 2155)%1725.0;
      double tot = (1000000000000.0 - 2155 - leftOver)/1725*2685;
      System.out.println(leftOver + " " + tot);
      int c1 = 0, c2 = 0;
      int i = 0;
      while (nR < 2155 + leftOver) {
        rocks.get(nR%5).setPos(maxY);

        boolean falling = true;
        while (falling) {
          if (comm.charAt(i%10091) == '<') {
            if (rocks.get(nR%5).spaceClearLeft(map)) rocks.get(nR%5).moveLeft();
          }
          else {
            if (rocks.get(nR%5).spaceClearRight(map)) rocks.get(nR%5).moveRight();
          }
          i++;

          if (rocks.get(nR%5).spaceClearDown(map)) {
            rocks.get(nR%5).moveDown();
          }
          else {
            rocks.get(nR%5).setShape(map);
            maxY = Math.max(rocks.get(nR%5).getTop(), maxY);
            falling = false;
          }
        }

        if (c1 == 0 && maxY >= 605) c1 = nR;
        if (c2 == 0 && maxY >= 3290) c2 = nR;
        nR++;
      }

      //CYCLE HEIGHT: 605 --> 3290 (2685)
      //CYCLE ROCK: 390 --> 2115 (1725)
      
      // for (int j=3000; j<7000; j++) {
      //   for (int k=j+5; k<7000; k++) {
      //     if (isCycle(map, j, k)) System.out.println(j + " " + k);
      //   }
      // }
      
      System.out.println(nR + " " + maxY + " " + c1 + " " + c2 + " " + i);
      System.out.printf("%f0.", (tot+maxY));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isCycle(boolean[][] map, int _1, int _2) {
    for (int i=0; i<7; i++) {
      for (int j=_1; j<_2; j++) {
        if (map[i][j] != map[i][j+_2-_1]) return false;
      }
    }

    return true;
  }

  public static ArrayList<Rock> makeRocks() {
    ArrayList<Rock> rocks = new ArrayList<Rock>();
    // ####
    if (true) {
      int[][] d = {{-1,-1},{0,-1},{1,-1},{2,-1}};
      int[][] l = {{-2,0}};
      int[][] r = {{3,0}};
      int[][] s = {{-1,0},{0,0},{1,0},{2,0}};
      rocks.add(new Rock(0, d, l, r, s));
    }
    // .#.
    // ###
    // .#.
    if (true) {
      int[][] d = {{-1,0},{0,-1},{1,0}};
      int[][] l = {{-1,0},{-2,1},{-1,2}};
      int[][] r = {{1,0},{2,1},{1,2}};
      int[][] s = {{0,0},{-1,1},{1,1},{0,1},{0,2}};
      rocks.add(new Rock(2, d, l, r, s));
    }
    // ..#
    // ..#
    // ###
    if (true) {
      int[][] d = {{-1,-1},{0,-1},{1,-1}};
      int[][] l = {{-2,0},{0,1},{0,2}};
      int[][] r = {{2,0},{2,1},{2,2}};
      int[][] s = {{-1,0},{0,0},{1,0},{1,1},{1,2}};
      rocks.add(new Rock(2, d, l, r, s));
    }
    // #
    // #
    // #
    // #
    if (true) {
      int[][] d = {{-1,-1}};
      int[][] l = {{-2,0},{-2,1},{-2,2},{-2,3}};
      int[][] r = {{0,0},{0,1},{0,2},{0,3}};
      int[][] s = {{-1,0},{-1,1},{-1,2},{-1,3}};
      rocks.add(new Rock(3, d, l, r, s));
    }
    // ##
    // ##
    if (true) {
      int[][] d = {{-1,-1},{0,-1}};
      int[][] l = {{-2,0},{-2,1}};
      int[][] r = {{1,0},{1,1}};
      int[][] s = {{-1,0},{0,0},{-1,1},{0,1}};
      rocks.add(new Rock(1, d, l, r, s));
    }

    return rocks;
  }
}

class Rock {
  int x;
  int y;
  int heightDif;
  int[][] down;
  int[][] left;
  int[][] right;
  int[][] stamp;
  
  public Rock(int heightDif, int[][] down, int[][] left, int[][] right, int[][] stamp) {
    this.heightDif = heightDif;
    this.down = down;
    this.left = left;
    this.right = right;
    this.stamp = stamp;
  }

  public void setPos(int y) {
    this.y = y + 4;
    x = 3;
  }

  public int getTop() {
    return y + heightDif;
  }

  public boolean spaceClearDown(boolean[][] map) {
    for (int[] d : down) {
      if (y + d[1] < 0 || map[x + d[0]][y + d[1]]) return false;
    }

    return true;
  }

  public boolean spaceClearLeft(boolean[][] map) {
    for (int[] d : left) {
      if (x + d[0] < 0 || map[x + d[0]][y + d[1]]) return false;
    }

    return true;
  }

  public boolean spaceClearRight(boolean[][] map) {
    for (int[] d : right) {
      if (x + d[0] >= 7 || map[x + d[0]][y + d[1]]) return false;
    }

    return true;
  }

  public boolean[][] setShape(boolean[][] map) {
    for (int[] s : stamp) {
      map[x + s[0]][y + s[1]] = true;
    }

    return map;
  }

  public void setShape2(ArrayList<Boolean[]> map) {
    Boolean[] empty = {false,false,false,false,false,false,false};
    for (int[] s : stamp) {
      if (map.size() <= y + s[1])
        map.add(empty);
      
      System.out.println(map.get(y + s[1])[x + s[0]]);
      map.get(y + s[1])[x + s[0]] = true;
    }
  }

  public boolean spaceClearDown2(ArrayList<Boolean[]> map, int maxY) {
    for (int[] d : down) {
      if (y + d[1] <= maxY && map.get(y + d[1])[x + d[0]]) return false;
    }

    return true;
  }

  public boolean spaceClearLeft2(ArrayList<Boolean[]> map, int maxY) {
    for (int[] d : left) {
      if (x + d[0] < 0 || (y + d[1] <= maxY && map.get(y + d[1])[x + d[0]])) return false;
    }

    return true;
  }

  public boolean spaceClearRight2(ArrayList<Boolean[]> map, int maxY) {
    for (int[] d : right) {
      if (x + d[0] >= 7 || (y + d[1] <= maxY && map.get(y + d[1])[x + d[0]])) return false;
    }

    return true;
  }

  public void moveDown() {
    y--;
  }

  public void moveLeft() {
    x--;
  }

  public void moveRight() {
    x++;
  }
}