package Minesweeper;

import java.io.*;
import java.util.*;

class StateSolver {
  private int r, c, m, e;
  private int[][] board;
  private Point[][] points;
  private List[][] zones;
  private int newID = 1000000;
  private ArrayList<Zone> easyReference;
  private ArrayList<Click> clicks;

  public StateSolver(int m, int[][] board) {
    this.r = board.length;
    this.c = board[0].length;
    this.m = m;
    this.board = copyBoard(board);
    e = 0;
    points = new Point[r][c];
    zones = new List[r][c];
    easyReference = new ArrayList<Zone>();
    clicks = new ArrayList<Click>();
    
    setup();
  }

  public int[][] copyBoard(int[][] board) {
    int[][] ret = new int[board.length][board[0].length];

    for (int i=0; i<ret.length; i++) 
      for (int j=0; j<ret[0].length; j++)
        ret[i][j] = board[i][j];

    return ret;
  }

  public ArrayList<Click> getClicks() {
    return clicks;
  }
  
  public boolean clearComplete() {
    boolean change = false;
    
    for (int i=0; i<easyReference.size(); i++) {
      Zone z = easyReference.get(i);

      if (z.max == 0) {
        //clear all points
        for (Point p : z.points) {
          ArrayList<Zone> thisSpot = (ArrayList<Zone>)zones[p.x][p.y];
          clicks.add(new Click(p.x, p.y, true));
          board[p.x][p.y] = -5;

          for (Zone subZone : thisSpot) {
            if (z != subZone) subZone.removePoint(p);
          }
          thisSpot = new ArrayList<Zone>();
        }

        easyReference.remove(i--);
        change = true;
      }
      else if (z.min >= z.points.size()) {
        //put mines on all points
        for (Point p : z.points) {
          ArrayList<Zone> thisSpot = (ArrayList<Zone>)zones[p.x][p.y];
          clicks.add(new Click(p.x, p.y, false));
          board[p.x][p.y] = -99;

          for (Zone subZone : thisSpot) {
            if (z != subZone) {
              subZone.removePoint(p);
              subZone.max--;
              subZone.min = Math.max(subZone.min-1, 0);
            }
          }
          thisSpot = new ArrayList<Zone>();
        }

        easyReference.remove(i--);
        change = true;
      }
    }

    return change;
  }

  public boolean allOverlaps() {
    boolean change = false;

    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        if (zones[i][j].size() > 0) {
          if (seeOverlaps(i,j)) change = true;
        }
      }
    }

    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        if (zones[i][j].size() > 0) {
          if (seePartialOverlaps(i,j)) change = true;
        }
      }
    }

    return change;
  }

  public boolean overlapsNoPartials() {
    boolean change = false;

    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        if (zones[i][j].size() > 0) {
          if (seeOverlaps(i,j)) change = true;
        }
      }
    }

    return change;
  }

  public boolean seeOverlaps(int x, int y) {
    ArrayList<Zone> thisSpot = (ArrayList<Zone>)zones[x][y];
    boolean change = false;

    if (thisSpot.size() > 1) {
      for (int i=0; i<thisSpot.size()-1; i++) {
        loop:
        for (int j=i+1; j<thisSpot.size(); j++) {
          int relate = thisSpot.get(i).isWithin(thisSpot.get(j));

          if (relate == thisSpot.get(j).points.size() && thisSpot.get(i).points.size() != 1) {
            // split up the bigger zone
            splitZone(thisSpot.get(i), thisSpot.get(j));
            change = true;
            i--;
            break loop;
          } else if (relate == thisSpot.get(i).points.size() && thisSpot.get(j).points.size() != 1) {
            // split up the bigger zone
            splitZone(thisSpot.get(j), thisSpot.get(i));
            change = true;
            j--;
          }
        }
      }
    }

    return change;
  }

  public boolean seePartialOverlaps(int x, int y) {
    ArrayList<Zone> thisSpot = (ArrayList<Zone>)zones[x][y];
    boolean change = false;

    if (thisSpot.size() > 1) {
      int hardstop = thisSpot.size();
      for (int i=0; i<hardstop-1; i++) {
        loop:
        for (int j=i+1; j<hardstop; j++) { 
          int relate = thisSpot.get(i).isWithin(thisSpot.get(j));

          if (thisSpot.get(i).max != thisSpot.get(j).max && relate != thisSpot.get(i).points.size() && relate != thisSpot.get(j).points.size() && thisSpot.get(i).id != thisSpot.get(j).parent && thisSpot.get(j).id != thisSpot.get(i).parent) {
            if (thisSpot.get(i).max > thisSpot.get(j).max) {
              splitNonOverlap(thisSpot.get(i), thisSpot.get(j), relate);
              // i--;
              // break loop;
            }
            else {
              splitNonOverlap(thisSpot.get(j), thisSpot.get(i), relate);
              // j--;
            }
            change = true;
          }
        }
      }
    }

    return change;
  }

  public void remove(Zone z) {
    for (Point p : z.points) {
      ArrayList<Zone> copyZone = (ArrayList<Zone>)zones[p.x][p.y];

      copyZone.remove(copyZone.indexOf(z));
    }

    easyReference.remove(easyReference.indexOf(z));
  }

  // PRECONDITIONS: z1 is a zone with a greater number of mines
  // than z2 and z1/z2 partially overlap, neither being fully
  // contained by the other
  public void splitNonOverlap(Zone z1, Zone z2, int over) {
    int min3 = Math.max(z1.min - z1.points.size() + over, z2.min - z2.points.size() + over);
    Zone z3 = new Zone(Math.max(min3, 0), Math.min(z1.max, z2.max), newID++);
    z3.setParent(z1.id);

    int min4 = z1.min - Math.min(over, z2.max);
    Zone z4 = new Zone(Math.max(min4, 0), z1.max, newID++);
    z4.setParent(z1.id);

    for (Point p : z1.points) {
      ArrayList<Zone> copyZone = (ArrayList<Zone>)zones[p.x][p.y];
      if (!copyZone.contains(z2)) {
        copyZone.add(z4);
        z4.addPoint(p);
      }
      else {
        copyZone.add(z3);
        z3.addPoint(p);
      }
      //copyZone.remove(copyZone.indexOf(z1));
    }

    //easyReference.remove(easyReference.indexOf(z1));
    if (z3.points.size() > 0) easyReference.add(z3);
    if (z4.points.size() > 0) easyReference.add(z4);
  }
  
  // PRECONDITIONS: z1 is a zone that is bigger than z2
  // and fully contains z2
  public void splitZone(Zone z1, Zone z2) {
    Zone z3 = new Zone(Math.max(z1.min - z2.max, 0), z1.max - z2.min, newID++);

    for (Point p : z1.points) {
      ArrayList<Zone> copyZone = (ArrayList<Zone>)zones[p.x][p.y];
      if (!copyZone.contains(z2)) {
        copyZone.add(z3);
        z3.addPoint(p);
      }
      copyZone.remove(copyZone.indexOf(z1));
    }
    
    easyReference.remove(easyReference.indexOf(z1));
    if (z3.points.size() > 0) easyReference.add(z3);
  }

  public void setup() {
    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        points[i][j] = new Point(i,j);
        zones[i][j] = new ArrayList<Zone>();
      }
    }

    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        if (board[i][j] > 0) {
          Zone z = new Zone(board[i][j], board[i][j], i*c+j);
          easyReference.add(z);

          for (int di=-1; di<=1; di++) {
            for (int dj=-1; dj<=1; dj++) {
              if (i+di >= 0 && i+di < r && j+dj >= 0 && j+dj < c) {
                if (board[i+di][j+dj] == -1) {
                  z.addPoint(points[i+di][j+dj]);
                  zones[i+di][j+dj].add(z);
                }
                else if (board[i+di][j+dj] == -99) {
                  z.deltaMax(-1);
                  z.deltaMin(-1);
                }
              }
            }
          }
        }
      }
    }
  }

  public int[][] getBoard() {
    return board;
  }

  public ArrayList<Zone> getZones() {
    return easyReference;
  }
}