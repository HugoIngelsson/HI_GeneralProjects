class Main {
  public static void main(String[] args) {
    double[][] test = {{1,2,3,4},{3,-3,5,6},{6,10,8,9},{6,3,3,5}};
    System.out.println(determinant(test));
  }

  public static double determinant(double[][] matr) {
    if (matr.length == 2) {
      return (matr[0][0] * matr[1][1] - matr[0][1] * matr[1][0]);
    }
    else {
      int sum = 0;
      for (int i=0; i<matr.length; i++) {
        if (i%2 == 0) {
          sum -= matr[0][i] * determinant(withoutRowCol(matr, 0, i));
        }
        else {
          sum += matr[0][i] * determinant(withoutRowCol(matr, 0, i));
        }
      }
      return sum;
    }
  }

  public static double[][] withoutRowCol(double[][] matr, int r, int c) {
    double[][] ret = new double[matr.length - 1][matr.length - 1];
    int i = 0, j = 0;

    for (int k=0; k<matr.length; k++) {
      if (k != r) {
        j = 0;
        for (int l=0; l<matr.length; l++) {
          if (l != c) {
            ret[i][j] = matr[k][l];
            j++;
          }
        }
        i++;
      }
    }
    
    return ret;
  }
}