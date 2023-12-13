package Minesweeper;

//INPUT : A board state
//OUTPUT : A series of "clicks"
//TESTED ACCURACY : 0.896 (beginner difficulty)
//                  0.745 (intermediate difficulty)
//                  0.261 (expert difficulty)
import java.io.*;
import java.util.*;

class Player {
  private ArrayList<Click> clicks;
  private int[][] board;
  private int m, e;
  private int gameStage;

  public Player(int[][] board, int m) {
    this.board = board;
    this.m = m;
    gameStage = 0;
  }

  public int summarizeBoard() {
    e = 0;

    for (int i=0; i<board.length; i++) {
      for (int j=0; j<board[0].length; j++) {
        if (board[i][j] == -1) e++;
      }
    }

    return e;
  }

  public ArrayList<Click> determineClick() {
    clicks = new ArrayList<Click>();
    
    if (gameStage == 0) {
      if (board.length * board[0].length - summarizeBoard() < 4) {
        int i = board.length * board[0].length - e;
        switch (i) {
          case 0: clicks.add(new Click(0, 0, true)); break;
          case 1: clicks.add(new Click(board.length-1, 0, true)); break;
          case 2: clicks.add(new Click(board.length-1, board[0].length-1, true)); break;
          case 3: clicks.add(new Click(0, board[0].length-1, true)); break;
        }
      }
      else {
        gameStage = 1;
      }
    }

    if (gameStage == 1) {
      StateSolver logician = new StateSolver(m, board);
  
      while (logician.allOverlaps());
      while (logician.clearComplete());

      clicks = logician.getClicks();
      if (clicks.size() == 0) {
        StateSolver guesser = new StateSolver(m, board);

        while (guesser.allOverlaps());
        ArrayList<Zone> zones = guesser.getZones();
        double[][] probs = new double[board.length][board[0].length];
        int[][] zoneCount = new int[board.length][board[0].length];

        for (Zone z : zones) {
          for (Point p : z.points) {
            probs[p.x][p.y] += ((double)z.max / z.points.size());
            zoneCount[p.x][p.y]++;
          }
        }

        int minX = -1, minY = -1;
        double minProb = 1;
        for (int i=0; i<board.length; i++) {
          for (int j=0; j<board[i].length; j++) {
            if (zoneCount[i][j] != 0) {
              probs[i][j] /= zoneCount[i][j]+0.01;
              if (probs[i][j] < minProb) {
                minX = i;
                minY = j;
                minProb = probs[i][j];
              }
            }
          }
        }

        if (minX >= 0) {
          clicks.add(new Click(minX, minY, true));
        }
      }
    }

    return clicks;
  }
}