import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  static boolean[][] onMap = new boolean[200][150];
  static boolean[][] walls = new boolean[200][150];
  static char[][] map = new char[200][150];
  static int dir = 1;
  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);

      int j=0;
      while (j < 200) {
        String next = in.nextLine();

        for (int i=0; i<next.length(); i++) {
          map[j][i] = ' ';
          if (next.charAt(i) != ' ') onMap[j][i] = true;
          if (next.charAt(i) != ' ') map[j][i] = '.';
          if (next.charAt(i) == '#') walls[j][i] = true;
          if (next.charAt(i) == '#') map[j][i] = '#';
        }

        j++;
      }

      in.nextLine();
      String next = in.nextLine();
      int id, x = 50, y = 0;
      while ((id = nextTurn(next)) != -1) {
        int move = Integer.parseInt(next.substring(0,id));
        char comm = next.charAt(id);
        next = next.substring(id+1);

        while (move --> 0) {
          int[] xy = move(x, y, dir);
          x = xy[0];
          y = xy[1];
        }

        if (comm == 'R') dir = (dir+1)%4;
        else dir = (dir+3)%4;
      }
      int move = Integer.parseInt(next);
      while (move --> 0) {
        int[] xy = move(x, y, dir);
        x = xy[0];
        y = xy[1];
      }

      for (char[] c : map) {
        for (char e : c) System.out.print(e);
        System.out.println();
      }
      System.out.println(x + " " + y);
      System.out.println(1000*(y+1)+4*(x+1)+(dir+3)%4);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int[] move(int x, int y, int dir) {
    if (dir == 0) {
      map[y][x] = '^';
      return upY(x, y);
    } else if (dir == 1) {
      map[y][x] = '>';
      return rightX(x, y);
    } else if (dir == 2) {
      map[y][x] = 'v';
      return downY(x, y);
    } else if (dir == 3) {
      map[y][x] = '<';
      return leftX(x, y);
    }
    return null;
  }

  public static int[] upY(int x, int y) {
    int[] xy = {x,y};
    if (y > 0 && onMap[y-1][x]) {
      if (!walls[y-1][x]) xy[1] = y-1;
    }
    else if (x < 50) {
      if (!walls[50+x][50]) {
        xy[0] = 50;
        xy[1] = 50+x;
        dir = 1;
      }
    }
    else if (x < 100) {
      if (!walls[100+x][0]) {
        xy[0] = 0;
        xy[1] = 100+x;
        dir = 1;
      }
    }
    else if (!walls[199][x-100]) {
      xy[0] = x-100;
      xy[1] = 199;
      dir = 0;
    }
    return xy;
  }

  public static int[] downY(int x, int y) {
    int[] xy = {x,y};
    
    if (y < 199 && onMap[y+1][x]) {
      if (!walls[y+1][x]) xy[1] = y+1;
    }
    else if (x < 50) {
      if (!walls[0][x+100]) {
        xy[0] = x+100;
        xy[1] = 0;
        dir = 2;
      }
    }
    else if (x < 100) {
      if (!walls[x+100][49]) {
        xy[0] = 49;
        xy[1] = x+100;
        dir = 3;
      }
    }
    else if (!walls[x-50][99]) {
      xy[0] = 99;
      xy[1] = x-50;
      dir = 3;
    }
    return xy;
  }

  public static int[] leftX(int x, int y) {
    int[] xy = {x,y};
    
    if (x > 0 && onMap[y][x-1]) {
      if (!walls[y][x-1]) xy[0] = x-1;
    }
    else if (y < 50) {
      if (!walls[149-y][0]) {
        xy[0] = 0;
        xy[1] = 149-y;
        dir = 1;
      }
    }
    else if (y < 100) {
      if (!walls[100][y-50]) {
        xy[0] = y-50;
        xy[1] = 100;
        dir = 2;
      }
    }
    else if (y < 150) {
      if (!walls[149-y][50]) {
        xy[0] = 50;
        xy[1] = 149-y;
        dir = 1;
      }
    }
    else if (!walls[0][y-100]) {
      xy[0] = y-100;
      xy[1] = 0;
      dir = 2;
    }
    return xy;
  }

  public static int[] rightX(int x, int y) {
    int[] xy = {x,y};
    
    if (x < 149 && onMap[y][x+1]) {
      if (!walls[y][x+1]) xy[0] = x+1;
    }
    else if (y < 50) {
      if (!walls[149-y][99]) {
        xy[0] = 99;
        xy[1] = 149-y;
        dir = 3;
      }
    }
    else if (y < 100) {
      if (!walls[49][50+y]) {
        xy[0] = 50+y;
        xy[1] = 49;
        dir = 0;
      }
    }
    else if (y < 150) {
      if (!walls[149-y][149]) {
        xy[0] = 149;
        xy[1] = 149-y;
        dir = 3;
      }
    }
    else if (!walls[149][y-100]) {
      xy[0] = y-100;
      xy[1] = 149;
      dir = 0;
    }
    return xy;
  }
  
  public static int nextTurn(String id) {
    if (id.indexOf("L") < 0) return id.indexOf("R");
    if (id.indexOf("R") < 0) return id.indexOf("L");
    return Math.min(id.indexOf("L"), id.indexOf("R"));
  }
}