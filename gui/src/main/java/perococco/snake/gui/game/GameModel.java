package perococco.snake.gui.game;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import perococco.snake.core.GameState;
import perococco.snake.core.SnakeGame;

import java.util.Optional;

public class GameModel implements ROGameModel {

    @Getter
    private final @NonNull ObservableList<String> snakeDrawerStyles = FXCollections.observableArrayList();

    @Getter
    private final @NonNull ObservableList<String> readOnlySnakeDrawerStyles = FXCollections.unmodifiableObservableList(snakeDrawerStyles);
    private final @NonNull ObjectProperty<Optional<GameState>> gameState = new SimpleObjectProperty<>(Optional.empty());
    private final @NonNull LongProperty tick = new SimpleLongProperty(0);
    private final @NonNull ObjectProperty<Optional<SnakeGame>> snakeGame=  new SimpleObjectProperty<>(Optional.empty());


    public Optional<GameState> getGameState() {
        return gameState.get();
    }

    public void setGameState(Optional<GameState> gameState) {
        this.gameState.set(gameState);
    }

    public void setTick(long tick) {
        this.tick.set(tick);
    }

    public @NonNull ObjectProperty<Optional<SnakeGame>> snakeGameProperty() {
        return snakeGame;
    }

    public void clearSnakeGame() {
        this.snakeGame.set(Optional.empty());
    }

    public void setSnakeGame(@NonNull SnakeGame snakeGame) {
        this.snakeGame.set(Optional.of(snakeGame));
    }

    @Override
    public @NonNull ReadOnlyLongProperty tickProperty() {
        return tick;
    }

    @Override
    public @NonNull ObjectProperty<Optional<GameState>> gameStateProperty() {
        return gameState;
    }

    public void setGameState(@NonNull GameState gameState) {
        this.gameState.set(Optional.of(gameState));
    }

    public void clearGameState() {
        this.gameState.set(Optional.empty());
    }

}
