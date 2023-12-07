public class Regret extends GenericPlayer {
  private boolean play = true;
  
  public Regret() {
    super("Regret");
  }

  public boolean play() {
    return play;
  }

  public void reset() {
    super.reset();
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    if (!enemy) play = false;
    super.updateKnowledge(memory, enemy, score);
  }
}