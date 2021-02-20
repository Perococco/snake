package perococco.snake.gui.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import perococco.snake.core.Disposable;
import perococco.snake.core.GameView;
import perococco.snake.gui.KeyTracker;
import perococco.snake.gui.game.drawer.G1dootSnakeDrawer;

import java.util.Timer;
import java.util.TimerTask;

public class GameController implements EventHandler<KeyEvent>, Disposable {

    private final @NonNull SnakeDrawerMap snakeDrawerMap;
    private final @NonNull GameManager gameManager;
    private final @NonNull KeyTracker keyTracker;

    private final ObjectProperty<SnakeDrawer> snakeDrawer = new SimpleObjectProperty<>(null);

    private final ObjectProperty<GameView> gameView = new SimpleObjectProperty<>(GameView.empty());
    public Canvas drawingArea;

    public Pane parent;

    public GameController(@NonNull GameManager gameManager,
                          @NonNull SnakeDrawerMap snakeDrawerMap,
                          @NonNull KeyTracker keyTracker) {
        this.gameManager = gameManager;
        this.keyTracker = keyTracker;
        this.snakeDrawerMap = snakeDrawerMap;
        this.gameView.bind(gameManager.getModel().gameViewProperty());
        this.keyTracker.addListener(new WeakEventHandler<>(this));
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            gameManager.handleUserInput(event);
        }
        if (gameView.get().isRunning()) {
            event.consume();
        }
    }

    public void initialize() {
        drawingArea.widthProperty().bind(parent.widthProperty());
        drawingArea.heightProperty().bind(parent.heightProperty());

        this.gameView.addListener(l -> this.onGameViewChanged());
        this.snakeDrawer.addListener(l -> this.onGameViewChanged());
        var model = gameManager.getModel();
        this.snakeDrawer.bind(
                Bindings.createObjectBinding(
                        () -> {
                            final var name = model.getSelectedSnakeDrawerStyle();
                            return snakeDrawerMap.getDrawer(name).orElse(null);
                        },
                        model.selectedDrawerStyleProperty()));
    }

    @Override
    public void dispose() {

    }

    private void onGameViewChanged() {
        final var gameView = this.gameView.get();
        final var fullWidth = drawingArea.getWidth();
        final var fullHeight = drawingArea.getHeight();
        final var g = drawingArea.getGraphicsContext2D();
        g.clearRect(-1, -1, fullWidth + 2, fullHeight + 2);

        if (gameView.isEmpty()) {
            return;
        }

        final int nbColumns = gameView.getWidth();
        final int nbRows = gameView.getHeight();
        final double cellSize = Math.min(fullHeight / (nbRows + 2), fullWidth / (nbColumns + 2));

        final double gameWidth = cellSize*(nbColumns+2);
        final double gameHeight = cellSize*(nbRows+2);

        final DrawInfo drawInfo = new DrawInfo(g, nbColumns, nbRows, cellSize);

        final double dx = (fullWidth - gameWidth)*0.5;
        final double dy = (fullHeight - gameHeight)*0.5;

        g.translate(dx,dy);

        BorderDrawer.draw(drawInfo);

        g.translate(cellSize, cellSize);
        try {
            gameView.getApple().ifPresent(p -> AppleDrawer.draw(drawInfo, p));
            final var drawer = snakeDrawer.get();
            if (drawer != null) {
                drawer.draw(drawInfo, gameView);
            }
        } finally {
            g.translate(-cellSize-dx, -cellSize-dy);
        }


    }


}
