import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  //Position formatting: x*40000^2 + y*40000 + z;
  static ArrayList<Double> beacons = new ArrayList<Double>();
  static ArrayList<Double> scanners = new ArrayList<Double>();
  static ArrayList<Double> test = new ArrayList<Double>();
  static ArrayList<Double> keep = new ArrayList<Double>();
  static boolean[] done = new boolean[38];
  static int[] sizes = new int[38];

  public static void main(String[] args) {
    done[0] = true;
    sizes[0] = 26;
    File f = new File("data.txt");
    initialize(f);
    System.out.println(Arrays.toString(sizes));

    // for (Double d : beacons) {
    //   System.out.printf("%.0f", d);
    //   System.out.println();
    // }

    int id = 1;
    ArrayList<Double> relatives = new ArrayList<Double>();
    while (notDone()) {
      if (!done[id]) {
        relatives.clear();
        int size = sumPrev(id);
        for (int i=size; i<size+sizes[id]; i++) {
          //System.out.println(test.get(i) + " " + i);
          relatives.add(test.get(i));
        }

        for (int j=0; j<10; j++) {
          done[id] = findScanner(relatives, j);
          if (done[id]) j=20;
        }
      }

      id = (id%37)+1;
      if (!done[id]) {
        System.out.println(id + " " + beacons.size() + " " + scanners.size());
      }
    }

    System.out.println(beacons.size());

    int dist = -1;
    int _t = 0;
    for (Double d : scanners) {
      for (Double e : scanners) {
        System.out.printf("%.0f", d);
        System.out.print(" ");
        System.out.printf("%.0f", e);
        System.out.println();
        _t = manhattanDist(d, e);
        System.out.println(_t);
        if (_t > dist) dist = _t;
      }
    }

    System.out.println(dist);
  }

  public static int manhattanDist(double pos1, double pos2) {
    int dist = 0;

    dist += Math.abs((int)(pos1%40000.0) - (int)(pos2%40000.0));
    dist += Math.abs((int)((pos1/40000.0)%40000.0) - (int)((pos2/40000.0)%40000.0));
    dist += Math.abs((int)(pos1/1600000000.0) - (int)(pos2/1600000000.0));

    return dist;
  }

  public static int sumPrev(int n) {
    int tot = 0;
    for (int i=0; i<n; i++) {
      tot += sizes[i];
    }

    return tot;
  } 

  public static boolean notDone() {
    for (boolean b : done) {
      if (!b) return true;
    }

    return false;
  }

  public static boolean findScanner(ArrayList<Double> relatives, int id) {
    double pos = relatives.get(id);
    double temp;
    int[] op = {-1,1};

    for (int x : op) {
      for (int y : op) {
        for (int z : op) {
          temp = getOffset(pos, relatives, 1600000000.0*x, 40000.0*y, 1.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
          temp = getOffset(pos, relatives, 1600000000.0*x, 1.0*y, 40000.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
          temp = getOffset(pos, relatives, 40000.0*x, 1600000000.0*y, 1.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
          temp = getOffset(pos, relatives, 1.0*x, 1600000000.0*y, 40000.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
          temp = getOffset(pos, relatives, 40000.0*x, 1.0*y, 1600000000.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
          temp = getOffset(pos, relatives, 1.0*x, 40000.0*y, 1600000000.0*z);
          if (temp != -1) {
            for (Double d : keep) {
              beacons.add(d);
            }
            return true;
          }
        }
      }
    }

    return false;
  }

  public static double getOffset(double pos, ArrayList<Double> relatives, double a, double b, double c) {
    keep.clear();
    int x = (int)(pos / Math.abs(a) % 40000.0);
    x = (x-20000)*(int)Math.signum(a)+20000;
    int y = (int)(pos / Math.abs(b) % 40000.0);
    y = (y-20000)*(int)Math.signum(b)+20000;
    int z = (int)(pos / Math.abs(c) % 40000.0);
    z = (z-20000)*(int)Math.signum(c)+20000;
    int count = 0;

    //Collections.sort(beacons);
    for (Double d : beacons) {
      int dz = (int)(d%40000.0) - z;
      int dy = (int)((d/40000)%40000.0) - y;
      int dx = (int)(d/1600000000) - x;

      for (Double r : relatives) {
        int rx = (int) (r / Math.abs(a) % 40000.0);
        rx = (rx-20000)*(int)Math.signum(a)+20000+dx;
        int ry = (int) (r / Math.abs(b) % 40000.0);
        ry = (ry-20000)*(int)Math.signum(b)+20000+dy;
        int rz = (int) (r / Math.abs(c) % 40000.0);
        rz = (rz-20000)*(int)Math.signum(c)+20000+dz;
        pos = rx*1600000000.0 + ry*40000.0 + rz;

        if (beacons.contains(pos)) count++;
        else keep.add(pos);
      }

      if (count >= 10) {
        scanners.add((dx+20000)*1600000000.0+(dy+20000)*40000.0+(dz+20000)*1.0);
        System.out.println(dx + " " + dy + " " + dz);
        return (dx+20000)*1600000000.0+(dy+20000)*40000.0+(dz+20000)*1.0;
      }
      keep.clear();

      // System.out.println(count);
      count = 0;
    }

    return -1.0;
  }

  public static void initialize(File f) {
    try {
      Scanner sc = new Scanner(f);
      sc.nextLine();
      sc.useDelimiter("[,\n]");
      
      for (int i=0; i<26; i++) {
        int x = sc.nextInt() + 20000;
        int y = sc.nextInt() + 20000;
        int z = sc.nextInt() + 20000;
        
        beacons.add(x*1600000000.0 + y*40000.0 + z*1.0);
        test.add(x*1600000000.0 + y*40000.0 + z*1.0);
      }
      scanners.add(0, 20000*1600000000.0 + 20000*40000.0 + 20000);

      for (int n=1; n<=37; n++) {
        for (int i=0; i<3; i++) System.out.println(sc.nextLine());

        int j = 0;
        while (sc.hasNextInt()) {
          int x = sc.nextInt() + 20000;
          int y = sc.nextInt() + 20000;
          int z = sc.nextInt() + 20000;
          
          test.add(x*1600000000.0 + y*40000.0 + z*1.0);
          System.out.println(x + " " + y + " " + z);
          j++;
        }

        sizes[n] = j;
      }

      sc.close();
      Collections.sort(beacons);
      System.out.println(beacons);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}