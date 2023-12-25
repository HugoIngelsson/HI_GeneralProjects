import java.io.*;

public class _1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        String in;

        int total = 0;
        while ((in = br.readLine()) != null && in.length() > 0) {
            int a=-1, b=-1;
            int idA = Integer.MAX_VALUE, idB = -1;

            for (int i=0; i<in.length(); i++) {
                if (in.charAt(i) >= '0' && in.charAt(i) <= '9') {
                    if (a == -1) {
                        a = 10 * (in.charAt(i)-'0');
                        idA = i;
                    }
                    else {
                        b = (in.charAt(i)-'0');
                        idB = i;
                    }
                }
            }
            if (b == -1 && a != -1) {
                b = a/10;
                idB = idA;
            }

            int[][] ids = getIDs(in);
            for (int i=0; i<9; i++) {
                if (idA > ids[0][i]-1 && ids[0][i] != 0) {
                    idA = ids[0][i]-1;
                    a = 10 * (i+1);
                }
                if (idB < ids[1][i]-1 && ids[1][i] != 0) {
                    idB = ids[1][i]-1;
                    b = (i+1);
                }
            }
            
            total += a + b;
        }

        pw.println(total);

        br.close();
        pw.close();
    }

    public static int[][] getIDs(String word) {
        int[][] id = new int[2][9];
        String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        for (int i=0; i<9; i++) {
            id[0][i] = word.indexOf(words[i])+1;
            id[1][i] = word.lastIndexOf(words[i])+1;
        }

        return id;
    }
}