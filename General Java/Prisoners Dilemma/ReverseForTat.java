public class ReverseForTat extends GenericPlayer {
  public boolean lastPlay;
  
  public ReverseForTat() {
    super("Reverse for Tat");
    lastPlay = true;
  }

  public boolean play() {
    return lastPlay;
  }

  public void reset() {
    super.reset();
    lastPlay = true;
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    super.updateKnowledge(memory, enemy, score);
    lastPlay = enemy;
  }
}