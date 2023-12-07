public class Sucker extends GenericPlayer {
  public Sucker() {
    super("Sucker");
  }

  public boolean play() {
    return false;
  }

  public void reset() {
    super.reset();
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    super.updateKnowledge(memory, enemy, score);
  }
}