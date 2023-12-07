public class Grudge extends GenericPlayer {
  private boolean play = false;
  private int count = 0;
  
  public Grudge() {
    super("Grudge");
  }

  public boolean play() {
    return play;
  }

  public void reset() {
    super.reset();
  }

  public void updateKnowledge(boolean memory, boolean enemy, double score) {
    if (enemy) {
      play = true;
      count = 3;
    }
    else {
      count--;
      if (count <= 0) play = false;
    }
    
    super.updateKnowledge(memory, enemy, score);
  }
}