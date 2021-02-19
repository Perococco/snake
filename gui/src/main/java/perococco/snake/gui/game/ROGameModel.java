package perococco.snake.gui.game;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import lombok.NonNull;
import perococco.snake.core.GameView;

public interface ROGameModel {

    /**
     * @return a list containing all the available style's names that can be used to draw the snake
     */
    @NonNull ObservableList<String> getReadOnlySnakeDrawerStyles();

    /**
     * @return a property containing the last snapshot of the game
     */
    @NonNull ReadOnlyObjectProperty<GameView> gameViewProperty();

    default @NonNull GameView getGameView() {
        return gameViewProperty().get();
    }


}
