package perococco.snake.gui;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.Disposable;
import perococco.snake.gui.game.GameManager;

@RequiredArgsConstructor
public class MainController implements Disposable {

    private final ApplicationCloser applicationCloser;

    private final GameManager gameManager;
    public Disposable gameController;
    public Disposable gameOptionsController;

    public void quit(ActionEvent actionEvent) {
        applicationCloser.close();
    }

    public void startGame(ActionEvent actionEvent) {
        gameManager.startGame();
    }

    public void stopGame(ActionEvent actionEvent) {
        gameManager.stopGame();
    }

    @Override
    public void dispose() {
        gameController.dispose();
        gameOptionsController.dispose();
    }
}
