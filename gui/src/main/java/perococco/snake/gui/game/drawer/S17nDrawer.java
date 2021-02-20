package perococco.snake.gui.game.drawer;

import javafx.scene.paint.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.Direction;
import perococco.snake.core.GameView;
import perococco.snake.gui.game.DrawInfo;
import perococco.snake.gui.game.SnakeDrawer;

import java.util.function.IntToDoubleFunction;

public class S17nDrawer implements SnakeDrawer {

    @Override
    public @NonNull String getName() {
        return "s17n";
    }

    @Override
    public void draw(@NonNull DrawInfo drawInfo,
                     @NonNull GameView gameView) {

        var context = drawInfo.getGraphicsContext();
        var cellSize = drawInfo.getCellSize();
        var snakeLength = gameView.getSnakeLength();

        final var factor = snakeLength <= 0 ? 1. : 1. / snakeLength;
//        final IntToDoubleFunction brightness = i -> 0.5 * (1 + i * factor);
        final IntToDoubleFunction brightness = i -> i * factor;

        var dark = Color.hsb(116, 1, brightness.applyAsDouble(0));

        var snakeBody = gameView.getSnakeBody();

        for (int i = 0; i < snakeBody.size(); i++) {
            var light = Color.hsb(116, 1, brightness.applyAsDouble(i + 1));
            final var part = snakeBody.get(i);
            final var to = part.getTo();
            final Direction from;
            if (i < snakeBody.size() - 1) {
                from = snakeBody.get(i + 1).getTo();
            } else {
                from = to;
            }

            var x = part.getPosition().getX() * cellSize;
            var y = part.getPosition().getY() * cellSize;


            context.setFill(new GradientComputer(from, to, dark, light, x, y, cellSize).compute());
            context.fillRect(x, y, cellSize, cellSize);

            dark = light;

        }
    }

    @RequiredArgsConstructor
    private static class GradientComputer {

        private final @NonNull Direction from;
        private final @NonNull Direction to;
        private final @NonNull Color dark;
        private final @NonNull Color light;
        private final double x;
        private final double y;
        private final double cellSize;

        public Paint compute() {
            if (sameDirections()) {
                return computeLinearGradient();
            } else {
                return computeRadialGradient();
            }
        }

        private boolean sameDirections() {
            return from == to;
        }


        private Paint computeLinearGradient() {
            return switch (from) {
                case NORTH -> verticalGradient(dark, light);
                case SOUTH -> verticalGradient(light, dark);
                case WEST -> horizontalGradient(dark, light);
                case EAST -> horizontalGradient(light, dark);
            };
        }

        private Paint verticalGradient(Color first, Color last) {
            return linearGradient(0, cellSize, first, last);
        }

        private Paint horizontalGradient(Color first, Color last) {
            return linearGradient(cellSize, 0, first, last);
        }

        private Paint linearGradient(double endX, double endY, Color first, Color last) {
            return new LinearGradient(x, y, x + endX, y + endY, false, CycleMethod.NO_CYCLE, new Stop(0, first),
                                      new Stop(1, last));
        }


        private Paint computeRadialGradient() {
            final int flag = flag(from) | flag(to);
            return switch (flag) {
                case 0b1001 -> computeNorthWest();
                case 0b0101 -> computeNorthEast();
                case 0b1010 -> computeSouthWest();
                case 0b0110 -> computeSouthEast();
                default -> Color.RED;
            };
        }

        private Paint computeNorthWest() {
//            return Color.RED;
            return new LinearGradient(x,y,x+cellSize,y+cellSize,
                                      false,CycleMethod.NO_CYCLE,
                                      new Stop(0,dark),new Stop(1,light));
        }

        private Paint computeSouthWest() {
            return new LinearGradient(x+cellSize,y,x,y+cellSize,
                                      false,CycleMethod.NO_CYCLE,
                                      new Stop(0,light),new Stop(1,dark));
        }

        private Paint computeNorthEast() {
            return new LinearGradient(x+cellSize,y,x,y+cellSize,
                                      false,CycleMethod.NO_CYCLE,
                                      new Stop(0,dark),new Stop(1,light));
        }

        private Paint computeSouthEast() {
            return new LinearGradient(x,y,x+cellSize,y+cellSize,
                                      false,CycleMethod.NO_CYCLE,
                                      new Stop(0,light),new Stop(1,dark));
        }


        private int flag(@NonNull Direction direction) {
            return switch (direction) {
                case NORTH -> 1;
                case SOUTH -> 2;
                case EAST -> 4;
                case WEST -> 8;
            };
        }
    }

}
