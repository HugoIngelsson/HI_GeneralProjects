import java.util.*;
import java.io.*;

public class _8 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;
        String instructions = br.readLine();
        br.readLine();

        HashMap<String, Node> nodes = new HashMap<String, Node>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String from = st.nextToken(); st.nextToken();
            String left = st.nextToken().substring(1,4);
            String right = st.nextToken().substring(0,3);

            Node F = new Node(from), L = new Node(left), R = new Node(right);

            if (nodes.containsKey(from)) F = nodes.get(from);
            if (nodes.containsKey(left)) L = nodes.get(left);
            if (nodes.containsKey(right)) R = nodes.get(right);

            if (left.equals(from)) L = F;
            if (right.equals(left)) R = L;

            F.left = L;
            F.right = R;
            nodes.put(from, F);
            nodes.put(left, L);
            nodes.put(right, R);
        }

        Node curr = nodes.get("AAA");
        int steps = 0;
        while (curr != nodes.get("ZZZ")) {
            if (instructions.charAt(steps%instructions.length()) == 'L') {
                curr = curr.left;
            }
            else curr = curr.right;
            steps++;
        }

        br.close();
        return "" + steps;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        StringTokenizer st;
        String in;
        String instructions = br.readLine();
        br.readLine();

        HashMap<String, Node> nodes = new HashMap<String, Node>();
        while ((in = br.readLine()) != null) {
            st = new StringTokenizer(in);
            String from = st.nextToken(); st.nextToken();
            String left = st.nextToken().substring(1,4);
            String right = st.nextToken().substring(0,3);

            Node F = new Node(from), L = new Node(left), R = new Node(right);

            if (nodes.containsKey(from)) F = nodes.get(from);
            if (nodes.containsKey(left)) L = nodes.get(left);
            if (nodes.containsKey(right)) R = nodes.get(right);

            if (left.equals(from)) L = F;
            if (right.equals(left)) R = L;

            F.left = L;
            F.right = R;
            nodes.put(from, F);
            nodes.put(left, L);
            nodes.put(right, R);
        }

        HashSet<Integer> stepList = new HashSet<>();
        for (Map.Entry<String, Node> e : nodes.entrySet()) {
            if (e.getKey().charAt(2) == 'A') {
                int steps = 0;
                Node curr = e.getValue();
                while (curr.id.charAt(2) != 'Z') {
                    if (instructions.charAt(steps%instructions.length()) == 'L') {
                        curr = curr.left;
                    }
                    else curr = curr.right;
                    steps++;
                }
                stepList.add(steps);
            }
        }

        long total = 1;
        for (Integer i : stepList) {
            long gcd = gcd(total, i);
            total *= i;
            total /= gcd;
        }

        br.close();
        return "" + total;
    }

    public static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }
}

class Node {
    Node left;
    Node right;
    String id;

    public Node(String id) {
        left = null;
        right = null;
        this.id = id;
    }
}