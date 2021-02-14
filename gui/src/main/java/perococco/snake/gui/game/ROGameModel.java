package perococco.snake.gui.game;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import lombok.NonNull;
import perococco.snake.core.GameState;
import perococco.snake.core.SnakeGame;

import java.util.Optional;

public interface ROGameModel {

    @NonNull ObservableList<String> getReadOnlySnakeDrawerStyles();

    @NonNull ReadOnlyObjectProperty<Optional<GameState>> gameStateProperty();
    @NonNull ReadOnlyLongProperty tickProperty();
    @NonNull ReadOnlyObjectProperty<Optional<SnakeGame>> snakeGameProperty();

    default @NonNull Optional<SnakeGame> getSnakeGame() {
        return snakeGameProperty().get();
    }

    default long getTick() {
        return tickProperty().get();
    }


}
