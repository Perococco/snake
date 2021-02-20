package perococco.snake.engine;

import lombok.Getter;
import lombok.NonNull;
import perococco.snake.core.*;

import java.util.*;
import java.util.stream.IntStream;

public class PerococcoSnakeGame implements SnakeGame {

    private static final Random RANDOM = new Random();

    @Getter
    private final int width;
    @Getter
    private final int height;
    private final int size;

    private Point apple;

    private final Cell[] cells;

    private @NonNull Direction snakeDirection = Direction.EAST;
    private @NonNull Direction pendingDirection = Direction.EAST;

    private int headIndex;

    private int snakeLength;

    @Getter
    private GameState state;

    public PerococcoSnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.size = width * height;
        this.state = GameState.WAITING;

        this.cells = IntStream.range(0, width * height).mapToObj(i -> Cell.with(i, width)).toArray(Cell[]::new);

        this.headIndex = RANDOM.nextInt(size);
        this.snakeLength = 1;
        this.headIndex = (width/3) +(height/2)*width;
        this.snakeDirection = Direction.EAST;

        this.apple = new Point(width/3*2,height/2);

//        this.pendingDirection = Direction.EAST;
//        for (int i = 0; i < width/2; i++) {
//            this.apple = getSnakeHeadPosition().pointAt(pendingDirection);
//            moveSnake();
//        }
//        this.pendingDirection = Direction.SOUTH;
//        for (int j = 0; j < width/2; j++) {
//            this.apple = getSnakeHeadPosition().pointAt(pendingDirection);
//            moveSnake();
//        }

//        this.apple = this.pickApplePosition().orElseThrow(() -> new RuntimeException("BUG !!"));
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
        int numberOfRoomLeft = width * height - snakeLength;
        if (numberOfRoomLeft <= 0) {
            return Optional.empty();
        }
        int appleIndex = RANDOM.nextInt(numberOfRoomLeft);
        var cell = cells[(appleIndex + 1 + headIndex) % size];
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
                this.state = GameState.GAME_WON;
            });
        }
    }

    @Override
    public @NonNull GameView createSnapshot() {
        return new GameView(state, width,height,apple, getSnakeBody());
    }

    @Override
    public boolean isGameOver() {
        return state == GameState.GAME_WON || state == GameState.GAME_LOST;

    }

    private @NonNull List<BodyPart> getSnakeBody() {

        final var list = new ArrayList<BodyPart>(this.snakeLength);

        var cell = this.cells[convertBodyIndexToCellIndex(0)];
        var lastPoint = cell.getPoint();
        list.add(new BodyPart(lastPoint,this.snakeDirection,cell.isAppleEaten()));
        for (int i = 1; i < snakeLength; i++) {
            cell = this.cells[convertBodyIndexToCellIndex(i)];
            var current = cell.getPoint();
            final var direction = getDirectionToMoveToTarget(current,lastPoint);
            list.add(new BodyPart(current,direction,cell.isAppleEaten()));
            lastPoint = current;
        }
        return list;
    }

    private @NonNull Direction getDirectionToMoveToTarget(Point current, Point lastPoint) {
        final int dy = lastPoint.getY()-current.getY();
        final int dx = lastPoint.getX()-current.getX();
        if (dy != 0 && dx != 0) {
            throw new IllegalStateException("Something wrong, the snake did not move !!");
        }
        if (dx == 0) {
            return dy<0?Direction.NORTH:Direction.SOUTH;
        } else {
            return dx<0?Direction.WEST:Direction.EAST;
        }
    }

    private int convertBodyIndexToCellIndex(int bodyIndex) {
        return Math.mod(this.headIndex - bodyIndex, this.size);
    }

    private boolean isSnakeAlive() {
        return this.state != GameState.GAME_LOST;
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
            final var cellIndex = this.cells[destinationIndex].getIndex();

            this.swapCell(newHeadIndex, cellIndex);

            this.headIndex = newHeadIndex;

            this.cells[this.headIndex].setAppleEaten(appleEaten);

            if (appleEaten) {
                this.snakeLength++;
            }

            return appleEaten;
        }
    }

    private boolean isOnSnakeBody(@NonNull Point destination) {
        int linearCoordinate = toLinearCoordinate(destination);
        int index = this.cells[linearCoordinate].getIndex();

        int relative = Math.mod(index - headIndex + snakeLength, size);

        return relative > 0 && relative <= snakeLength;
    }

    private void swapCell(int index1, int index2) {
        final var cell1 = this.cells[index1];
        final var cell2 = this.cells[index2];

        this.cells[index1] = cell2;
        this.cells[index2] = cell1;

        final var backup = cell1.getIndex();
        cell1.setIndex(cell2.getIndex());
        cell2.setIndex(backup);

        this.cells[toLinearCoordinate(cell2.getPoint())].setIndex(index1);
        this.cells[toLinearCoordinate(cell1.getPoint())].setIndex(index2);
    }

    private boolean isOnBorder(@NonNull Point destination) {
        return destination.getX() < 0 || destination.getY() < 0 || destination.getX() >= width || destination.getY() >= height;
    }

    private @NonNull Point getSnakeHeadPosition() {
        return this.cells[this.headIndex].getPoint();
    }


    public @NonNull int toLinearCoordinate(@NonNull Point point) {
        return point.getX() + point.getY() * width;
    }


    @Override
    public void setDirection(@NonNull Direction direction) {
        if (!this.snakeDirection.isOpposite(direction)) {
            this.pendingDirection = direction;
        }
    }


//    @RequiredArgsConstructor
//    private static class MySnake implements perococco.snake.core.Snake {
//
//        private final @NonNull PerococcoSnakeGame game;
//
//        @Override
//        public boolean isAlive() {
//            return game.getState() != GameState.GAME_LOST;
//        }
//
//        @Override
//        public int bodyLength() {
//            return game.snakeLength;
//        }
//
//        @Override
//        public @NonNull Point position(int bodyPart) {
//            if (bodyPart<0||bodyPart>= game.snakeLength) {
//                throw new IndexOutOfBoundsException();
//            }
//            return game.points[Math.mod(game.headIndex-bodyPart, game.size)].getPoint();
//        }
//    }
}
