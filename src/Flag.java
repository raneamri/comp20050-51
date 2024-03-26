import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;

public class Flag {
  private Polygon interactable;

  public Flag(Pair<Double, Double> coords) {
    interactable = new Polygon(coords.getKey(), coords.getValue());

    double x = coords.getKey();
    double y = coords.getValue();

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
}
