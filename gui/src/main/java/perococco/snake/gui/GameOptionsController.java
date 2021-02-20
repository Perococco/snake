package perococco.snake.gui;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.Disposable;
import perococco.snake.core.GameDifficulty;
import perococco.snake.gui.game.GameManager;

@RequiredArgsConstructor
public class GameOptionsController implements Disposable {

    private final @NonNull GameManager gameManager;

    public ComboBox<String> styleSelector;
    public ComboBox<GameDifficulty> difficultySelector;

    private final InvalidationListener difficultyListener = (l) -> this.updateDifficultySelection();

    private final InvalidationListener styleListener = (l) -> this.updateStyleSelection();


    public void initialize() {
        final var model = gameManager.getModel();

        styleSelector.setItems(model.getReadOnlySnakeDrawerStyles());
        difficultySelector.getItems().setAll(GameDifficulty.values());

        model.gameDifficultyProperty().addListener(difficultyListener);
        model.selectedDrawerStyleProperty().addListener(styleListener);

        this.updateStyleSelection();
        this.updateDifficultySelection();

    }

    @Override
    public void dispose() {
        final var model = gameManager.getModel();
        model.gameDifficultyProperty().removeListener(difficultyListener);
        model.selectedDrawerStyleProperty().removeListener(styleListener);
        styleSelector.setItems(FXCollections.emptyObservableList());
    }

    private void updateStyleSelection() {
        styleSelector.getSelectionModel().select(gameManager.getModel().getSelectedSnakeDrawerStyle());
    }
    private void updateDifficultySelection() {
        difficultySelector.getSelectionModel().select(gameManager.getModel().getGameDifficulty());
    }

    public void onStyleSelected() {
        gameManager.onStyleSelected(styleSelector.getSelectionModel().getSelectedItem());
    }

    public void onDifficultySelected() {
        gameManager.selectDifficulty(difficultySelector.getSelectionModel().getSelectedItem());
    }
}
