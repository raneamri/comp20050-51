import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

/**
 * The Flag class is a visual prop which indicates the exit point of a ray on
 * the board. This is made with the JavaFX <a
 * href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Polygon.html">Polygon
 * class</a>.
 */
public class Flag {
  private Polygon interactable;

  public Flag(Pair<Double, Double> coords) {
    interactable = new Polygon(coords.getKey(), coords.getValue());

    double x = coords.getKey();
    double y = coords.getValue();

    /*
     * Hard coded points to draw flag (comments at EOL to prevent intellisense
     * from collapsing the text)
     */
    interactable.getPoints().addAll(x, y - 15,      //
                                    x + 15, y - 15, //
                                    x + 5, y - 20,  //
                                    x + 15, y - 25, //
                                    x, y - 25,      //
                                    x, y            //
    );

    interactable.setFill(Color.BLUE);
    interactable.setStroke(Color.BLUEVIOLET);
    interactable.setStrokeWidth(2);
  }

  public Polygon getInteractable() { return this.interactable; }

  public void toggleOn() {
    interactable.setFill(Color.BLUE);
    interactable.setStroke(Color.BLUEVIOLET);
  }

  public void toggleOff() {
    interactable.setFill(Color.TRANSPARENT);
    interactable.setStroke(Color.TRANSPARENT);
  }
}
