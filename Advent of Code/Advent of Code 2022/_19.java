import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  public static void main(String[] args) {
    File f = new File("data.txt");
    sol(f);
  }

  static int[] presets;
  static int maxxx = 0;
  public static void sol(File f) {
    try {
      Scanner in = new Scanner(f);

      int i = 0;
      int sum = 1;
      while (in.hasNext() && i++ < 3) {
        String next = in.nextLine().substring(20);
        presets = new int[6];
        presets[0] = Integer.parseInt(next.substring(next.indexOf("costs ") + 6, next.indexOf(" ore")));
        next = next.substring(next.indexOf(" ore") + 4);
        presets[1] = Integer.parseInt(next.substring(next.indexOf("costs ") + 6,next.indexOf(" ore")));
        next = next.substring(next.indexOf(" ore") + 4);
        presets[2] = Integer.parseInt(next.substring(next.indexOf("costs ") + 6,next.indexOf(" ore")));
        presets[3] = Integer.parseInt(next.substring(next.indexOf("and ") + 4,next.indexOf(" clay")));
        next = next.substring(next.indexOf(" clay") + 5);
        presets[4] = Integer.parseInt(next.substring(next.indexOf("costs ") + 6,next.indexOf(" ore")));
        presets[5] = Integer.parseInt(next.substring(next.indexOf("and ") + 4,next.indexOf(" obsidian")));

        //System.out.println(Arrays.toString(presets));
        maxxx = 0;
        sum *= simulateRecur(0, 0, 0, 0, 0, 1, 0, 0, 0, false);
        System.out.println(i + " DONE! (" + sum + ")");
      }
      System.out.println(sum);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int simulateRecur(int sec, int ore, int clay, int obs, int geode, int OR, int CR, int OBR, int GR, boolean wait) {
    if (sec == 32) {
      maxxx = Math.max(maxxx, geode);
      return geode;
    }
    else if (geode + (32 - sec)*(2*GR+33-sec) / 2 < maxxx) return -1;
    int max = 0;

    if (ore >= presets[4] && obs >= presets[5] && (!wait || (ore - OR < presets[4] || obs - OBR < presets[5]))) {
      max = Math.max(simulateRecur(sec+1, ore-presets[4]+OR, clay+CR, obs-presets[5]+OBR, geode+GR, OR, CR, OBR, GR+1, false), max);
    } else {
      if (ore >= presets[0] && (!wait || ore - OR < presets[0])) {
        max = Math.max(simulateRecur(sec+1, ore-presets[0]+OR, clay+CR, obs+OBR, geode+GR, OR+1, CR, OBR, GR, false), max);
      }
      if (ore >= presets[1] && (!wait || ore - OR < presets[1])) {
        max = Math.max(simulateRecur(sec+1, ore-presets[1]+OR, clay+CR, obs+OBR, geode+GR, OR, CR+1, OBR, GR, false), max);
      }
      if (ore >= presets[2] && clay >= presets[3] && (!wait || (ore - OR < presets[2] || clay - CR < presets[3]))) {
        max = Math.max(simulateRecur(sec+1, ore-presets[2]+OR, clay-presets[3]+CR, obs+OBR, geode+GR, OR, CR, OBR+1, GR, false), max);
      }
    }
    
    max = Math.max(simulateRecur(sec+1, ore+OR, clay+CR, obs+OBR, geode+GR, OR, CR, OBR, GR, true), max);
    
    return max;
  }
}