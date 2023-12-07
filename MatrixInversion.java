import java.io.*;
import java.util.*;

public class MatrixInversion {
    static Fraction[][] matrix;

    public static void main(String[] args) throws IOException {
        int n = 8;
        Fraction[][] test = generateFraction(n, n);
        // Fraction[][] test = inputMatrixFromFile("input.txt");
        // Fraction[][] ATA = MatrixMultiplication.multiply(MatrixMultiplication.transpose(test), test);
        // test = appendIdentity(test);
        for (Fraction[] f : test) {
            System.out.println(Arrays.toString(f));
        }

        System.out.println();

        // for (Fraction[] f : ATA) {
        //     System.out.println(Arrays.toString(f));
        // }

        // System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("Press enter when ready to see inverse... ");
        sc.nextLine();
        sc.close();

        Fraction[][] appended = appendIdentity(test);
        Fraction[][] rowReduced = rowReduce(appended);
        for (Fraction[] f : extractCols(rowReduced, n, 2*n)) {
            System.out.println(Arrays.toString(f));
        }
    }

    public static Fraction[][] generateFraction(int rows, int cols) {
        Fraction[][] ret = new Fraction[rows][cols];

        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                ret[i][j] = new Fraction((long)(Math.random()*6));
                if (Math.random() < 0.5) ret[i][j] = ret[i][j].multiplyFraction(new Fraction(-1));
            }
        }

        return ret;
    }

    public static Fraction[][] extractCols(Fraction[][] matrix, int start, int end) {
        Fraction[][] ret = new Fraction[matrix.length][end-start];

        for (int i=0; i<matrix.length; i++) for (int j=0; j<end-start; j++) {
            ret[i][j] = matrix[i][start+j];
        }

        return ret;
    }

    public static Fraction[][] appendIdentity(Fraction[][] matrix) {
        Fraction[][] ret = new Fraction[matrix.length][matrix[0].length+matrix.length];

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                ret[i][j] = matrix[i][j].clone();
            }

            for (int j=matrix[0].length; j<matrix[0].length+matrix.length; j++) {
                if (i == j-matrix[0].length) ret[i][j] = new Fraction(1);
                else ret[i][j] = new Fraction(0);
            }
        }

        return ret;
    }

    public static Fraction[][] append(Fraction[][] m1, Fraction[][] m2) {
        if (m1.length != m2.length) return null;
        Fraction[][] ret = new Fraction[m1.length][m1[0].length + m2[0].length];

        for (int i=0; i<m1.length; i++) {
            for (int j=0; j<m1[0].length; j++) {
                ret[i][j] = m1[i][j];
            }

            for (int j=0; j<m2[0].length; j++) {
                ret[i][j+m1[0].length] = m2[i][j];
            }
        }

        return ret;
    }

    public static Fraction[][] rowReduce(Fraction[][] matrix) {
        Fraction[][] reduced = matrix.clone();

        int pivotalRow = 0;
        for (int col=0; col<reduced[0].length; col++) {
            for (int row=pivotalRow; row<reduced.length; row++) {
                if (!reduced[row][col].isZero()) {
                    Fraction inverse = Fraction.inverseFraction(reduced[row][col]);
                    reduced[row] = multiplyRow(reduced[row], inverse);
                    for (int r=0; r<reduced.length; r++) if (r != row) reduced[r] = subtractRow(reduced[r], multiplyRow(reduced[row].clone(), reduced[r][col]));

                    swapRows(reduced, row, pivotalRow);
                    pivotalRow++;
                    break;
                }
            }
        }

        return reduced;
    }

    /* 
     * Overload to allow for using the static fraction matrix
    */
    public static Fraction[][] rowReduce() {
        return rowReduce(MatrixInversion.matrix);
    }

    /*
     * Returns whether a square matrix has been inverted
     * (i.e. its first columns are the identity matrix)
    */
    public static boolean isInverted(Fraction[][] matrix) {
        if (matrix.length > matrix[0].length) return false;

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                if (i == j && !matrix[i][j].isOne()) return false;
                if (i != j && !matrix[i][j].isZero()) return false;
            }
        }

        return true;
    }

    public static void swapRows(Object[][] obj, int r1, int r2) {
        Object[] keepRow = obj[r1];
        obj[r1] = obj[r2];
        obj[r2] = keepRow;
    }

    public static Fraction[] multiplyRow(Fraction[] row, Fraction multiply) {
        Fraction[] ret = new Fraction[row.length];
        
        for (int col=0; col<row.length; col++) {
            ret[col] = row[col].multiplyFraction(multiply);
        }

        return ret;
    }

    public static Fraction[] subtractRow(Fraction[] row, Fraction[] add) {
        Fraction[] ret = new Fraction[row.length];

        for (int col=0; col<row.length; col++) {
            ret[col] = row[col].addFraction(add[col], true);
        }

        return ret;
    }

    public static Fraction[][] inputMatrixFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int rows = Integer.parseInt(st.nextToken());
        int cols = Integer.parseInt(st.nextToken());
        Fraction[][] ret = new Fraction[rows][cols];

        for (int i=0; i<rows; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<cols; j++) {
                ret[i][j] = new Fraction(Long.parseLong(st.nextToken()));
            }
        }

        br.close();
        return ret;
    }
}