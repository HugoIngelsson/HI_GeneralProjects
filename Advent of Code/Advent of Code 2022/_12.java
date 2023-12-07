import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static int[][] map = new int[173][41];
  static int Ex, Ey;
  static boolean[][] visited = new boolean[173][41];
  static int[][] dist = new int[173][41];
  static int nodeX, nodeY;
  
  public static void main(String[] args) {
    File f = new File("data.txt");
    readData(f);
    int min = Integer.MAX_VALUE;

    for (int i=0; i<173; i++) for (int j=0; j<41; j++) if (map[i][j] == 0) {
      visited = new boolean[173][41];
      dist = new int[173][41];
      nodeX = i;
      nodeY = j;
      for (int h=0; h<173; h++) for (int k=0; k<41; k++) if (!(h == nodeX && k == nodeY)) dist[h][k] = -1;
      
      while (dist[Ex][Ey] == -1) {
        dijkstra();
        findMin();
      }
      min = Math.min(dist[Ex][Ey], min);
      System.out.println(min);
    }
    

    System.out.println(min);
  }

  public static void dijkstra() {
    if (nodeX > 0 && !visited[nodeX-1][nodeY] && map[nodeX-1][nodeY] <= map[nodeX][nodeY] + 1) {
      if (dist[nodeX-1][nodeY] == -1) {
        dist[nodeX-1][nodeY] = dist[nodeX][nodeY] + 1;
      }
      else {
        dist[nodeX-1][nodeY] = Math.min(dist[nodeX][nodeY] + 1, dist[nodeX-1][nodeY]);
      }
    }
    if (nodeX < 172 && !visited[nodeX+1][nodeY] && map[nodeX+1][nodeY] <= map[nodeX][nodeY] + 1) {
      if (dist[nodeX+1][nodeY] == -1) {
        dist[nodeX+1][nodeY] = dist[nodeX][nodeY] + 1;
      }
      else {
        dist[nodeX+1][nodeY] = Math.min(dist[nodeX][nodeY] + 1, dist[nodeX+1][nodeY]);
      }
    }
    if (nodeY > 0 && !visited[nodeX][nodeY-1] && map[nodeX][nodeY-1] <= map[nodeX][nodeY] + 1) {
      if (dist[nodeX][nodeY-1] == -1) {
        dist[nodeX][nodeY-1] = dist[nodeX][nodeY] + 1;
      }
      else {
        dist[nodeX][nodeY-1] = Math.min(dist[nodeX][nodeY] + 1, dist[nodeX][nodeY-1]);
      }
    }
    if (nodeY < 40 && !visited[nodeX][nodeY+1] && map[nodeX][nodeY+1] <= map[nodeX][nodeY] + 1) {
      if (dist[nodeX][nodeY+1] == -1) {
        dist[nodeX][nodeY+1] = dist[nodeX][nodeY] + 1;
      }
      else {
        dist[nodeX][nodeY+1] = Math.min(dist[nodeX][nodeY] + 1, dist[nodeX][nodeY+1]);
      }
    }

    visited[nodeX][nodeY] = true;
  }

  public static void findMin() {
    int min = Integer.MAX_VALUE;
    int minX = 0;
    int minY = 0;
    
    for (int i=0; i<173; i++) {
      for (int j=0; j<41; j++) {
        if (dist[i][j] >= 0 && dist[i][j] < min && !visited[i][j]) {
          min = dist[i][j];
          minX = i;
          minY = j;
        }
      }
    }

    nodeX = minX;
    nodeY = minY;
  }

  public static void readData(File f) {
    try {
      Scanner in = new Scanner(f);

      int j = 0;
      while (in.hasNext()) {
        String s = in.nextLine();
    
        for (int i=0; i<s.length(); i++) {
          if (s.charAt(i) == 'S') {
            nodeX = i;
            nodeY = j;
            map[i][j] = 0;
          }
          else if (s.charAt(i) == 'E') {
            Ex = i;
            Ey = j;
            map[i][j] = 25;
          }
          else {
            map[i][j] = (int)s.charAt(i) - 97;
          }
        }
        j++;
      }

      for (int i=0; i<173; i++) for (int k=0; k<41; k++) if (!(i == nodeX && k == nodeY)) dist[i][k] = -1;
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}