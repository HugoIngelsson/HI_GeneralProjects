import java.util.*;
import java.io.*;

public class _5 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Long> seeds = new ArrayList<Long>();
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        while (st.hasMoreTokens()) seeds.add(Long.parseLong(st.nextToken()));
        ArrayList<Long> push = new ArrayList<Long>();
        br.readLine();

        while ((in = br.readLine()) != null) {
            if (in.length() == 0) {
                for (Long i : seeds) push.add(i);
                seeds = push;
            }
            else if (in.contains(":")) {
                push = new ArrayList<>();
            } 
            else {
                st = new StringTokenizer(in);
                long startTo = Long.parseLong(st.nextToken());
                long startFrom = Long.parseLong(st.nextToken());
                long range = Long.parseLong(st.nextToken());

                for (int j=0; j<seeds.size(); j++) {
                    Long i = seeds.get(j);
                    if (i - startFrom >= 0 && i - startFrom < range) {
                        push.add(startTo + i - startFrom);
                        seeds.remove(j--);
                    }
                }
            }
        }

        seeds.addAll(push);
        long min = Integer.MAX_VALUE;
        for (Long i : seeds) {
            min = Math.min(min, i);
        }

        br.close();
        return "" + min;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        ArrayList<Range> seeds = new ArrayList<Range>();
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        while (st.hasMoreTokens()) {
            Long min = Long.parseLong(st.nextToken());
            Long len = Long.parseLong(st.nextToken());
            seeds.add(new Range(min, min+len-1));
        }
        ArrayList<Range> push = new ArrayList<Range>();
        br.readLine();

        while ((in = br.readLine()) != null) {
            if (in.length() == 0) {
                for (Range i : seeds) {
                    push.add(i);
                }
                seeds = push;
            }
            else if (in.contains(":")) {
                push = new ArrayList<>();
            } 
            else {
                st = new StringTokenizer(in);
                long startTo = Long.parseLong(st.nextToken());
                long startFrom = Long.parseLong(st.nextToken());
                long range = Long.parseLong(st.nextToken());

                for (int j=0; j<seeds.size(); j++) {
                    Range i = seeds.get(j);
                    if (i.min - startFrom >= 0 && i.min - startFrom < range) {
                        if (i.max - startFrom >= 0 && i.max - startFrom < range) {
                            push.add(new Range(startTo + i.min - startFrom, startTo + i.max - startFrom));
                            seeds.remove(j--);
                        }
                        else {
                            push.add(new Range(startTo + i.min - startFrom, startTo + range - 1));
                            i.min = startFrom + range;
                        }
                    }
                    else if (i.max - startFrom >= 0 && i.max - startFrom < range) {
                        push.add(new Range(startTo, startTo + i.max - startFrom));
                        i.max = startFrom-1;
                    }
                    else if (i.min - startFrom < 0 && i.max - startFrom >= range) {
                        seeds.add(new Range(i.min, startFrom-1));
                        seeds.add(new Range(startFrom+range, i.max));
                        push.add(new Range(startTo, startTo + range - 1));
                        seeds.remove(j--);
                    }
                }
            }
        }

        seeds.addAll(push);
        long min = Integer.MAX_VALUE;
        for (Range i : seeds) {
            min = Math.min(i.min, min);
        }

        br.close();
        return "" + min;
    }
}

class Range {
        long min;
        long max;

        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public String toString() {
            return min + " " + max;
        }
    }