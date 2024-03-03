import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class Ray extends Line {
    private ArrayList<Pair<Double, Double>> coords = new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();

    public Ray(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
        setFill(Color.WHITE);
        setStroke(Color.LIGHTCYAN);
        setStrokeWidth(4);
        coords.add(new Pair(startX, startY));
    }

    public Boolean checkCollision() {
        for (Atom atom : Main.atoms) {
        }

        return false;
    }

    public void drawRays() {
        for (int i = 0; i < coords.size(); i++) {
            lines.add(new Line(coords.get(i).getKey(), coords.get(i).getValue(),
                    coords.get(i + 1).getKey(),
                    coords.get(i + 1).getValue()));
        }
    }
}
