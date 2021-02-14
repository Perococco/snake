package perococco.snake.gui.game;

import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.Point;

@RequiredArgsConstructor
public class AppleDrawer {

    public static void draw(@NonNull DrawInfo drawInfo, @NonNull Point applePosition) {
        new AppleDrawer(drawInfo, applePosition).draw();
    }


    private final @NonNull DrawInfo drawInfo;
    private final @NonNull Point applePosition;

    private void draw() {
        final var g = drawInfo.getGraphicsContext();
        final var cellSize = drawInfo.getCellSize();
        g.setFill(Color.RED);
        g.fillOval(applePosition.getX() * cellSize, applePosition.getY() * cellSize, cellSize, cellSize);
    }
}
