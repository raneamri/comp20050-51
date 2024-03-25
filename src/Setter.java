import java.util.Random;

public class Setter {

  public Setter() {}

  public void placeAtoms() {
    Random rand = new Random();

    for (int i = 0; i < 6; i++) {
      int row = rand.nextInt(Board.NUM_ROWS);
      int col = rand.nextInt(Board.getNumHexagonsInRow(row));

      if (!Board.getCells()[row][col].addAtom()) {
        i--;
        continue;
      }
    }
  }
}
