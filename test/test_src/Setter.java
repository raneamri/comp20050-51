package test_src;

import java.util.Random;

public class Setter {
  public Setter() {}

  /**
   * Places atoms on the board at random locations
   * (Outdated function used for bugfixing)
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
}
