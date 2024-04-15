import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The Board class generates all the cells for the game using maths. The board
 * created by this class is then turned into a JavaFX <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Group.html">Group
 * class</a> for rendering purposes.
 */
public class Board {
  /**
   * Dimensions as constants
   */
  private static final int HEXAGON_SIZE = 38;
  private static final int NUM_ROWS = 9;

  /**
   * Matrix to hold cell positions
   */
  private static Cell[][] cells;

  public Board() {
    cells = new Cell[NUM_ROWS][];

    /**
     * Fill cells matrix and assigns each cell its index
     */
    for (int row = 0; row < NUM_ROWS; row++) {
      cells[row] = new Cell[getNumHexagonsInRow(row)];

      for (int col = 0; col < getNumHexagonsInRow(row); col++) {
        cells[row][col] = new Cell(row, col);
      }
    }
  }

  public Group getBoardGroup() {
    Group group = new Group();
    int lowerTorchNo = 1;
    int upperTorchNo = 54;
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int col = 0; col < cells[row].length; col++) {
        Cell cell = cells[row][col];

        if (cell != null) {
          double startX = getStartXForRow(row);
          double yOffset = HEXAGON_SIZE * (Math.sqrt(3f) / 2);
          double x = startX + col * HEXAGON_SIZE * 1.85;
          double y = row * yOffset * 1.85;

          Polygon hexagon = cell.createHexagon(HEXAGON_SIZE);

          hexagon.setLayoutX(x);
          hexagon.setLayoutY(y);
          group.getChildren().add(hexagon);

          /**
           * Passing in the array position of the hexagon point needed to
           * draw triangle of torch
           */
          int[] cellTorches = null;

          if (row == 0) {
            cellTorches = new int[] {6, 8, -1};
          } else if (row == NUM_ROWS - 1) {
            cellTorches = new int[] {2, 0, -1};
          } else if (col == 0) {
            cellTorches = (row <= NUM_ROWS / 2) ? new int[] {6, 4, -1}
                                                : new int[] {4, 2, -1};
          } else if (col == getNumHexagonsInRow(row) - 1) {
            cellTorches = (row <= NUM_ROWS / 2) ? new int[] {8, 10, -1}
                                                : new int[] {10, 0, -1};
          }

          if (col == 0 && row == 0) {
            cellTorches[1] = 4;
            cellTorches[2] = 8;
            for (int i = 0; i < 3; i++) {
              torchVisuals(group, cell, cellTorches[i],
                           (i < 2) ? lowerTorchNo++ : upperTorchNo--);
            }
            continue;
          }

          else if (col == 0 && row == NUM_ROWS - 1)
            torchVisuals(group, cell, 4, lowerTorchNo++);

          else if (col == getNumHexagonsInRow(row) - 1 &&
                   (row == 0 || row == NUM_ROWS - 1))
            cellTorches[2] = 10;

          else if (col == 0 && row == NUM_ROWS / 2)
            cellTorches[2] = 2;

          else if (col == getNumHexagonsInRow(row) - 1 && row == NUM_ROWS / 2) {
            assert cellTorches != null;
            cellTorches[2] = 0;
          }

          if (cellTorches != null) {
            for (int i = 0; i < cellTorches.length && cellTorches[i] != -1; i++)
              torchVisuals(group, cell, cellTorches[i],
                           (col == 0 || row == NUM_ROWS - 1) ? lowerTorchNo++
                                                             : upperTorchNo--);
          }
        }
      }
    }

    return group;
  }

  /**
   * Mechanism to give the board the hexagonal shape we need
   *
   * @param row used to perform maths to figure width of current row
   * @return corresponding number of hexagons in row
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
   * @return start position for first hexagon in given row
   */
  private double getStartXForRow(int row) {
    if (row < 4) {
      return (4 - row) * HEXAGON_SIZE * 1.85 / 2;
    } else {
      return (row - 4) * HEXAGON_SIZE * 1.85 / 2;
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

  /**
   * Creates the board visuals for the torches
   *
   * @param group the board group
   * @param cell the cell the torch is being made in
   * @param hexPoint the index needed for the hexagon point array
   *                 to draw torch
   * @param number the number of the torch corresponding to board layout
   */
  private void torchVisuals(Group group, Cell cell, int hexPoint, int number) {

    Torch t = new Torch(cell, hexPoint, number);
    cell.addTorch(t);
    group.getChildren().add(t.getInteractable());
    Main.torchs.add(t);

    Text text = new Text("" + t.getNumber());
    text.setFill(Color.GRAY);
    text.setStroke(Color.BLACK);
    text.setStrokeWidth(1.3);
    text.setFont(Font.font("Arial", 20));
    text.setMouseTransparent(true);
    text.setStyle("-fx-font-weight: bold");

    if (cell.getCol() == 0)
      text.setLayoutX(t.getMainMidpoint()[0] - 10);
    else if (cell.getRow() > (NUM_ROWS) / 2 && cell.getCol() == 0)
      text.setLayoutX(t.getMainMidpoint()[0] - 20);
    else
      text.setLayoutX(t.getMainMidpoint()[0] - 1);

    if (cell.getRow() == NUM_ROWS - 1)
      text.setLayoutY(t.getMainMidpoint()[1] + 10);
    else
      text.setLayoutY(t.getMainMidpoint()[1]);

    group.getChildren().add(text);
  }
}
