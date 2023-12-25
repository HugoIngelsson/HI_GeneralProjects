import java.util.*;
import java.io.*;

public class _9 {
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
            ArrayList<Integer> base = new ArrayList<Integer>();
            while (st.hasMoreTokens()) base.add(Integer.parseInt(st.nextToken()));
            
            @SuppressWarnings("unchecked")
            List<Integer>[] steps = new List[base.size()];
            for (int i=0; i<steps.length; i++) steps[i] = new ArrayList<Integer>();
            steps[0] = base;

            int leave = 0;
            for (int i=1; i<steps.length; i++) {
                boolean delta = false;
                for (int j=0; j<steps.length-i; j++) {
                    steps[i].add(steps[i-1].get(j+1) - steps[i-1].get(j));
                    if (steps[i].get(j) != 0) delta = true;
                }

                if (!delta) {
                    leave = i-1;
                    break;
                }
            }

            for (int i=leave; i>=0; i--) {
                steps[i].add(steps[i].get(steps[i].size()-1) + steps[i+1].get(steps[i+1].size()-1));
            }

            total += steps[0].get(steps[0].size()-1);
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        int total = 0;
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            ArrayList<Integer> base = new ArrayList<Integer>();
            while (st.hasMoreTokens()) base.add(Integer.parseInt(st.nextToken()));
            
            @SuppressWarnings("unchecked")
            List<Integer>[] steps = new List[base.size()];
            for (int i=0; i<steps.length; i++) steps[i] = new ArrayList<Integer>();
            steps[0] = base;

            int leave = 0;
            for (int i=1; i<steps.length; i++) {
                boolean delta = false;
                for (int j=0; j<steps.length-i; j++) {
                    steps[i].add(steps[i-1].get(j+1) - steps[i-1].get(j));
                    if (steps[i].get(j) != 0) delta = true;
                }

                if (!delta) {
                    leave = i-1;
                    break;
                }
            }

            for (int i=leave; i>=0; i--) {
                steps[i].add(0, steps[i].get(0) - steps[i+1].get(0));
            }

            total += steps[0].get(0);
        }

        br.close();
        return "" + total;
    }
}