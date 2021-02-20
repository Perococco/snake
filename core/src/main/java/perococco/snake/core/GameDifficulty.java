package perococco.snake.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public enum GameDifficulty {
    EASY(Duration.ofMillis(250)),
    NORMAL(Duration.ofMillis(125)),
    HARD(Duration.ofMillis(75)),
    ;
    @Getter
    private final Duration tickDuration;
}
