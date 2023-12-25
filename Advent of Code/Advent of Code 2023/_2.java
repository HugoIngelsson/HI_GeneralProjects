import java.util.*;
import java.io.*;

public class _2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st;
        String in;

        int total = 0;
        while ((in = br.readLine()) != null && in.length() > 0) {
            st = new StringTokenizer(in);
            st.nextToken();
            st.nextToken();

            int r=0, g=0, b=0;
            int mr=0, mg=0, mb=0;

            while (st.hasMoreTokens()) {
                int delta = Integer.parseInt(st.nextToken());
                String next = st.nextToken();

                if (next.contains("red")) {
                    r += delta;
                }
                else if (next.contains("blue")) {
                    b += delta;
                }
                else if (next.contains("green")) {
                    g += delta;
                }

                if (next.contains(";") || !st.hasMoreTokens()) {
                    mr = Math.max(r, mr);
                    mb = Math.max(b, mb);
                    mg = Math.max(g, mg);
                    r=0;
                    g=0;
                    b=0;
                }
            }

            System.out.println(mr + " " + mb + " " + mg);
            System.out.println(mr*mb*mg);
            total += mb*mr*mg;
        }

        pw.println(total);
        br.close();
        pw.close();
    }
}