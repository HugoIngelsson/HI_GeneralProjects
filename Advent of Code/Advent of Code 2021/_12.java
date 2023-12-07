import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  static int n = 13;
  static boolean[][] connections = new boolean[n][n];
  static boolean[] size;
  static ArrayList<String> map = new ArrayList<String>();
  static boolean[] passed = new boolean[n];

  public static void main(String[] args) {
    File file = new File("data.txt");
    populate(file);
    mappings(file);

    // for (int i = 0; i<13; i++) {
    //   System.out.println(Arrays.toString(connections[i]));
    // }
    // System.out.println(Arrays.toString(size));
    // System.out.println(map.toString());
    passed[0] = true;
    System.out.println(pathfind(0, true));
  }

  public static int pathfind(int id, boolean small) {
    int total = 0;

    for (int i=1; i<n; i++) {
      if (map.get(i).equals("end") && connections[id][i]) total += 1;
      else if (i != id && connections[id][i]) {
        if (size[i] || !passed[i]) {
          passed[i] = true;
          total += pathfind(i, small);
          passed[i] = false;
        }
        else if (small) {
          total += pathfind(i, false);
        }
      }
    }

    return total;
  }

  public static void populate(File f) {
    try {
      Scanner sc = new Scanner(f);
      String s;

      map.add("start");
      while (sc.hasNext()) {
        s = sc.nextLine();

        String p1 = s.substring(0,s.indexOf("-"));
        if (!map.contains(p1)) map.add(p1);
        String p2 = s.substring(s.indexOf("-")+1);
        if (!map.contains(p2)) map.add(p2);
      }

      size = new boolean[map.size()];
    }
    catch (IOException e) {
      e.printStackTrace(); 
    }
  }

  public static void mappings(File f) {
    try {
      Scanner sc = new Scanner(f);
      String s;

      while (sc.hasNext()) {
        s = sc.nextLine();

        String p1 = s.substring(0,s.indexOf("-"));
        String p2 = s.substring(s.indexOf("-")+1);

        connections[map.indexOf(p1)][map.indexOf(p2)] = true;
        connections[map.indexOf(p2)][map.indexOf(p1)] = true;

        if (p1.equals(p1.toUpperCase())) size[map.indexOf(p1)] = true;
        if (p2.equals(p2.toUpperCase())) size[map.indexOf(p2)] = true;
      }
    }
    catch (IOException e) {
      e.printStackTrace(); 
    }
  }
}