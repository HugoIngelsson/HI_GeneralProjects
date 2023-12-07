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
      ArrayList<String> ranges = new ArrayList<String>();
      int abc = 33;
      int[][] dists = new int[3][abc];
      Scanner in = new Scanner(f);

      int i = 0;
      while (in.hasNext()) {
        int s1, s2, b1, b2;
        String next = in.nextLine();
        // int y = 10;

        s1 = Integer.parseInt(next.substring(next.indexOf("x=")+2, next.indexOf(",")));
        s2 = Integer.parseInt(next.substring(next.indexOf("y=")+2, next.indexOf(":")));
        next = next.substring(next.indexOf(":"));
        b1 = Integer.parseInt(next.substring(next.indexOf("x=")+2, next.indexOf(",")));
        b2 = Integer.parseInt(next.substring(next.indexOf("y=")+2));

        int manhattan = Math.abs(s1 - b1) + Math.abs(s2 - b2);
        dists[0][i] = manhattan+1;
        dists[1][i] = s1;
        dists[2][i++] = s2;
        
        // if (s2 > y && s2 - manhattan < y) {
        //   ranges.add((s1 - manhattan + s2 - y) + "," + (s1 + manhattan - s2 + y));
        // }
        // else if (s2 < y && s2 + manhattan > y) {
        //   ranges.add((s1 - manhattan - s2 + y) + "," + (s1 + manhattan + s2 - y));
        // }
      }

      for (int h=0; h<abc; h++) {
        for (int j=-dists[0][h]; j<dists[0][h]; j++) {
          int x = dists[1][h] + j;
          int y1 = dists[2][h] + dists[0][h] - Math.abs(j);
          int y2 = dists[2][h] - dists[0][h] + Math.abs(j);
          if (x >= 0 && x <= 4000000 && y1 >= 0 && y1 <= 4000000) {
            for (int k=0; k<abc; k++) {
              if (Math.abs(x - dists[1][k]) + Math.abs(y1 - dists[2][k]) <= dists[0][k]-1) break;
                
              else if (k == abc - 1) System.out.printf("value: %.0f \n", x*4000000.0 + y2);
            }
          }
          if (x >= 0 && x <= 4000000 && y2 >= 0 && y2 <= 4000000) {
            for (int k=0; k<abc; k++) {
              if (Math.abs(x - dists[1][k]) + Math.abs(y2 - dists[2][k]) <= dists[0][k]-1) break;
              else if (k == abc - 1) System.out.printf("value: %.0f \n", x*4000000.0 + y2);
            }
          }
        }
      }

      // System.out.println(ranges);
      
      // for (int i=0; i<ranges.size(); i++) {
      //   for (int j=i+1; j<ranges.size(); j++) {
      //     String replace = mergeRanges(ranges.get(i), ranges.get(j));
      //     if (replace.length() != 0) {
      //       ranges.set(i, replace);
      //       ranges.remove(j--);
      //     }
      //   }
      // }

      // for (int i=0; i<ranges.size(); i++) {
      //   for (int j=i+1; j<ranges.size(); j++) {
      //     String replace = mergeRanges(ranges.get(i), ranges.get(j));
      //     if (replace.length() != 0) {
      //       ranges.set(i, replace);
      //       ranges.remove(j--);
      //     }
      //   }
      // }
      // System.out.println(ranges);
      // String a = ranges.get(0);
      // System.out.println(-Integer.parseInt(a.substring(0, a.indexOf(","))) 
      //                    + Integer.parseInt(a.substring(a.indexOf(",")+1)));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String mergeRanges(String s1, String s2) {
    int n1, n2, n3, n4;
    n1 = Integer.parseInt(s1.substring(0, s1.indexOf(",")));
    n2 = Integer.parseInt(s1.substring(s1.indexOf(",")+1));
    n3 = Integer.parseInt(s2.substring(0, s2.indexOf(",")));
    n4 = Integer.parseInt(s2.substring(s2.indexOf(",")+1));

    if (n2 >= n3 && n1 <= n4) return Math.min(n1,n3) + "," + Math.max(n2,n4);
    // else if (n1 < n4 && n3 <= n2) return n1 + "," + n4;

    return "";
  }
}