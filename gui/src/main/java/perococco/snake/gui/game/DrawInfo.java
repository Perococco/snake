package perococco.snake.gui.game;

import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DrawInfo {

    private final @NonNull GraphicsContext graphicsContext;
    private final @NonNull double fieldWidth;
    private final @NonNull double fieldHeight;
    private final @NonNull double cellSize;

}
