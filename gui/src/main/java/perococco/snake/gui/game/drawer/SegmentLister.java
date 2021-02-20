package perococco.snake.gui.game.drawer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.BodyPart;
import perococco.snake.core.GameView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SegmentLister {

    public static List<Segment> list(@NonNull GameView gameView) {
        return new SegmentLister(gameView).list();
    }

    private final @NonNull GameView gameView;


    private @NonNull List<Segment> list() {
        final List<Segment> segments = new ArrayList<>(gameView.getSnakeLength());

        var snakeBody = gameView.getSnakeBody();

        if (snakeBody.isEmpty()) {
            return List.of();
        }

        var first = snakeBody.get(0);
        var previous = snakeBody.get(0);
        for (int i = 1; i < snakeBody.size(); i++) {
            var current = snakeBody.get(i);
            if (previous.getTo() != current.getTo()) {
                segments.add(Segment.create(first.getPosition(),previous.getPosition()));
                first = current;
            }
            previous = current;
        }

        segments.add(Segment.create(first.getPosition(),snakeBody.get(snakeBody.size()-1).getPosition()));

        return segments;
    }


}
