package perococco.snake.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import perococco.snake.gui.game.GameManager;
import perococco.snake.gui.game.SnakeDrawer;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final GameManager gameManager = new GameManager();
    private final KeyTracker keyTracker = new KeyTracker();


    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setControllerFactory(new ControllerFactory(
                primaryStage::close, gameManager, keyTracker));

        fxmlLoader.setLocation(MainController.class.getResource("main.fxml"));
        final Parent parent = fxmlLoader.load();
        final var scene = new Scene(parent,600,400);


        keyTracker.attach(scene);


        gameManager.startGame();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleEvent(KeyEvent e) {

    }

}
