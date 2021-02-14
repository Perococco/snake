package perococco.snake.gui.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BorderDrawer  {

    public static void draw(@NonNull DrawInfo drawInfo) {
        new BorderDrawer(drawInfo).draw();
    }


    private final @NonNull DrawInfo drawInfo;

    private void draw() {
        final var g = drawInfo.getGraphicsContext();
        final var cellSize = drawInfo.getCellSize();
        final var fieldWidth = drawInfo.getFieldWidth();
        final var fieldHeight = drawInfo.getFieldHeight();
        g.setFill(Color.BLACK);
        g.fillRect(0,0,cellSize*(fieldWidth+2),cellSize);
        g.fillRect(0,0,cellSize, cellSize*(fieldHeight+2));
        g.fillRect(0,cellSize*(fieldHeight+1),cellSize*(fieldWidth+2),cellSize);
        g.fillRect(cellSize*(fieldWidth+1),0,cellSize, cellSize*(fieldHeight+2));

    }
}
