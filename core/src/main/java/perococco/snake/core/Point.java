package perococco.snake.core;

import lombok.NonNull;
import lombok.Value;

@Value
public class Point {

    public static @NonNull Point of(int x, int y) {
        return new Point(x, y);
    }

    int x;
    int y;


    public @NonNull Point pointAt(@NonNull Direction direction) {
        return switch (direction) {
            case NORTH -> of(x,y-1);
            case WEST -> of(x-1,y);
            case SOUTH -> of(x,y+1);
            case EAST -> of(x+1,y);
        };
    }
}
