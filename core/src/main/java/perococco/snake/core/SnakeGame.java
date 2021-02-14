package perococco.snake.core;

import lombok.NonNull;

import java.util.Optional;
import java.util.ServiceLoader;

public interface SnakeGame {

    @NonNull GameState getState();

    int getWidth();
    int getHeight();

    void togglePause();

    void tick();

    void setDirection(@NonNull Direction direction);

    @NonNull Optional<Point> getApple();

    @NonNull Snake getSnake();

    static SnakeGame create(int width, int height) {
        return ServiceLoader.load(SnakeGameFactory.class)
                            .stream()
                            .map(p -> p.get())
                            .findFirst().orElseThrow(() -> new RuntimeException("No implementation of the SnakeGame"))
                            .create(width, height);
    }
}
