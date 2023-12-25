import java.io.*;

public class _10 {
    public static void main(String[] args) throws IOException {
        System.out.println(p1());
        System.out.println(p2());
    }

    public static String p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        int x = -1, y = -1;
        int sx = -1, sy = -1;
        int[][] map = new int[140][140];
        int r = 0;
        while ((in = br.readLine()) != null) {
            for (int i=0; i<in.length(); i++) {
                map[r][i] = (int)in.charAt(i);
                if (in.charAt(i) == 'S') {
                    sx = r;
                    sy = i;
                }
            }
            r++;
        }

        map[sx][sy] = 'L';
        x = sx; y = sy;
        int dir = 2;
        int total = 0;
        do {
            total++;
            if (map[x][y] == '|') {
                if (dir == 0) {
                    x--;
                    dir = 0;
                }
                else {
                    x++;
                    dir = 2;
                }
            }
            else if (map[x][y] == '-') {
                if (dir == 1) {
                    y++;
                    dir = 1;
                }
                else {
                    y--;
                    dir = 3;
                }
            }
            else if (map[x][y] == 'L') {
                if (dir == 2) {
                    y++;
                    dir = 1;
                }
                else {
                    x--;
                    dir = 0;
                }
            }
            else if (map[x][y] == 'J') {
                if (dir == 2) {
                    y--;
                    dir = 3;
                }
                else {
                    x--;
                    dir = 0;
                }
            }
            else if (map[x][y] == '7') {
                if (dir == 0) {
                    y--;
                    dir = 3;
                }
                else {
                    x++;
                    dir = 2;
                }
            }
            else if (map[x][y] == 'F') {
                if (dir == 0) {
                    y++;
                    dir = 1;
                }
                else {
                    x++;
                    dir = 2;
                }
            }
        }
        while (x != sx || y != sy);

        br.close();
        return total / 2 + "";
    }

    public static String p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        String in;

        int x = -1, y = -1;
        int sx = -1, sy = -1;
        int[][] map = new int[140][140];
        int r = 0;
        while ((in = br.readLine()) != null) {
            for (int i=0; i<in.length(); i++) {
                map[r][i] = (int)in.charAt(i);
                if (in.charAt(i) == 'S') {
                    sx = r;
                    sy = i;
                }
            }
            r++;
        }

        map[sx][sy] = 'L';
        x = sx; y = sy;
        int dir = 1;
        int total = 0;
        boolean[][] looped = new boolean[140][140];
        boolean[][] rights = new boolean[140][140];
        do {
            total++;
            if (map[x][y] == '|') {
                if (dir == 0) {
                    rights[x][y+1] = true;
                    x--;
                    dir = 0;
                }
                else {
                    rights[x][y-1] = true;
                    x++;
                    dir = 2;
                }
            }
            else if (map[x][y] == '-') {
                if (dir == 1) {
                    rights[x+1][y] = true;
                    y++;
                    dir = 1;
                }
                else {
                    rights[x-1][y] = true;
                    y--;
                    dir = 3;
                }
            }
            else if (map[x][y] == 'L') {
                if (dir == 2) {
                    rights[x][y-1] = true;
                    rights[x+1][y] = true;
                    y++;
                    dir = 1;
                }
                else {
                    x--;
                    dir = 0;
                }
            }
            else if (map[x][y] == 'J') {
                if (dir == 2) {
                    y--;
                    dir = 3;
                }
                else {
                    rights[x+1][y] = true;
                    rights[x][y+1] = true;
                    x--;
                    dir = 0;
                }
            }
            else if (map[x][y] == '7') {
                if (dir == 0) {
                    rights[x][y+1] = true;
                    rights[x-1][y] = true;
                    y--;
                    dir = 3;
                }
                else {
                    x++;
                    dir = 2;
                }
            }
            else if (map[x][y] == 'F') {
                if (dir == 0) {
                    y++;
                    dir = 1;
                }
                else {
                    rights[x-1][y] = true;
                    rights[x][y-1] = true;
                    x++;
                    dir = 2;
                }
            }

            looped[x][y] = true;
        }
        while (x != sx || y != sy);

        // for (int j=0; j<80; j++) {
        //     for (int i=0; i<80; i++) {
        //         if (rights[j][i] && !looped[j][i]) System.out.print('#');
        //         else System.out.print(".");
        //     }
        //     System.out.println();
        // }

        total = 0;
        for (int rr=1; rr<139; rr++) {
            for (int c=1; c<139; c++) {
                if (rights[rr][c] && !looped[rr][c]) 
                    total += dfs(rr, c, looped); 
            }
        }

        br.close();
        return total + "";
    }

    public static int dfs(int x, int y, boolean[][] map) {
        if (map[x][y]) return 0;
        map[x][y] = true;

        int ret = 1;
        if (!map[x+1][y]) ret += dfs(x+1, y, map);
        if (!map[x-1][y]) ret += dfs(x-1, y, map);
        if (!map[x][y+1]) ret += dfs(x, y+1, map);
        if (!map[x][y-1]) ret += dfs(x, y-1, map);

        return ret;
    }
}