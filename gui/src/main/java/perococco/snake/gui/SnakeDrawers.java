package perococco.snake.gui;

import lombok.NonNull;
import perococco.snake.gui.game.SnakeDrawer;
import perococco.snake.gui.game.SnakeDrawerMap;

import java.util.*;
import java.util.stream.Collectors;

public class SnakeDrawers implements SnakeDrawerMap {

    private final Map<String, SnakeDrawer> drawers;

    public SnakeDrawers() {
        this.drawers = ServiceLoader.load(SnakeDrawer.class)
                                    .stream()
                                    .map(ServiceLoader.Provider::get)
                                    .collect(Collectors.toMap(SnakeDrawer::getName, s -> s));
    }

    @Override
    public @NonNull Optional<SnakeDrawer> getDrawer(@NonNull String name) {
        return Optional.ofNullable(drawers.get(name));
    }

    @Override
    public @NonNull List<String> getDrawerNames() {
        return new ArrayList<>(drawers.keySet());
    }
}
