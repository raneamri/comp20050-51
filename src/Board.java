import javafx.scene.shape.Polygon;

public class Board {
  /**
   * Proportions as constants
   */
  private static final int HEXAGON_SIZE = 40;
  private static final int NUM_ROWS = 9;

  /**
   * Matrix to hold cell positions
   */
  private Cell[][] cells;

  public Board() { createBoard(); }

  private void createBoard() {
    cells = new Cell[NUM_ROWS][];
    for (int row = 0; row < NUM_ROWS; row++) {
      int numHexagons = getNumHexagonsInRow(row);
      cells[row] = new Cell[numHexagons];
      for (int col = 0; col < numHexagons; col++) {
        cells[row][col] = new Cell();
        cells[row][col].coords[0] = row;
        cells[row][col].coords[1] = col;
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
          hexagon.setLayoutX(x);
          hexagon.setLayoutY(y);
          Main.getGroup().getChildren().add(hexagon);
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
  public int getNumHexagonsInRow(int row) {
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
  public Cell[][] getCells(){
    return cells;
  }

}
