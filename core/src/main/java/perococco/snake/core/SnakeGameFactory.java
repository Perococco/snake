package perococco.snake.core;

import lombok.NonNull;

public interface SnakeGameFactory {

    @NonNull SnakeGame create(int width, int height);


}
