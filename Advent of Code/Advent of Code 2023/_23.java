import java.util.*;
import java.io.*;

public class _23 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] map = new boolean[input.size()][input.get(0).length()];
        
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (input.get(i).charAt(j) == '#') map[i][j] = true;
            }
        }

        ArrayList<Vertex> stack = new ArrayList<>();
        Vertex[][] vMap = new Vertex[map.length][map[0].length];
        HashSet<Vertex> set = new HashSet<>();
        stack.add(new Vertex(0,1));
        vMap[0][1] = stack.get(0);

        while (stack.size() > 0) {
            Vertex current = stack.remove(0);
            map[current.r][current.c] = true;
            if (current.r == map.length-1) continue;
            else if (current.r == 0) {
                int dist = -makeStep(map, current.r+1, current.c, vMap);
                vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                stack.add(vMap[destR][destC]);

                set.add(current);
                continue;
            }

            if (!map[current.r-1][current.c] && input.get(current.r-1).charAt(current.c) != 'v') {
                int dist = -makeStep(map, current.r-1, current.c, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].dependencies.add(current);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r+1][current.c] && input.get(current.r+1).charAt(current.c) != '^') {
                int dist = -makeStep(map, current.r+1, current.c, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].dependencies.add(current);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r][current.c-1] && input.get(current.r).charAt(current.c-1) != '>') {
                int dist = -makeStep(map, current.r, current.c-1, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].dependencies.add(current);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r][current.c+1] && input.get(current.r).charAt(current.c+1) != '<') {
                int dist = -makeStep(map, current.r, current.c+1, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].dependencies.add(current);
                stack.add(vMap[destR][destC]);
            }

            set.add(current);
        }

        Vertex current = null;
        HashSet<Vertex> open = new HashSet<>();
        HashSet<Vertex> closed = new HashSet<>();
        open.add(vMap[0][1]);
        vMap[0][1].minDist = 0;
        do {
            int minDist = Integer.MAX_VALUE;
            for (Vertex v : open) {
                if (v.minDist < minDist && !closed.contains(v)) {
                    current = v;
                    minDist = v.minDist;
                }
            }

            loop:
            for (Vertex v : current.edges.keySet()) {
                if (closed.contains(v)) continue;
                if (v.minDist > current.edges.get(v) + current.minDist) {
                    v.minDist = current.edges.get(v) + current.minDist;
                }

                for (Vertex w : v.dependencies) {
                    if (!closed.contains(w) && w != current) continue loop;
                }

                open.add(v);
            }

            closed.add(current);
            open.remove(current);
        }
        while (current.r != map.length-1);

        br.close();
        return "" + current.minDist;
    }

    static int destR, destC;
    public static int makeStep(boolean[][] map, int r, int c, Vertex[][] vertices) {
        if (isVertex(map, r, c, vertices)) {
            destR = r;
            destC = c;
            return 1;
        }

        map[r][c] = true;
        if (!map[r-1][c]) return 1 + makeStep(map, r-1, c, vertices);
        if (!map[r+1][c]) return 1 + makeStep(map, r+1, c, vertices);
        if (!map[r][c-1]) return 1 + makeStep(map, r, c-1, vertices);
        if (!map[r][c+1]) return 1 + makeStep(map, r, c+1, vertices);

        return -1;
    }

    public static boolean isVertex(boolean[][] map, int r, int c, Vertex[][] vertices) {
        if (r == 0 || r == map.length-1) return true;
        if (vertices[r][c] != null) return true;

        int total = 0;
        if (!map[r-1][c]) total++;
        if (!map[r+1][c]) total++;
        if (!map[r][c-1]) total++;
        if (!map[r][c+1]) total++;

        return total >= 2;
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        ArrayList<String> input = new ArrayList<>();
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        boolean[][] map = new boolean[input.size()][input.get(0).length()];
        
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (input.get(i).charAt(j) == '#') map[i][j] = true;
            }
        }

        ArrayList<Vertex> stack = new ArrayList<>();
        Vertex[][] vMap = new Vertex[map.length][map[0].length];
        HashSet<Vertex> set = new HashSet<>();
        stack.add(new Vertex(0,1));
        vMap[0][1] = stack.get(0);

        while (stack.size() > 0) {
            Vertex current = stack.remove(0);
            map[current.r][current.c] = true;
            if (current.r == map.length-1) continue;
            else if (current.r == 0) {
                int dist = makeStep(map, current.r+1, current.c, vMap);
                vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].edges.put(current, dist);
                stack.add(vMap[destR][destC]);

                set.add(current);
                continue;
            }

            if (!map[current.r-1][current.c] && input.get(current.r-1).charAt(current.c) != 'v') {
                int dist = makeStep(map, current.r-1, current.c, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].edges.put(current, dist);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r+1][current.c] && input.get(current.r+1).charAt(current.c) != '^') {
                int dist = makeStep(map, current.r+1, current.c, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].edges.put(current, dist);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r][current.c-1] && input.get(current.r).charAt(current.c-1) != '>') {
                int dist = makeStep(map, current.r, current.c-1, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].edges.put(current, dist);
                stack.add(vMap[destR][destC]);
            }
            if (!map[current.r][current.c+1] && input.get(current.r).charAt(current.c+1) != '<') {
                int dist = makeStep(map, current.r, current.c+1, vMap);
                if (vMap[destR][destC] == null) vMap[destR][destC] = new Vertex(destR, destC);
                current.edges.put(vMap[destR][destC], dist);
                vMap[destR][destC].edges.put(current, dist);
                stack.add(vMap[destR][destC]);
            }

            set.add(current);
        }

        dfs(vMap[0][1], new HashSet<>(), vMap[map.length-1][map.length-2], 0);

        br.close();
        return max + " " + totalPaths;
    }

    static int max;
    static int totalPaths;
    public static void dfs(Vertex v, HashSet<Vertex> visited, Vertex goal, int dist) {
        if (goal == v) {
            if (dist > max) {
                max = dist;
            }

            totalPaths++;
            return;
        }

        for (Vertex w : v.edges.keySet()) {
            if (!visited.contains(w)) {
                visited.add(w);
                dfs(w, visited, goal, dist+v.edges.get(w));
                visited.remove(w);
            }
        }
    }
}

class Vertex {
    int r;
    int c;
    HashMap<Vertex, Integer> edges;
    HashSet<Vertex> dependencies;
    int minDist;

    public Vertex(int r, int c) {
        this.r = r;
        this.c = c;
        this.minDist = Integer.MAX_VALUE;

        edges = new HashMap<>();
        dependencies = new HashSet<>();
    }

    public String toString() {
        return r + " " + c + " " + edges.size();
    }
}