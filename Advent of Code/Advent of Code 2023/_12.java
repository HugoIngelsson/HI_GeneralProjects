import java.util.*;
import java.io.*;

public class _12 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;
        long total = 0;

        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String sprints = st.nextToken();
            st = new StringTokenizer(st.nextToken(), ",");
            
            ArrayList<Integer> counts = new ArrayList<>();
            while (st.hasMoreTokens()) counts.add(Integer.parseInt(st.nextToken()));
            total += checkAll("", sprints, counts);
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;
        long total = 0;

        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String sprints = st.nextToken();
            st = new StringTokenizer(st.nextToken(), ",");
            
            ArrayList<Integer> count = new ArrayList<>();
            while (st.hasMoreTokens()) count.add(Integer.parseInt(st.nextToken()));
            ArrayList<Integer> counts = new ArrayList<>();
            String spring = sprints;
            counts.addAll(count);
            for (int i=0; i<4; i++) {
                sprints += "?" + spring;
                counts.addAll(count);
            }

            total += dp(sprints, counts);
        }

        br.close();
        return "" + total;
    }

    public static long checkAll(String curr, String left, ArrayList<Integer> counts) {
        if (left.length() == 0) {
            return isValid(curr, counts);
        }

        if (left.charAt(0) == '?') return checkAll(curr + ".", left.substring(1), counts) +
                                            checkAll(curr + "#", left.substring(1), counts); 
        return checkAll(curr + left.charAt(0), left.substring(1), counts);
    }

    public static long isValid(String in, ArrayList<Integer> counts) {
        int count = 0;
        int id = 0;
        for (int i=0; i<in.length(); i++) {
            if (in.charAt(i) == '#') count++;
            else if (count != 0) {
                if (id >= counts.size() || counts.get(id++) != count) return 0;
                else count = 0;
            }
        }

        if (count != 0) {
            if (id >= counts.size() || counts.get(id++) != count) return 0;
        }

        return (id == counts.size()) ? 1 : 0;
    }

    public static long dp(String in, ArrayList<Integer> counts) {
        long[][] dp = new long[counts.size()][in.length()];

        for (int i=counts.size()-1; i>=0; i--) {
            for (int j=0; j<in.length(); j++) {
                if (i == counts.size()-1) {
                    if (checkWorks(j, counts.get(i), in.length(), in)) dp[i][j] = 1;
                }
                else if (i==0) {
                    for (int k=j+counts.get(i)+1; k<in.length(); k++) {
                        if (checkWorks(j, counts.get(i), k, in)) dp[i][j] += dp[i+1][k];
                    }
                    if (in.charAt(j) == '#') break;
                }
                else {
                    for (int k=j+counts.get(i)+1; k<in.length(); k++) {
                        if (checkWorks(j, counts.get(i), k, in)) dp[i][j] += dp[i+1][k];
                    }
                }
            }
        }

        long ret = 0;
        for (int i=0; i<in.length(); i++) ret += dp[0][i];
        return ret;
    }

    public static boolean checkWorks(int id, int length, int end, String in) {
        if (id + length > in.length()) return false;
        for (int i=0; i<length; i++) {
            if (in.charAt(i+id) == '.') return false;
        }
        for (int i=length; i+id<end; i++) {
            if (in.charAt(i+id) == '#') return false;
        }
        return (id+length == in.length() || in.charAt(id+length) != '#');
    }

    public static long checkLast(int id, String in) {
        for (int i=id; i<in.length(); i++) {
            if (in.charAt(i) == '#') return 0;
        }

        return 1;
    }
}