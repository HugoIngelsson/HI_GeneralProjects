import java.util.*;

class Main {
  public static void main(String[] args) {
    List<GenericPlayer> players = new ArrayList<GenericPlayer>();
    List<Double> scores = new ArrayList<Double>();
    Simulator sim1 = new Simulator(100, 0, 50, 10, 50);

    players.add(new Aggressive());
    players.add(new Aggressive());
    players.add(new Sucker());
    players.add(new TitForTat());
    players.add(new TitForTat());
    players.add(new ForgivingTat());
    players.add(new ReverseForTat());
    players.add(new Anger());
    players.add(new Regret());
    players.add(new Grudge());
    players.add(new Grudge());
    players.add(new Grudge());
    for (int i=0; i<players.size(); i++) scores.add(0.0);

    double[] temp;
    for (int i=0; i<players.size()-1; i++) {
      for (int j=i+1; j<players.size(); j++) {
        temp = sim1.simulate(players.get(i), players.get(j));

        scores.set(i, scores.get(i)+temp[0]);
        scores.set(j, scores.get(j)+temp[1]);
      }
    }

    for (int i=0; i<players.size(); i++) {
      System.out.println(players.get(i).getName() + ": " + scores.get(i) / (players.size()-1));
    }
  }
}