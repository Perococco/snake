package perococco.snake.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import perococco.snake.core.Disposable;
import perococco.snake.gui.game.GameManager;
import perococco.snake.gui.game.SnakeDrawer;

import javax.swing.plaf.synth.Region;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final GameManager gameManager = new GameManager();
    private final KeyTracker keyTracker = new KeyTracker();


    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader();



        fxmlLoader.setControllerFactory(new ControllerFactory(primaryStage::close, gameManager, keyTracker));

        fxmlLoader.setLocation(MainController.class.getResource("main.fxml"));
        fxmlLoader.load();
        final Parent parent = fxmlLoader.getRoot();
        final Object controller = fxmlLoader.getController();

        final var scene = new Scene(parent,600,400);

        keyTracker.attach(scene);

        gameManager.startGame();

        primaryStage.setOnHidden(e -> {
            if (controller instanceof Disposable) {
                ((Disposable) controller).dispose();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleEvent(KeyEvent e) {

    }

}
