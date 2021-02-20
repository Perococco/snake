package perococco.snake.engine;

import lombok.*;
import perococco.snake.core.Point;

@AllArgsConstructor
public class Cell {

    public static @NonNull Cell with(int index, int width) {
        final var point = new Point(index%width, index/width);
        return new Cell(point,width,index,false);
    }

    @Getter
    private final Point point;

    private final int width;
    @Getter @Setter
    private int index;
    @Getter @Setter
    private boolean appleEaten;


    @Override
    public String toString() {
        return "Cell{(x=" +point.getX()+",y="+point.getY()+","+(point.getX()+point.getY()*width)+") "+index+"}";
    }
}
