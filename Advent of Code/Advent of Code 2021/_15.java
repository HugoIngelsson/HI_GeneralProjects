import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static int[][] risk = new int[100][100];
  static int[][] bigRisk = new int[500][500];
  static int[][] dist = new int[500][500];
  static boolean[][] visited = new boolean[500][500];
  static int x = 0;
  static int y = 0;

  public static void main(String[] args) {
    File f = new File("data.txt");
    populate(f);
    dijkstra();
  }

  public static void dijkstra() {
    while (!visited[499][499]) {
      if (x > 0 && !visited[x-1][y]) {
        if (dist[x-1][y] > dist[x][y] + bigRisk[x-1][y]) dist[x-1][y] = dist[x][y] + bigRisk[x-1][y];
      }
      if (x < 499 && !visited[x+1][y]) {
        if (dist[x+1][y] > dist[x][y] + bigRisk[x+1][y]) dist[x+1][y] = dist[x][y] + bigRisk[x+1][y];
      }
      if (y > 0 && !visited[x][y-1]) {
        if (dist[x][y-1] > dist[x][y] + bigRisk[x][y-1]) dist[x][y-1] = dist[x][y] + bigRisk[x][y-1];
      }
      if (y < 499 && !visited[x][y+1]) {
        if (dist[x][y+1] > dist[x][y] + bigRisk[x][y+1]) dist[x][y+1] = dist[x][y] + bigRisk[x][y+1];
      }

      visited[x][y] = true;
      findNext();
      System.out.println(x + " " + y);
    }

    System.out.println(dist[499][499]);
  }

  public static void findNext() {
    int min = Integer.MAX_VALUE;

    for (int i=0; i<500; i++) {
      for (int j=0; j<500; j++) {
        if (!visited[i][j] && min > dist[i][j]) {
          min = dist[i][j];
          x = i;
          y = j;
        }
      }
    }
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);

      for (int i=0; i<100; i++) {
        String s = sc.nextLine();
        for (int j=0; j<100; j++) {
          risk[i][j] = Integer.parseInt("" + s.charAt(j));

          for (int k=0; k<5; k++) {
            for (int n=0; n<5; n++) {
              bigRisk[100*k+i][100*n+j] = (risk[i][j]+k+n-1)%9+1;
            }
          }
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    for (int i=0; i<500; i++)
      for (int j=0; j<500; j++)
        dist[i][j] = Integer.MAX_VALUE;

    dist[0][0] = 0;
  }
}