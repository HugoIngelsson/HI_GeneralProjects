import java.util.*;
import java.io.*;

public class _3_2 {
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
        int[][] ints = new int[input.size()][input.get(0).length()];
        String num = "";
        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j);

                if (input.get(i).charAt(j) >= '0' && input.get(i).charAt(j) <= '9') {
                    num += input.get(i).charAt(j);
                } else if (num.length() > 0) {
                    for (int k=0; k<num.length(); k++) {
                        ints[i][j-k-1] = Integer.parseInt(num);
                    }
                    num = "";
                }
            }

            for (int k=0; k<num.length(); k++) {
                ints[i][map[0].length-k-1] = Integer.parseInt(num);
            }
            num = "";
        }

        for (int i=0; i<map.length; i++) {
            for (int j=0; j<map[0].length; j++) {
                if (map[i][j] == '*') {
                    HashSet<Integer> set = countUnique(i, j, ints);
                    if (set.size() == 2) {
                        int mult = 1;
                        for (Integer id : set) {
                            mult *= id;
                        }
                        total += mult;
                    }
                }
            }
        }

        pw.println(total);

        br.close();
        pw.close();
    }

    public static HashSet<Integer> countUnique(int r, int c, int[][] ints) {
        HashSet<Integer> add = new HashSet<Integer>();
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (r+i>=0&&r+i<ints.length&&c+j>=0&&c+j<ints[0].length) {
                    if (ints[r+i][c+j] > 0) add.add(ints[r+i][c+j]);
                }
            }
        }

        return add;
    }
}