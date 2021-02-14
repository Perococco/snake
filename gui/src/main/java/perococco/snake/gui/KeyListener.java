package perococco.snake.gui;

import javafx.scene.input.KeyEvent;
import lombok.NonNull;

public interface KeyListener {

    void onKey(@NonNull KeyEvent event);
}
