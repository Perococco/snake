package perococco.snake.gui.game;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.*;
import perococco.snake.core.Direction;
import perococco.snake.core.GameState;
import perococco.snake.core.SnakeGame;
import perococco.snake.gui.SnakeDrawers;

import java.util.Optional;
import java.util.function.Consumer;

public class GameManager {

    private final GameModel gameModel = new GameModel();

    @Getter
    private final SnakeDrawerMap snakeDrawers = new SnakeDrawers();

    private Thread thread;

    private Loop loop = null;

    private String snakeDrawerName = null;

    private long tick = 0;

    public GameManager() {
        this.snakeDrawerName = snakeDrawers.getDrawerNames().get(0);
        this.gameModel.getSnakeDrawerStyles().setAll(snakeDrawers.getDrawerNames());

    }

    @Synchronized
    public void startGame() {
        this.stopGame();
        var snakeGame = SnakeGame.create(20,20);
        this.loop = new Loop(snakeGame,() -> Platform.runLater(this::updateModel));
        this.thread = new Thread(this.loop);
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Synchronized
    public void stopGame() {
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
            this.loop = null;
        }
        this.updateModel();
    }

    public @NonNull Optional<String> getSnakeDrawer() {
        return Optional.ofNullable(snakeDrawerName);
    }

    public void handleUserInput(KeyEvent e) {

        final Consumer<SnakeGame> updater = switch (e.getCode()) {
            case UP -> s -> s.setDirection(Direction.NORTH);
            case DOWN -> s -> s.setDirection(Direction.SOUTH);
            case LEFT -> s -> s.setDirection(Direction.WEST);
            case RIGHT -> s -> s.setDirection(Direction.EAST);
            case SPACE -> SnakeGame::togglePause;
            default -> s -> {};
        };

            Optional.ofNullable(loop).map(Loop::getSnakeGame).ifPresent(updater);
    }


    public @NonNull ROGameModel getModel() {
        return this.gameModel;
    }

    private void updateModel() {
        Optional.ofNullable(loop).map(Loop::getSnakeGame).ifPresentOrElse(s -> {
            this.gameModel.setGameState(s.getState());
            this.gameModel.setSnakeGame(s);
        }, () -> {
            this.gameModel.clearGameState();
            this.gameModel.clearSnakeGame();
        });
        this.gameModel.setTick(tick++);
    }

    public ObservableValue<Optional<GameState>> gameStateProperty() {
        return this.gameModel.gameStateProperty();
    }

    public @NonNull Optional<SnakeGame> getSnakeGame() {
        return gameModel.getSnakeGame();
    }

    public void addTickChangeListener(ChangeListener<Number> tickListener) {
        gameModel.tickProperty().addListener(tickListener);
    }

    public void onStyleSelected(String selectedItem) {
        this.snakeDrawerName = selectedItem;
    }


    @RequiredArgsConstructor
    private static class Loop implements Runnable {

        @Getter
        private final SnakeGame snakeGame;

        private final Runnable modelUpdater;

        @Override
        public void run() {
            System.out.println("LAUNCHED");
            try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    snakeGame.tick();
                    modelUpdater.run();
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void setDirection(@NonNull Direction direction) {
            snakeGame.setDirection(direction);
        }
    }
}
