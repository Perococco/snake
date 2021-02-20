package perococco.snake.gui.game;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perococco.snake.core.Direction;
import perococco.snake.core.GameDifficulty;
import perococco.snake.core.GameView;
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

    public GameManager() {
        final var drawerName = snakeDrawers.getDrawerNames().get(0);
        this.gameModel.setSelectedDrawerStyle(drawerName);
        this.gameModel.getSnakeDrawerStyles().setAll(snakeDrawers.getDrawerNames());
        this.gameModel.setGameDifficulty(GameDifficulty.NORMAL);
        this.gameModel.setGameView(GameView.empty());
    }

    @Synchronized
    public void startGame() {
        this.stopGame();
        var snakeGame = SnakeGame.create(10, 10);
        this.loop = new Loop(snakeGame, () -> Platform.runLater(this::updateModel), gameModel);
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

    /**
     * @param e event sent by the GUI
     */
    public void handleUserInput(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            Optional.ofNullable(loop)
                    .map(Loop::getSnakeGame)
                    .ifPresent(game -> {
                        if (game.isGameOver()) {
                            startGame();
                        } else {
                            game.togglePause();
                        }
                    });
        } else {
            Consumer<SnakeGame> updater = switch (e.getCode()) {
                case UP -> s -> s.setDirection(Direction.NORTH);
                case DOWN -> s -> s.setDirection(Direction.SOUTH);
                case LEFT -> s -> s.setDirection(Direction.WEST);
                case RIGHT -> s -> s.setDirection(Direction.EAST);
                default -> s -> {
                };
            };
            Optional.ofNullable(loop).map(Loop::getSnakeGame).ifPresent(updater);
        }
    }


    public @NonNull ROGameModel getModel() {
        return this.gameModel;
    }

    private void updateModel() {
        final var gameView = Optional.ofNullable(loop)
                                     .map(Loop::getSnakeGame)
                                     .map(SnakeGame::createSnapshot)
                                     .orElseGet(GameView::empty);
        this.gameModel.setGameView(gameView);
    }

    public void onStyleSelected(String selectedItem) {
        if (selectedItem != null) {
            this.gameModel.setSelectedDrawerStyle(selectedItem);
        }
    }

    public void selectDifficulty(GameDifficulty selectedItem) {
        if (selectedItem != null) {
            this.gameModel.setGameDifficulty(selectedItem);
        }
    }

    @RequiredArgsConstructor
    private static class Loop implements Runnable {

        @Getter
        private final SnakeGame snakeGame;

        private final Runnable modelUpdater;

        private final ROGameModel model;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    snakeGame.tick();
                    modelUpdater.run();
                    Thread.sleep(
                            model.getGameDifficulty().getTickDuration().toMillis());//TODO extract speed of the game to put it in the model
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }


        public void setDirection(@NonNull Direction direction) {
            snakeGame.setDirection(direction);
        }
    }
}
