import java.util.*;
import java.io.*;

public class _20 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static HashMap<String, ArrayList<String>> recipients;
    public static HashMap<String, HashMap<String, Boolean>> sentFrom;
    public static HashMap<String, Boolean> types;
    public static HashMap<String, Boolean> states;
    public static int lows, highs;
    public static ArrayList<Pulse> nextSend;
    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        recipients = new HashMap<>();
        sentFrom = new HashMap<>();
        types = new HashMap<>();
        states = new HashMap<>();
        ArrayList<String> broadcaster = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String id = st.nextToken(); st.nextToken();
            if (id.equals("broadcaster")) {
                while (st.hasMoreTokens()) {
                    String next = st.nextToken();
                    if (next.contains(",")) broadcaster.add(next.substring(0,next.indexOf(",")));
                    else broadcaster.add(next);
                }
            }
            else {
                if (id.startsWith("%")) {
                    id = id.substring(1);
                    types.put(id, true);
                    states.put(id, false);
                }
                else {
                    id = id.substring(1);
                    types.put(id, false);
                }

                recipients.put(id, new ArrayList<>());
                while (st.hasMoreTokens()) {
                    String next = st.nextToken();
                    if (next.contains(",")) next = next.substring(0, next.indexOf(","));
                    if (!sentFrom.containsKey(next)) {
                        sentFrom.put(next, new HashMap<>());
                    }
                    sentFrom.get(next).put(id, false);
                    recipients.get(id).add(next);
                }
            }
        }

        lows = 0; highs = 0;
        for (int i=0; i<1000; i++) {
            lows++;
            for (String s : broadcaster) {
                nextSend = new ArrayList<>();
                lows++;
                sendPulse(s, false, "broadcaster");

                while (nextSend.size() != 0) {
                    Pulse nextPulse = nextSend.remove(0);
                    sendPulse(nextPulse.to, nextPulse.freq, nextPulse.from);
                }
            }
        }

        br.close();
        return "" + lows*highs + " " + lows + " " + highs;
    }

    public static void sendPulse(String to, boolean freq, String from) {
        if (!types.containsKey(to)) return;
        if (types.get(to)) {
            if (freq) return;
            states.put(to, !states.get(to));
            for (String s : recipients.get(to)) {
                if (states.get(to)) highs++;
                else lows++;

                nextSend.add(new Pulse(s, states.get(to), to));
            }
        }
        else {
            sentFrom.get(to).put(from, freq);
            boolean send = true;
            for (Boolean b : sentFrom.get(to).values()) {
                send &= b;
            }
            for (String s : recipients.get(to)) {
                if (send) lows++;
                else highs++;

                nextSend.add(new Pulse(s, !send, to));
            }
        }
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        recipients = new HashMap<>();
        sentFrom = new HashMap<>();
        types = new HashMap<>();
        states = new HashMap<>();
        ArrayList<String> broadcaster = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String id = st.nextToken(); st.nextToken();
            if (id.equals("broadcaster")) {
                while (st.hasMoreTokens()) {
                    String next = st.nextToken();
                    if (next.contains(",")) broadcaster.add(next.substring(0,next.indexOf(",")));
                    else broadcaster.add(next);
                }
            }
            else {
                if (id.startsWith("%")) {
                    id = id.substring(1);
                    types.put(id, true);
                    states.put(id, false);
                }
                else {
                    id = id.substring(1);
                    types.put(id, false);
                }

                recipients.put(id, new ArrayList<>());
                while (st.hasMoreTokens()) {
                    String next = st.nextToken();
                    if (next.contains(",")) next = next.substring(0, next.indexOf(","));
                    if (!sentFrom.containsKey(next)) {
                        sentFrom.put(next, new HashMap<>());
                    }
                    sentFrom.get(next).put(id, false);
                    recipients.get(id).add(next);
                }
            }
        }

        lows = 0; highs = 0;
        long[] reps = new long[broadcaster.size()];
        for (int i=1; i<100000000; i++) {
            for (int k=0; k<reps.length; k++) {
                if (reps[k] == 0) break;
                if (k == reps.length-1) {
                    long total = 1;
                    for (int m=0; m<reps.length; m++) total *= reps[m] / gcd(total, reps[m]);
                    br.close();
                    return total + "";
                }
            }
            lows++;

            int j=0;
            for (String s : broadcaster) {
                nextSend = new ArrayList<>();
                lows++;
                sendPulse(s, false, "broadcaster");

                while (nextSend.size() != 0) {
                    Pulse nextPulse = nextSend.remove(0);
                    if (nextPulse.to.equals("bb") && nextPulse.freq && reps[j] == 0) {
                        reps[j] = i;
                    }
                    sendPulse(nextPulse.to, nextPulse.freq, nextPulse.from);
                }

                j++;
            }
        }

        br.close();
        long total = 1;
        for (int i=0; i<reps.length; i++) total *= reps[i];
        return "" + total;
    }

    public static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }
}

class Pulse {
    String to;
    boolean freq;
    String from;

    public Pulse(String to, boolean freq, String from) {
        this.to = to;
        this.freq = freq;
        this.from = from;
    }

    public String toString() {
        return from + " --> " + to + " " + freq;
    }
}