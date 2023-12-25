import java.io.*;

public class _6 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        int[] times = {49, 78, 79, 80};
        int[] dists = {298, 1185, 1066, 1181};
        
        long total = 1;
        for (int i=0; i<times.length; i++) {
            long count = 0;
            for (int j=0; j<times[i]; j++) {
                if (j*(times[i]-j) > dists[i]) count++;
            }
            total *= count;
        }

        br.close();
        return "" + total;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));

        long time = 49787980L;
        long dist = 298118510661181L;
        
        long total = 0;
        for (int j=0; j<time; j++) {
            if (j*(time-j) > dist) total++;
        }

        br.close();
        return "" + total;
    }
}