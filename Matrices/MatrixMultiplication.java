public class MatrixMultiplication {
    public static Fraction[][] multiply(Fraction[][] m1, Fraction[][] m2) {
        if (m1[0].length != m2.length) return null;

        Fraction[][] ret = new Fraction[m1.length][m2[0].length];

        for (int i=0; i<m1.length; i++) {
            for (int j=0; j<m2[0].length; j++) {
                Fraction put = new Fraction(0);

                for (int k=0; k<m1[0].length; k++) {
                    put = put.addFraction(m1[i][k].multiplyFraction(m2[k][j]), false);
                }

                ret[i][j] = put;
            }
        }

        return ret;
    }

    public static Fraction[][] transpose(Fraction[][] matrix) {
        Fraction[][] ret = new Fraction[matrix[0].length][matrix.length];

        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                ret[j][i] = matrix[i][j];
            }
        }

        return ret;
    }
}
