import java.util.*;
import java.io.*;

public class _4 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        int total = 0;
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            st.nextToken();
            st.nextToken();

            HashSet<Integer> winners = new HashSet<>();
            String next;

            while (!(next = st.nextToken()).equals("|")) {
                winners.add(Integer.parseInt(next));
            }

            int add = 1;
            while (st.hasMoreTokens()) {
                if (winners.contains(Integer.parseInt(st.nextToken()))) {
                    add *= 2;
                }
            }

            total += add/2;
        }


        br.close();

        return total + "";
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        int[] occs = new int[216];
        for (int i=0; i<216; i++) occs[i] = 1;
        int total = 0;
        int i = 0;
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            st.nextToken();
            st.nextToken();

            HashSet<Integer> winners = new HashSet<>();
            String next;

            while (!(next = st.nextToken()).equals("|")) {
                winners.add(Integer.parseInt(next));
            }

            int add = 0;
            while (st.hasMoreTokens()) {
                if (winners.contains(Integer.parseInt(st.nextToken()))) {
                    add++;
                }
            }

            for (int j=1; j<=add; j++) {
                occs[i+j] += occs[i];
            }

            total += occs[i++];
        }


        br.close();

        return total + "";
    }
}