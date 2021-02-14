import perococco.snake.core.SnakeGameFactory;
import perococco.snake.engine.PerococcoSnakeGameFactory;

module snake.engine {
    requires static lombok;

    requires snake.core;

    provides SnakeGameFactory with PerococcoSnakeGameFactory;
}
