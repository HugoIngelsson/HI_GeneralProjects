import java.util.*;
import java.io.*;

public class _24 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Hail> hail = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            long[] process = new long[6];
            process[0] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[1] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[2] = Long.parseLong(st.nextToken().replaceAll(",", "")); st.nextToken();
            process[3] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[4] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[5] = Long.parseLong(st.nextToken().replaceAll(",", ""));

            hail.add(new Hail(process));
        }

        long min = 200000000000000L, max = 400000000000000L;
        long total = 0;
        for (int i=0; i<hail.size()-1; i++) {
            for (int j=i+1; j<hail.size(); j++) {
                double[] intersection = hail.get(i).intersection(hail.get(j));
                if (intersection == null) continue;

                if (intersection[0] >= min && intersection[0] <= max &&
                    intersection[1] >= min && intersection[1] <= max) total++;
            }
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Hail> hail = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            long[] process = new long[6];
            process[0] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[1] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[2] = Long.parseLong(st.nextToken().replaceAll(",", "")); st.nextToken();
            process[3] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[4] = Long.parseLong(st.nextToken().replaceAll(",", ""));
            process[5] = Long.parseLong(st.nextToken().replaceAll(",", ""));

            hail.add(new Hail(process));
        }

        System.out.print("Solve[{");
        for (int i=0; i<3; i++) {
            System.out.print("x+vx*t" + i + "==" + hail.get(i).x + "+" + hail.get(i).vx + "*t" + i + ", ");
            System.out.print("y+vy*t" + i + "==" + hail.get(i).y + "+" + hail.get(i).vy + "*t" + i + ", ");
            System.out.print("z+vz*t" + i + "==" + hail.get(i).z + "+" + hail.get(i).vz + "*t" + i);
            if (i != 2) System.out.print(",");
            else System.out.print("},");
        }

        System.out.println("{x,vx,y,vy,z,vz,t0,t1,t2}]");

        br.close();
        return "" + (420851642592931L + 273305746686315L + 176221626745613L);
    }

    //{{x->420851642592931,vx->-261,y->273305746686315,vy->15,z->176221626745613,vz->233,t0->281427954234,t1->487736179331,t2->637228617556}} 
    // x = 420851642592931
    // y = 273305746686315
    // z = 176221626745613

    public static boolean isZeroVector(long[] in) {
        return in[0] == 0 && in[1] == 0 && in[2] == 0;
    }

    public static long[] cross(long[] a, long[] b) {
        long[] ret = {a[1]*b[2]-a[2]*b[1],
                        a[2]*b[0]-a[0]*b[2],
                        a[0]*b[1]-a[1]*b[0]};

        return ret;
    }
}

class Hail {
    long x,y,z;
    long vx,vy,vz;

    public Hail(long[] in) {
        x = in[0];
        y = in[1];
        z = in[2];
        vx = in[3];
        vy = in[4];
        vz = in[5];
    }

    public double[] intersection(Hail other) {
        double a = (double)this.vy / this.vx;
        double b = this.y - a*this.x;
        double c = (double)other.vy / other.vx;
        double d = other.y - c*other.x;
        if (a == c) return null;

        double xx = (d-b)/(a-c);

        if ((xx-this.x)/this.vx < 0 || (xx-other.x)/other.vx < 0) return null;
        double yy = a*xx+b;
        double[] ret = {xx, yy};

        return ret;
    }

    public long[] cross(Hail other) {
        long[] ret = {this.vy*other.vz-this.vz*other.vy,
                        this.vz*other.vx-this.vx*other.vz,
                        this.vx*other.vy-this.vy*other.vx};

        return ret;
    }

    public String toString() {
        return x + " " + y + " " + z;
    }
}