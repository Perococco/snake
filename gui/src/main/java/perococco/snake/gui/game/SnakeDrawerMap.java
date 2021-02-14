package perococco.snake.gui.game;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface SnakeDrawerMap {

    @NonNull Optional<SnakeDrawer> getDrawer(@NonNull String name);

    @NonNull List<String> getDrawerNames();
}
