package perococco.snake.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.gui.game.GameManager;
import perococco.snake.gui.game.SnakeDrawerMap;

@RequiredArgsConstructor
public class StyleSelectorController {

    private final @NonNull GameManager gameManager;

    private final @NonNull SnakeDrawerMap snakeDrawers;

    public ComboBox<String> styleSelector;

    public void initialize() {
        styleSelector.setItems(gameManager.getModel().getReadOnlySnakeDrawerStyles());
        gameManager.getSnakeDrawer().ifPresentOrElse(s -> styleSelector.getSelectionModel().select(s),
                                                     () -> styleSelector.getSelectionModel().clearSelection());

    }

    public void onStyleSelected(ActionEvent actionEvent) {
        gameManager.onStyleSelected(styleSelector.getSelectionModel().getSelectedItem());
    }
}
