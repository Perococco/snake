package perococco.snake.engine;

import lombok.NonNull;
import perococco.snake.core.SnakeGame;
import perococco.snake.core.SnakeGameFactory;

public class PerococcoSnakeGameFactory implements SnakeGameFactory {

    @Override
    public @NonNull SnakeGame create(int width, int height) {
        return new PerococcoSnakeGame(width, height);
    }
}
