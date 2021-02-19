package perococco.snake.gui.game.drawer;

import javafx.scene.paint.Color;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.core.GameView;
import perococco.snake.core.Snake;
import perococco.snake.core.SnakeGame;
import perococco.snake.gui.game.DrawInfo;
import perococco.snake.gui.game.SnakeDrawer;

@RequiredArgsConstructor
public class G1dootSnakeDrawer implements SnakeDrawer {

    @Override
    public @NonNull String getName() {
        return "G1doot";
    }

    @Override
    public void draw(@NonNull DrawInfo drawInfo, @NonNull GameView gameView) {
        final var g = drawInfo.getGraphicsContext();
        final var cellSize = drawInfo.getCellSize();

        final var snakeLength = gameView.getSnakeLength();
        final var factor = snakeLength<=1?1.:1./(snakeLength-1);

        for (int i = 0; i < snakeLength; i++) {
            var p = gameView.getSakePosition(i);
            g.setFill(Color.hsb(116, 1, 0.5*(1+i*factor)));

            double scale = 1 - (i*factor)*0.4;
            double size = cellSize*scale;
            double delta = (cellSize-size)*0.5;
            double x= p.getX()*cellSize+delta;
            double y= p.getY()*cellSize+delta;


            g.fillRect(x,y,size,size);
        }
    }
}
