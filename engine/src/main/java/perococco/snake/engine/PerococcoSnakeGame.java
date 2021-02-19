package perococco.snake.engine;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.*;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class PerococcoSnakeGame implements SnakeGame {

    private static final Random RANDOM = new Random();

    @Getter
    private final int width;
    @Getter
    private final int height;
    private final int size;

    private Point apple;

    private final Cell[] points;

    private @NonNull Direction snakeDirection = Direction.EAST;
    private @NonNull Direction pendingDirection = Direction.EAST;

    private int headIndex;

    private int snakeLength;

    @Getter
    private perococco.snake.core.Snake snake;

    @Getter
    private GameState state;

    public PerococcoSnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width*height;
        this.state = GameState.WAITING;
        this.snake = new MySnake(this);

        this.points = IntStream.range(0,width*height).mapToObj(i -> Cell.with(i,width)).toArray(Cell[]::new);

        this.headIndex = RANDOM.nextInt(size);

        this.headIndex = width;
        this.snakeLength = 1;

//        this.pendingDirection = Direction.EAST;
//        for (int i = 0; i < width-1; i++) {
//            this.apple = Point.of(i+1,1);
//            moveSnake();
//        }
//        this.pendingDirection = Direction.SOUTH;

        this.apple = this.pickApplePosition().orElseThrow(() -> new RuntimeException("BUG !!"));
    }

    @Override
    public void togglePause() {
        this.state = switch (state) {
            case RUNNING -> GameState.WAITING;
            case WAITING -> GameState.RUNNING;
            default -> this.state;
        };
    }

    private @NonNull Optional<Point> pickApplePosition() {
        int numberOfRoomLeft = width*height - snakeLength;
        if (numberOfRoomLeft<=0) {
            return Optional.empty();
        }
        int appleIndex = RANDOM.nextInt(numberOfRoomLeft);
        var cell = points[(appleIndex+1+headIndex)%size];
        return Optional.ofNullable(cell.getPoint());
    }

    public void tick() {
        if (state != GameState.RUNNING) {
            return;
        }

        if (this.moveSnake()) {
            this.pickApplePosition().ifPresentOrElse(p -> {
                this.apple = p;
            }, () -> {
                this.apple = null;
                this.state = GameState.GAME_WIN;
            });
        }
    }

    private boolean moveSnake() {
        this.snakeDirection = pendingDirection;
        final var destination = getSnakeHeadPosition().pointAt(snakeDirection);
        if (isOnBorder(destination) || isOnSnakeBody(destination)) {
            this.state = GameState.GAME_LOST;
            return false;
        } else {
            final var appleEaten = destination.equals(apple);
            final var newHeadIndex = (this.headIndex + 1) % size;
            final var destinationIndex = toLinearCoordinate(destination);
            final var cellIndex = this.points[destinationIndex].getIndex();

            this.swapCell(newHeadIndex, cellIndex);

            this.headIndex = newHeadIndex;

            if (appleEaten) {
                this.snakeLength++;
            }

            return appleEaten;
        }
    }

    private boolean isOnSnakeBody(@NonNull Point destination) {
        int linearCoordinate = toLinearCoordinate(destination);
        int index = this.points[linearCoordinate].getIndex();

        int relative = Math.mod(index-headIndex+snakeLength,size);

        return relative>0 && relative<=snakeLength;
    }

    private void swapCell(int index1, int index2) {
        final var cell1 = this.points[index1];
        final var cell2 = this.points[index2];

        this.points[index1] = cell2;
        this.points[index2] = cell1;

        final var backup = cell1.getIndex();
        cell1.setIndex(cell2.getIndex());
        cell2.setIndex(backup);

        this.points[toLinearCoordinate(cell2.getPoint())].setIndex(index1);
        this.points[toLinearCoordinate(cell1.getPoint())].setIndex(index2);
    }

    private boolean isOnBorder(@NonNull Point destination) {
        return destination.getX() < 0 || destination.getY()< 0 || destination.getX()>=width || destination.getY()>=height;
    }

    private @NonNull Point getSnakeHeadPosition() {
        return this.points[this.headIndex].getPoint();
    }


    public @NonNull int toLinearCoordinate(@NonNull Point point) {
        return point.getX()+point.getY()*width;
    }


    @Override
    public void setDirection(@NonNull Direction direction) {
        if (!this.snakeDirection.isOpposite(direction)) {
            this.pendingDirection = direction;
        }
    }

    @Override
    public @NonNull Optional<Point> getApple() {
        return Optional.ofNullable(apple);
    }


    @RequiredArgsConstructor
    private static class MySnake implements perococco.snake.core.Snake {

        private final @NonNull PerococcoSnakeGame game;

        @Override
        public boolean isAlive() {
            return game.getState() != GameState.GAME_LOST;
        }

        @Override
        public int bodyLength() {
            return game.snakeLength;
        }

        @Override
        public @NonNull Point position(int bodyPart) {
            if (bodyPart<0||bodyPart>= game.snakeLength) {
                throw new IndexOutOfBoundsException();
            }
            return game.points[Math.mod(game.headIndex-bodyPart, game.size)].getPoint();
        }
    }
}
