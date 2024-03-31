import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Board {
  /**
   * Dimensions as constants
   */
  private static final int HEXAGON_SIZE = 40;
  private static final int NUM_ROWS = 9;

  /**
   * Matrix to hold cell positions
   */
  private static Cell[][] cells;

  public Board() {
    cells = new Cell[NUM_ROWS][];

    for (int row = 0; row < NUM_ROWS; row++) {
      cells[row] = new Cell[getNumHexagonsInRow(row)];

      for (int col = 0; col < getNumHexagonsInRow(row); col++) {
        cells[row][col] = new Cell(row, col);
      }
    }
  }

  public void getBoardGroup() {
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < cells[row].length; col++) {
        Cell cell = cells[row][col];

        if (cell != null) {
          double startX = getStartXForRow(row);
          double yOffset = HEXAGON_SIZE * (Math.sqrt(3f) / 2);
          double x = startX + col * HEXAGON_SIZE * 1.73;
          double y = row * yOffset * 1.73;

          Polygon hexagon = cell.createHexagon(HEXAGON_SIZE);
          Text numText =
              new Text(Integer.toString(row) + " " + Integer.toString(col));
          numText.setStroke(Color.color(0.15, 0.15, 0.15));
          numText.setLayoutX(x);
          numText.setLayoutY(y);
          Main.getGroup().getChildren().add(numText);

          hexagon.setLayoutX(x);
          hexagon.setLayoutY(y);
          Main.getGroup().getChildren().add(hexagon);

          /**
           * Passing in the array position of the hexagon point needed to
           * draw triangle of torch
           */
          Torch t;
          if (row == 0) {
            for (int i = 0; i < 2; i++) {
              t = new Torch(cell, (i == 0) ? 6 : 8);
              cell.addTorch(t);
              Main.getGroup().getChildren().add(t.getInteractable());
              Main.torchs.add(t);
            }
          }
          if (row == NUM_ROWS - 1) {
            for (int i = 0; i < 2; i++) {
              t = new Torch(cell, (i == 0) ? 0 : 2);
              cell.addTorch(t);
              Main.getGroup().getChildren().add(t.getInteractable());
              Main.torchs.add(t);
            }
          }
          if (col == 0) {
            if (row <= NUM_ROWS / 2) {
              for (int i = 0; i < 2; i++) {
                t = new Torch(cell, (i == 0) ? 4 : 6);
                cell.addTorch(t);
                Main.getGroup().getChildren().add(t.getInteractable());
                Main.torchs.add(t);
              }
            }
            if (row >= NUM_ROWS / 2) {
              for (int i = 0; i < 2; i++) {
                t = new Torch(cell, (i == 0) ? 2 : 4);
                cell.addTorch(t);
                Main.getGroup().getChildren().add(t.getInteractable());
                Main.torchs.add(t);
              }
            }
          }
          if (col == getNumHexagonsInRow(row) - 1) {
            if (row <= NUM_ROWS / 2) {
              for (int i = 0; i < 2; i++) {
                t = new Torch(cell, (i == 0) ? 8 : 10);
                cell.addTorch(t);
                Main.getGroup().getChildren().add(t.getInteractable());
                Main.torchs.add(t);
              }
            }
            if (row >= NUM_ROWS / 2) {
              for (int i = 0; i < 2; i++) {
                t = new Torch(cell, (i == 0) ? 10 : 0);
                cell.addTorch(t);
                Main.getGroup().getChildren().add(t.getInteractable());
                Main.torchs.add(t);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Mechanism to give the board the hexagonal shape we need
   *
   * @param row used to perform maths to figure width of current row
   * @return
   */
  public static int getNumHexagonsInRow(int row) {
    if (row < 4) {
      return row + 5;
    } else {
      return 13 - row;
    }
  }

  /**
   * Uses geometry to pinpoint where next row should start to have all cells
   * perfectly tangential
   *
   * @param row
   * @return
   */
  private double getStartXForRow(int row) {
    if (row < 4) {
      return (4 - row) * HEXAGON_SIZE * 1.73 / 2;
    } else {
      return (row - 4) * HEXAGON_SIZE * 1.73 / 2;
    }
  }

  /**
   * Checks if an index pair [i][j] exists in Board and is a populated entry
   *
   * @param row
   * @param col
   * @return true if the entry exists, false otherwise
   */
  public static boolean isInBoard(int row, int col) {
    return !(row < 0 || col < 0 || col >= Board.getNumHexagonsInRow(row) ||
             row > 8);
  }

  public static Cell[][] getCells() { return cells; }
}
