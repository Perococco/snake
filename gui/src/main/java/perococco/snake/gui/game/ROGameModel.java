package perococco.snake.gui.game;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;
import lombok.NonNull;
import perococco.snake.core.GameDifficulty;
import perococco.snake.core.GameView;

public interface ROGameModel {

    @NonNull ReadOnlyObjectProperty<GameDifficulty> gameDifficultyProperty();

    /**
     * @return a list containing all the available style's names that can be used to draw the snake
     */
    @NonNull ObservableList<String> getReadOnlySnakeDrawerStyles();

    @NonNull ReadOnlyStringProperty selectedDrawerStyleProperty();

    /**
     * @return a property containing the last snapshot of the game
     */
    @NonNull ReadOnlyObjectProperty<GameView> gameViewProperty();

    default @NonNull GameDifficulty getGameDifficulty() {
        return gameDifficultyProperty().get();
    }

    default @NonNull String getSelectedSnakeDrawerStyle() {
        return selectedDrawerStyleProperty().get();
    }
}
