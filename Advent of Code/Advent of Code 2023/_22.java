import java.util.*;
import java.io.*;

public class _22 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Brick> bricks = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in, "~");

            String coord1 = st.nextToken();
            String coord2 = st.nextToken();
            st = new StringTokenizer(coord1, ",");
            StringTokenizer st2 = new StringTokenizer(coord2, ",");
            int[] coords = {Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st2.nextToken()),
                            Integer.parseInt(st2.nextToken()),
                            Integer.parseInt(st2.nextToken())};

            bricks.add(new Brick(coords));
        }

        Collections.sort(bricks);

        ArrayList<Brick> fallen = new ArrayList<>();
        for (Brick b : bricks) {
            int max = 1;
            for (Brick comp : fallen) {
                if (b.overlap(comp)) {
                    max = Math.max(max, comp.z2+1);
                }
            }

            fallen.add(b.shiftToZ(max));
        }

        long total = 0;
        for (Brick b1 : fallen) {
            int supports = 0;
            for (Brick b2 : fallen) {
                if (b1 != b2 && b1.overlap(b2) && b1.z1 == b2.z2+1) {
                    supports++;
                }
            }

            b1.supports = supports;
        }

        for (Brick b1 : fallen) {
            boolean safe = true;
            
            for (Brick b2 : fallen) {
                if (b1 != b2 && b1.overlap(b2) && b1.z2 == b2.z1-1) {
                    if (b2.supports == 1) safe = false;
                }
            }

            if (safe) total++;
        }


        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Brick> bricks = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in, "~");

            String coord1 = st.nextToken();
            String coord2 = st.nextToken();
            st = new StringTokenizer(coord1, ",");
            StringTokenizer st2 = new StringTokenizer(coord2, ",");
            int[] coords = {Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st.nextToken()),
                            Integer.parseInt(st2.nextToken()),
                            Integer.parseInt(st2.nextToken()),
                            Integer.parseInt(st2.nextToken())};

            bricks.add(new Brick(coords));
        }

        Collections.sort(bricks);

        ArrayList<Brick> fallen = new ArrayList<>();
        for (Brick b : bricks) {
            int max = 1;
            for (Brick comp : fallen) {
                if (b.overlap(comp)) {
                    max = Math.max(max, comp.z2+1);
                }
            }

            fallen.add(b.shiftToZ(max));
        }

        long total = 0;
        for (Brick b1 : fallen) {
            for (Brick b2 : fallen) {
                if (b1 != b2 && b1.overlap(b2) && b1.z1 == b2.z2+1) {
                    b1.supportBricks.add(b2);
                }
            }
        }

        for (Brick b1 : fallen) {
            HashSet<Brick> zapped = new HashSet<>();
            zapped.add(b1);
            long cur = 0;

            for (Brick b2 : fallen) {
                if (b1 == b2 || b2.supportBricks.size() == 0) continue;

                boolean saved = false;
                for (Brick b3 : b2.supportBricks) {
                    if (!zapped.contains(b3)) saved = true;
                }

                if (!saved) {
                    zapped.add(b2);
                    cur++;
                }
            }

            total += cur;
        }

        br.close();
        return "" + total;
    }
}

class Brick implements Comparable<Brick> {
    int x1,x2;
    int y1,y2;
    int z1,z2;
    int supports;
    HashSet<Brick> supportBricks;

    public Brick(int[] vals) {
        x1 = vals[0];
        y1 = vals[1];
        z1 = vals[2];
        x2 = vals[3];
        y2 = vals[4];
        z2 = vals[5];

        supportBricks = new HashSet<>();
    }

    public int compareTo(Brick other) {
        if (this.z1 < other.z1) return -1;
        else if (this.z1 > other.z1) return 1;
        else return 0;
    }

    public boolean overlap(Brick other) {
        return coordOverlap(this.x1, this.x2, other.x1, other.x2) &&
                coordOverlap(this.y1, this.y2, other.y1, other.y2);
    }

    public boolean coordOverlap(int x1, int x2, int x3, int x4) {
        return (x1 >= x3 && x1 <= x4 || x2 >= x3 && x2 <= x4) ||
                (x3 >= x1 && x3 <= x2 || x4 >= x1 && x4 <= x2);
    }

    public Brick shiftToZ(int z) {
        int[] ret = {x1,y1,z1,x2,y2,z2};
        ret[5] = z2 - (z1-z);
        ret[2] = z;
        return new Brick(ret);
    }

    public String toString() {
        return x1+","+y1+","+z1+"~"+x2+","+y2+","+z2;
    }
}