public class TitForTat extends GenericPlayer {
  public boolean lastPlay;
  
  public TitForTat() {
    super("Tit for Tat");
    lastPlay = false;
  }

  public boolean play() {
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