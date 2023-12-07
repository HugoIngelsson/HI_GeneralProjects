public class GenericPlayer {
  private String name;
  private double score;
  private int rounds;
  private double avgScore;
  
  public GenericPlayer(String name) {
    this.name = name;
  }

  public boolean play() {
    return true;
  }

  public void reset() {
    score = 0;
    rounds = 0;
    avgScore = 0;
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    this.score += score;
    rounds++;
    avgScore = this.score / rounds;
  }

  public String getName() {
    return name;
  }

  public double getAvgScore() {
    return avgScore;
  }

  public String toString() {
    return name + ": " + getAvgScore();
  }
}