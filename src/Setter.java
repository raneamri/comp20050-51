import java.util.Random;

public class Setter {
  private int score = 0;

  public Setter() {}

  /**
   * Places atoms on the board at random locations
   */
  public void placeAtoms() {
    Random rand = new Random();

    for (int i = 0; i < 6; i++) {
      int row = rand.nextInt(9);
      int col = rand.nextInt(Board.getNumHexagonsInRow(row));

      if (!Board.getCells()[row][col].addAtom()) {
        i--;
        continue;
      }
    }
  }

  public void setScore(int s) { this.score = s; }

  public int getScore() { return this.score; }
}
