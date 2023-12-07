public class Aggressive extends GenericPlayer {
  public Aggressive() {
    super("Aggressive");
  }

  public boolean play() {
    return true;
  }

  public void reset() {
    super.reset();
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    super.updateKnowledge(memory, enemy, score);
  }
}