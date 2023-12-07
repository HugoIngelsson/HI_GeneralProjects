public class ForgivingTat extends GenericPlayer {
  public boolean lastPlay;
  
  public ForgivingTat() {
    super("ForgivingTat");
    lastPlay = false;
  }

  public boolean play() {
    if (Math.random() < 0.10) return true;
    return lastPlay;
  }

  public void reset() {
    super.reset();
    lastPlay = false;
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    super.updateKnowledge(memory, enemy, score);
    lastPlay = enemy;
  }
}