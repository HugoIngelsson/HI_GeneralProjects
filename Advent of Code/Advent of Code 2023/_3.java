import java.util.*;
import java.io.*;

public class _3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        PrintWriter pw = new PrintWriter(System.out);
        String in;

        ArrayList<String> input = new ArrayList<String>();
        int total = 0;
        while ((in = br.readLine()) != null) {
            input.add(in);
        }

        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        } 

        for (int i=0; i<map.length; i++) {
            boolean run = false;
            String num = "";

            for (int j=0; j<map[0].length; j++) {
                if (map[i][j] >= '0' && map[i][j] <= '9') {
                    if (num.length() == 0) {
                        if (j > 0) run |= checkCol(i, j-1, map);
                    }
                    run |= checkCol(i, j, map);
                    num += map[i][j];
                }
                else if (num.length() > 0) {
                    if (j < map[0].length-1) run |= checkCol(i, j, map);
                    if (run) total += Integer.parseInt(num);
                    num = "";
                    run = false;
                }
            }

            if (run) total += Integer.parseInt(num);
        }


        pw.println(total);


        br.close();
        pw.close();
    }

    public static boolean checkCol(int r, int c, char[][] map) {
        return (r>0&&object(map[r-1][c])) || 
                (object(map[r][c])) || 
                (r<map.length-1&&object(map[r+1][c]));
    }

    public static boolean object(char c) {
        return c != '.' && (c < '0' || c > '9');
    }
}