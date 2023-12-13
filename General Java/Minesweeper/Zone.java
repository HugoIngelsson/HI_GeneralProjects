package Minesweeper;

import java.util.ArrayList;

class Zone {
  int min;
  int max;
  ArrayList<Point> points;
  int id;
  int parent;

  public Zone(int min, int max, int id) {
    this.min = min;
    this.max = max;
    this.id = id;
    points = new ArrayList<Point>();
    parent = -1;
  }

  public void addPoint(Point p) {
    points.add(p);
  }

  public void removePoint(Point p) {
    for (int i=0; i<points.size(); i++) {
      if (points.get(i) == p) {
        points.remove(i);
        return;
      }
    }
  }

  public int isWithin(Zone z) {
    int ret = 0;

    for (Point p : z.points) if (points.contains(p)) ret++;

    return ret;
  }

  public void deltaMin(int delta) {
    min += delta;
  }

  public void deltaMax(int delta) {
    max += delta;
  }

  public String toString() {
    return id + ": (" + min + ", " + max + ") ... " + points.size();
  }

  public void setParent(int p) {
    parent = p;
  }
}