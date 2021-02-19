package perococco.snake.gui.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import perococco.snake.core.GameView;

public class GameModel implements ROGameModel {

    @Getter
    private final @NonNull ObservableList<String> snakeDrawerStyles = FXCollections.observableArrayList();
    @Getter
    private final @NonNull ObservableList<String> readOnlySnakeDrawerStyles = FXCollections.unmodifiableObservableList(snakeDrawerStyles);

    private final @NonNull ObjectProperty<GameView> gameView =  new SimpleObjectProperty<>(GameView.empty());

    public void setGameView(@NonNull GameView gameView) {
        this.gameView.set(gameView);
    }

    @Override
    public @NonNull ReadOnlyObjectProperty<GameView> gameViewProperty() {
        return gameView;
    }
}
