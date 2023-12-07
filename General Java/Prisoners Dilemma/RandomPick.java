public class RandomPick extends GenericPlayer {
  public RandomPick() {
    super("Random");
  }

  public boolean play() {
    return Math.random() < 0.5;
  }

  public void reset() {
    super.reset();
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    super.updateKnowledge(memory, enemy, score);
  }
}