package perococco.snake.gui.game;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NonNull;
import perococco.snake.core.GameDifficulty;
import perococco.snake.core.GameView;

public class GameModel implements ROGameModel {

    private final @NonNull ObjectProperty<GameDifficulty> gameDifficulty = new SimpleObjectProperty<>(GameDifficulty.NORMAL);

    @Getter
    private final @NonNull ObservableList<String> snakeDrawerStyles = FXCollections.observableArrayList();

    private final @NonNull StringProperty selectedDrawerStyle = new SimpleStringProperty("");

    @Getter
    private final @NonNull ObservableList<String> readOnlySnakeDrawerStyles = FXCollections.unmodifiableObservableList(snakeDrawerStyles);

    private final @NonNull ObjectProperty<GameView> gameView =  new SimpleObjectProperty<>(GameView.empty());

    @Override
    public @NonNull StringProperty selectedDrawerStyleProperty() {
        return selectedDrawerStyle;
    }

    @Override
    public @NonNull ObjectProperty<GameDifficulty> gameDifficultyProperty() {
        return gameDifficulty;
    }

    public void setGameView(@NonNull GameView gameView) {
        this.gameView.set(gameView);
    }

    public void setGameDifficulty(@NonNull GameDifficulty gameDifficulty) {
        this.gameDifficulty.set(gameDifficulty);
    }

    public void setSelectedDrawerStyle(String selectedDrawerStyle) {
        this.selectedDrawerStyle.set(selectedDrawerStyle);
    }

    @Override
    public @NonNull ObjectProperty<GameView> gameViewProperty() {
        return gameView;
    }
}
