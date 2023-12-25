import java.util.*;
import java.io.*;

public class _19 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        HashMap<String, Workflow> flows = new HashMap<>();
        while ((in = br.readLine()) != null) {
            if (in.length() == 0) break;
            String flowName = in.substring(0, in.indexOf("{"));
            String rest = in.substring(in.indexOf("{")+1);

            st = new StringTokenizer(rest, ",");
            ArrayList<Rule> rules = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String next = st.nextToken();
                int id;
                switch(next.charAt(0)) {
                    case('x'): id=0; break;
                    case('m'): id=1; break;
                    case('a'): id=2; break;
                    default: id=3; break;
                }

                if (next.contains(">")) {
                    rules.add(new Rule(id, true, 
                        Integer.parseInt(next.substring(next.indexOf(">")+1,next.indexOf(":"))),
                        next.substring(next.indexOf(":")+1)));
                }
                else if (next.contains("<")) {
                    rules.add(new Rule(id, false, 
                        Integer.parseInt(next.substring(next.indexOf("<")+1,next.indexOf(":"))),
                        next.substring(next.indexOf(":")+1)));
                }
                else {
                    flows.put(flowName, new Workflow(rules, next.substring(0,next.indexOf("}"))));
                }
            }
        }

        long total = 0;
        while ((in=br.readLine()) != null) {
            st = new StringTokenizer(in, ",");
            int[] ids = new int[4];
            String next = st.nextToken();
            ids[0] = Integer.parseInt(next.substring(next.indexOf("=")+1));
            next = st.nextToken();
            ids[1] = Integer.parseInt(next.substring(next.indexOf("=")+1));
            next = st.nextToken();
            ids[2] = Integer.parseInt(next.substring(next.indexOf("=")+1));
            next = st.nextToken();
            ids[3] = Integer.parseInt(next.substring(next.indexOf("=")+1, next.indexOf("}")));

            String rep = "in";
            while (!rep.equals("A") && !rep.equals("R")) {
                rep = work(flows.get(rep), ids);
            }

            if (rep.equals("A")) {
                total += ids[0]+ids[1]+ids[2]+ids[3];
            }
        }

        br.close();
        return "" + total;
    }

    public static String work(Workflow flow, int[] vals) {
        for (Rule r : flow.rules) {
            if (r.rule(vals).length() != 0) {
                return r.rule(vals);
            }
        }
        return flow.auto;
    }

    static HashMap<String, Workflow> flows = new HashMap<>();
    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;

        flows = new HashMap<>();
        while ((in = br.readLine()) != null) {
            if (in.length() == 0) break;
            String flowName = in.substring(0, in.indexOf("{"));
            String rest = in.substring(in.indexOf("{")+1);

            st = new StringTokenizer(rest, ",");
            ArrayList<Rule> rules = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String next = st.nextToken();
                int id;
                switch(next.charAt(0)) {
                    case('x'): id=0; break;
                    case('m'): id=1; break;
                    case('a'): id=2; break;
                    default: id=3; break;
                }

                if (next.contains(">")) {
                    rules.add(new Rule(id, true, 
                        Integer.parseInt(next.substring(next.indexOf(">")+1,next.indexOf(":"))),
                        next.substring(next.indexOf(":")+1)));
                }
                else if (next.contains("<")) {
                    rules.add(new Rule(id, false, 
                        Integer.parseInt(next.substring(next.indexOf("<")+1,next.indexOf(":"))),
                        next.substring(next.indexOf(":")+1)));
                }
                else {
                    flows.put(flowName, new Workflow(rules, next.substring(0,next.indexOf("}"))));
                }
            }
        }

        long total = 0;
        long[][] start = new long[4][2];
        for (int i=0; i<4; i++) {
            start[i][0] = 1;
            start[i][1] = 4000;
        }
        RuleRange startRange = new RuleRange(start);
        total = range(startRange, "in");

        br.close();
        return "" + total;
    }

    public static long range(RuleRange cur, String flow) {
        long total = 0;
        if (flow.equals("R")) {
            return 0;
        }
        else if (flow.equals("A")) {
            long[][] ranges = cur.ranges;
            return (ranges[0][1]-ranges[0][0]+1)*
                (ranges[1][1]-ranges[1][0]+1)*
                (ranges[2][1]-ranges[2][0]+1)*
                (ranges[3][1]-ranges[3][0]+1);
        }

        for (Rule r : flows.get(flow).rules) {
            ArrayList<RuleRange> split = cur.split(r.ranges(), r.id);
            if (split.get(0) != null) {
                total += range(split.get(0), r.result);
                if (split.size() > 2) total += range(split.get(2), flow);
                if (split.size() > 1) cur = split.get(1);
                else break;
            }
        }
        total += range(cur, flows.get(flow).auto);

        return total;
    }
}

class RuleRange {
    long[][] ranges;

    public RuleRange(long[][] ranges) {
        this.ranges = ranges;
    }

    public ArrayList<RuleRange> split(long[][] splitter, int id) {
        ArrayList<RuleRange> ret = new ArrayList<>();
        if (ranges[id][0] >= splitter[id][0] || ranges[id][1] <= splitter[id][1]) {
            if (ranges[id][0] >= splitter[id][0] && ranges[id][1] <= splitter[id][1]) {
                ret.add(this);
            }   
            else if (ranges[id][0] >= splitter[id][0]) {
                long save = ranges[id][1];
                ranges[id][1] = splitter[id][1];
                ret.add(new RuleRange(deepCopy(ranges)));
                ranges[id][1] = save;
                ranges[id][0] = splitter[id][1]+1;
                ret.add(new RuleRange(deepCopy(ranges)));
            }
            else {
                long save = ranges[id][0];
                ranges[id][0] = splitter[id][0];
                ret.add(new RuleRange(deepCopy(ranges)));
                ranges[id][0] = save;
                ranges[id][1] = splitter[id][0]-1;
                ret.add(new RuleRange(deepCopy(ranges)));
            }
        }
        else {
            if (splitter[id][1] >= ranges[id][0]) {
                ret.add(new RuleRange(splitter));
                long[][] temp = new long[4][2];
                for (int i=0; i<4; i++) {temp[i][0]=ranges[i][0]; temp[i][1]=ranges[i][1];}

                temp[id][0] = splitter[id][1]+1;
                ret.add(new RuleRange(deepCopy(temp)));
                temp[id][0] = ranges[id][0];
                temp[id][1] = splitter[id][0]-1;
                ret.add(new RuleRange(deepCopy(temp)));
            }
            else {
                ret.add(null);
                ret.add(this);
            }
        }

        return ret;
    }

    public long[][] deepCopy(long[][] in) {
        long[][] ret = new long[in.length][in[0].length];
        for (int i=0; i<in.length; i++) {
            for (int j=0; j<in[0].length; j++) {
                ret[i][j] = in[i][j];
            }
        }

        return ret;
    }
}

class Rule {
    int id;
    boolean gt;
    int val;
    String result;

    public Rule(int id, boolean gt, int val, String result) {
        this.id = id;
        this.gt = gt;
        this.val = val;
        this.result = result;
    }

    public String rule(int[] ids) {
        if (gt) if (ids[id]>val) return result;
        if (!gt) if (ids[id]<val) return result;
        return "";
    }

    public long[][] ranges() {
        if (gt) {
            long[][] ret = new long[4][2];
            for (int i=0; i<4; i++) {
                ret[i][0] = 0; ret[i][1] = 4000;
            }
            ret[id][0] = val+1;
            return ret;
        }
        else {
            long[][] ret = new long[4][2];
            for (int i=0; i<4; i++) {
                ret[i][0] = 0; ret[i][1] = 4000;
            }
            ret[id][1] = val-1;
            return ret;
        }
    }

    public String toString() {
        return id + " " + gt + " " + val + " " + result;
    }
}

class Workflow {
    ArrayList<Rule> rules;
    String auto;

    public Workflow(ArrayList<Rule> rules, String auto) {
        this.rules = rules;
        this.auto = auto;
    }
}