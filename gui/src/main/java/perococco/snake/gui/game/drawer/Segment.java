package perococco.snake.gui.game.drawer;

import lombok.NonNull;
import perococco.snake.core.Point;

public interface Segment {

    static @NonNull Segment create(@NonNull Point start, @NonNull Point end) {
        if (start.getY() == end.getY()) {
            return new VerticalSegment(start.getX(),end.getX(),start.getY());
        } else if (start.getX() == end.getX()) {
            return new HorizontalSegment(start.getY(), end.getY(), start.getX());
        } else {
            throw new IllegalArgumentException("Invalid segment not horizontal nor vertical");
        }
    }

}
