package perococco.snake.gui.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import perococco.snake.core.GameState;
import perococco.snake.gui.KeyListener;
import perococco.snake.gui.KeyTracker;
import perococco.snake.gui.game.drawer.G1dootSnakeDrawer;

import java.util.Optional;

public class GameController implements EventHandler<KeyEvent> {

    private final @NonNull SnakeDrawerMap snakeDrawerMap;
    private final @NonNull GameManager gameManager;
    private final @NonNull KeyTracker keyTracker;

    private final ObjectProperty<Optional<GameState>> gameState = new SimpleObjectProperty<>(Optional.empty());
    public Canvas drawingArea;

    private final ChangeListener<Number> tickListener = (l,o,n) -> this.onTick();
    public Pane parent;

    public GameController(@NonNull GameManager gameManager,
                          @NonNull SnakeDrawerMap snakeDrawerMap,
                          @NonNull KeyTracker keyTracker) {
        this.gameManager = gameManager;
        this.keyTracker = keyTracker;
        this.snakeDrawerMap = snakeDrawerMap;
        this.gameState.bind(gameManager.gameStateProperty());
        this.gameManager.addTickChangeListener(tickListener);

        this.keyTracker.addListener(new WeakEventHandler<>(this));
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            gameManager.handleUserInput(event);
        }
        if (GameState.RUNNING == gameState.get().orElse(null)) {
            event.consume();
        }
    }

    public void initialize() {
        drawingArea.widthProperty().bind(parent.widthProperty());
        drawingArea.heightProperty().bind(parent.heightProperty());
    }


    private void onTick() {
        final var fullWidth = drawingArea.getWidth();
        final var fullHeight = drawingArea.getHeight();
        final var g = drawingArea.getGraphicsContext2D();

        g.clearRect(-1,-1, fullWidth+2, fullHeight+2);
        final var snakeGame = gameManager.getSnakeGame().orElse(null);
        if (snakeGame == null) {
            return;
        }
        final var gameHeight = snakeGame.getHeight();
        final var gameWidth = snakeGame.getWidth();

        final double cellSize = Math.min(fullHeight/(gameHeight+2), fullWidth/(gameWidth+2));

        final DrawInfo drawInfo = new DrawInfo(g,gameWidth,gameHeight,cellSize);


        BorderDrawer.draw(drawInfo);
        g.translate(cellSize,cellSize);
        try {
            snakeGame.getApple().ifPresent(p -> AppleDrawer.draw(drawInfo,p));
            gameManager.getSnakeDrawer()
                       .flatMap(snakeDrawerMap::getDrawer)
                       .orElseGet(G1dootSnakeDrawer::new)
                       .draw(drawInfo, snakeGame.getSnake());
        } finally {
            g.translate(-cellSize,-cellSize);
        }


    }



}
