package perococco.snake.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@RequiredArgsConstructor
public enum Direction {
    NORTH(Holder::isSouth),
    WEST(Holder::isEast),
    SOUTH(Holder::isNorth),
    EAST(Holder::isWest),
    ;

    private final @NonNull Predicate<Direction> oppositeTest;

    public boolean isOpposite(@NonNull Direction other) {
        return oppositeTest.test(other);
    }

    private static class Holder {

        public static boolean isSouth(@NonNull Direction direction) {
            return direction == SOUTH;
        }
        public static boolean isNorth(@NonNull Direction direction) {
            return direction == NORTH;
        }
        public static boolean isEast(@NonNull Direction direction) {
            return direction == EAST;
        }
        public static boolean isWest(@NonNull Direction direction) {
            return direction == WEST;
        }
    }

}
