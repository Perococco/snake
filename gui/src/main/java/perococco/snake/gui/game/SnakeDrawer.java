package perococco.snake.gui.game;

import lombok.NonNull;
import perococco.snake.core.Snake;

public interface SnakeDrawer {

    @NonNull  String getName();

    void draw(@NonNull DrawInfo drawInfo, @NonNull Snake snakeGame);
}
