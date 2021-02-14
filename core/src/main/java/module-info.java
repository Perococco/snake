import perococco.snake.core.SnakeGameFactory;

module snake.core {
    requires static lombok;

    exports perococco.snake.core;

    uses SnakeGameFactory;
}
