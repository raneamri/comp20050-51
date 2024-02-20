import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cell {
  int[] coords = new int[] {0, 0};
  private static final double ROTATION_ANGLE = Math.PI / 6.0;

  public Cell() {}

  public Polygon createHexagon(double size) {
    Polygon hexagon = new Polygon();

    for (int i = 0; i < 6; i++) {
      double angle = ROTATION_ANGLE + (2.0 * Math.PI / 6 * i);
      double x = size * Math.cos(angle);
      double y = size * Math.sin(angle);
      hexagon.getPoints().addAll(x, y);
    }

    // hexagon.setFill(Color.rgb(15, 15, 15));
    hexagon.setFill(Color.TRANSPARENT);
    hexagon.setStroke(Color.RED);

    hexagon.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        double centerX = hexagon.getBoundsInParent().getCenterX();
        double centerY = hexagon.getBoundsInParent().getCenterY();
        Atom atom = new Atom(centerX, centerY);
        atom.coi = new COI(centerX, centerY);
        System.out.println("hexagon enter coordinates: (" + centerX + ", " +
                           centerY + ")");

        if (Main.atoms.size() == Main.MAX_ATOMS) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return;
        } else if (Main.atoms.contains(atom)) {
          System.out.println("atom exists, event blocked");
          return;
        } else {
          Main.getGroup().getChildren().add(atom);
          Main.getGroup().getChildren().add(atom.coi);
          Main.atoms.add(atom);
        }
      }
    });

    return hexagon;
  }
}
