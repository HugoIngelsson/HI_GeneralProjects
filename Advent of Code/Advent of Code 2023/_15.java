import java.util.*;
import java.io.*;

public class _15 {
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
            st = new StringTokenizer(in, ",");
            
            while (st.hasMoreTokens()) total += hash(st.nextToken());
        }

        br.close();
        return "" + total;
    }

    public static int hash(String in) {
        int ret = 0;
        for (int i=0; i<in.length(); i++) {
            ret += (int)in.charAt(i);
            ret *= 17;
            ret %= 256;
        }

        return ret;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        long total = 0;
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in, ",");
            
            HashMap<Integer, ArrayList<String>> map = new HashMap<>();
            for (int i=0; i<256; i++) map.put(i, new ArrayList<String>());
            
            while (st.hasMoreTokens()) {
                String next = st.nextToken();

                if (next.contains("-")) {
                    String cut = next.substring(0, next.indexOf("-"));
                    ArrayList<String> toCut = map.get(hash(cut));
                    if (toCut != null) {
                        for (int i=0; i<toCut.size(); i++) {
                            if (toCut.get(i).substring(0, toCut.get(i).indexOf("=")).equals(cut)) {
                                toCut.remove(i--);
                                break;
                            }
                        }
                    }
                }
                else {
                    String cut = next.substring(0, next.indexOf("="));
                    int hash = hash(cut);
                    if (map.containsKey(hash)) {
                        ArrayList<String> toCut = map.get(hash);
                        for (int i=0; i<toCut.size(); i++) {
                            if (toCut.get(i).substring(0, toCut.get(i).indexOf("=")).equals(cut)) {
                                toCut.set(i, next);
                                break;
                            }
                            if (i == toCut.size()-1) {
                                toCut.add(next);
                                break;
                            }
                        }
                        if (toCut.size() == 0) toCut.add(next);
                    }
                    else {
                        map.put(hash, new ArrayList<String>());
                        map.get(hash).add(next);
                    }
                }
            }

            total = 0;
            for (Map.Entry<Integer, ArrayList<String>> e : map.entrySet()) {
                int i = 1;
                for (String s : e.getValue()) {
                    total += (e.getKey()+1)*i*Integer.parseInt(s.substring(s.length()-1));
                    i++;
                }
            }
        }

        br.close();
        return "" + total;
    }
}