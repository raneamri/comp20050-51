package blackbox;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
  private int torchCreator;
  private final Label label;

  public Flag(Pair<Double, Double> coords, int torchCreator) {
    this.torchCreator = torchCreator;
    interactable = new Polygon(coords.getKey(), coords.getValue());
    label = new Label("Torch " + this.torchCreator);
    label.setLabelFor(interactable);

    double x = coords.getKey();
    double y = coords.getValue();
    label.setLayoutX(x);
    label.setLayoutY(y);

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
    interactable.setStrokeWidth(1);

    BorderStroke borderStroke =
        new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID,
                         new CornerRadii(30), new BorderWidths(2));

    Border border = new Border(borderStroke);
    label.setBorder(border);
    label.setStyle("-fx-text-fill: blueviolet");

    /*
     * Mouse events
     */
    interactable.addEventHandler(MouseEvent.MOUSE_ENTERED,
                                 new EventHandler<MouseEvent>() {
                                   @Override
                                   public void handle(MouseEvent e) {
                                     Main.getGroup().getChildren().add(label);
                                   }
                                 });

    interactable.addEventHandler(
        MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            Main.getGroup().getChildren().remove(label);
          }
        });
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
