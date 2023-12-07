import java.util.*;

public class Simulator {
  private double greed;
  private double fool;
  private double share;
  private double punish;
  private int rounds;

  public Simulator() {
    greed = 100;
    fool = 0;
    share = 70;
    punish = 20;
    rounds = 20;
  }
  
  public Simulator(double g, double f, double s, double p, int r) {
    greed = g;
    fool = f;
    share = s;
    punish = p;
    rounds = r;
  }

  public double[] simulate(GenericPlayer p1, GenericPlayer p2) {
    p1.reset();
    p2.reset();
    
    for (int i=0; i<rounds; i++) {
      simulateRound(p1, p2);
    }

    double[] ret = {p1.getAvgScore(), p2.getAvgScore()};
    return ret;
  }

  public void simulateRound(GenericPlayer p1, GenericPlayer p2) {
    boolean r1 = p1.play();
    boolean r2 = p2.play();

    if (Math.random() < 0.02) r1 = !r1;
    if (Math.random() < 0.02) r2 = !r2;
    
    if (r1 && r2) {
      p1.updateKnowledge(r1, r2, punish);
      p2.updateKnowledge(r2, r1, punish);
    }
    else if (r1 && !r2) {
      p1.updateKnowledge(r1, r2, greed);
      p2.updateKnowledge(r2, r1, fool);
    }
    else if (!r1 && r2) {
      p1.updateKnowledge(r1, r2, fool);
      p2.updateKnowledge(r2, r1, greed);
    }
    else {
      p1.updateKnowledge(r1, r2, share);
      p2.updateKnowledge(r2, r1, share);
    }
  }
}