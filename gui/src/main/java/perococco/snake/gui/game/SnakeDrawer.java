package perococco.snake.gui.game;

import lombok.NonNull;
import perococco.snake.core.GameView;

public interface SnakeDrawer {

    @NonNull  String getName();

    void draw(@NonNull DrawInfo drawInfo, @NonNull GameView gameView);
}
