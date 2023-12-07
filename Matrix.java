import java.io.*;
import java.util.Arrays;

public class Matrix {
    public static void main(String[] args) throws IOException {
        String fileA = "input.txt";
        String fileB = "input2.txt";

        Fraction[][] A = MatrixInversion.inputMatrixFromFile(fileA);
        Fraction[][] b = MatrixInversion.inputMatrixFromFile(fileB);

        Fraction[][] ATA = MatrixMultiplication.multiply(MatrixMultiplication.transpose(A), A);
        Fraction[][] ATb = MatrixMultiplication.multiply(MatrixMultiplication.transpose(A), b);

        Fraction[][] append = MatrixInversion.append(ATA, ATb);
        Fraction[][] rr = MatrixInversion.rowReduce(append);

        for (Fraction[] f : rr) {
            System.out.println(Arrays.toString(f));
        }

        String fileC = "input3.txt";
        Fraction[][] C = MatrixInversion.inputMatrixFromFile(fileC);
        for (Fraction[] f : MatrixMultiplication.multiply(C, C)) {
            System.out.println(Arrays.toString(f));
        }
    }
}
