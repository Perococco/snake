package perococco.snake.gui.game;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lombok.NonNull;
import perococco.snake.core.GameView;
import perococco.snake.gui.KeyTracker;
import perococco.snake.gui.game.drawer.G1dootSnakeDrawer;

public class GameController implements EventHandler<KeyEvent> {

    private final @NonNull SnakeDrawerMap snakeDrawerMap;
    private final @NonNull GameManager gameManager;
    private final @NonNull KeyTracker keyTracker;

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
        this.gameView.addListener((l,o,n) -> this.onGameViewChanged(n));
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
    }


    private void onGameViewChanged(@NonNull GameView gameView) {
        final var fullWidth = drawingArea.getWidth();
        final var fullHeight = drawingArea.getHeight();
        final var g = drawingArea.getGraphicsContext2D();
        g.clearRect(-1,-1, fullWidth+2, fullHeight+2);

        if (gameView.isEmpty()) {
            return;
        }

        final int gameWidth = gameView.getWidth();
        final int gameHeight = gameView.getHeight();
        final double cellSize = Math.min(fullHeight/(gameHeight+2), fullWidth/(gameWidth+2));
        final DrawInfo drawInfo = new DrawInfo(g,gameWidth,gameHeight,cellSize);

        BorderDrawer.draw(drawInfo);

        g.translate(cellSize,cellSize);
        try {
            gameView.getApple().ifPresent(p -> AppleDrawer.draw(drawInfo,p));
            gameManager.getSnakeDrawer()
                       .flatMap(snakeDrawerMap::getDrawer)
                       .orElseGet(G1dootSnakeDrawer::new)
                       .draw(drawInfo, gameView);
        } finally {
            g.translate(-cellSize,-cellSize);
        }


    }



}
