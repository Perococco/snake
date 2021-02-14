package perococco.snake.core;

import lombok.NonNull;

public interface Snake {

    boolean isAlive();

    int bodyLength();

    @NonNull Point position(int bodyPart);
}
