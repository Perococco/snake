package perococco.snake.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GameView {

    public static @NonNull GameView empty() {
        return new GameView(GameState.WAITING, 0,0,null,List.of());
    }

    @Getter
    private final GameState gameState;

    @Getter
    private final int width;
    @Getter
    private final int height;

    private final Point apple;

    @Getter
    private final @NonNull List<BodyPart> snakeBody;


    public @NonNull Optional<Point> getApple() {
        return Optional.ofNullable(apple);
    }

    public int getSnakeLength() {
        return snakeBody.size();
    }

    public @NonNull Point getSakePosition(int bodyIndex) {
        return snakeBody.get(bodyIndex).getPosition();
    }

    public boolean isEmpty() {
        return width == 0 || height == 0;
    }

    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }
}
