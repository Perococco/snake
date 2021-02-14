package perococco.snake.gui;

import javafx.event.ActionEvent;
import lombok.RequiredArgsConstructor;
import perococco.snake.gui.game.GameManager;

@RequiredArgsConstructor
public class MainController {

    private final ApplicationCloser applicationCloser;

    private final GameManager gameManager;

    public void quit(ActionEvent actionEvent) {
        applicationCloser.close();
    }

    public void startGame(ActionEvent actionEvent) {
        gameManager.startGame();
    }

    public void stopGame(ActionEvent actionEvent) {
        gameManager.stopGame();
    }
}
