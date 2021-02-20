import perococco.snake.gui.game.SnakeDrawer;
import perococco.snake.gui.game.drawer.G1dootSnakeDrawer;
import perococco.snake.gui.game.drawer.PerococcoSnakeDrawer;
import perococco.snake.gui.game.drawer.S17nDrawer;

module snake.gui {
    requires static lombok;

    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires snake.core;
    requires java.desktop;

    exports perococco.snake.gui;

    opens perococco.snake.gui.game to javafx.fxml;

    uses SnakeDrawer;
    provides SnakeDrawer with G1dootSnakeDrawer, PerococcoSnakeDrawer, S17nDrawer;
}
