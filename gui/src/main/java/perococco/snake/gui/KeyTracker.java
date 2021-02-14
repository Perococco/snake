package perococco.snake.gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class KeyTracker {

    private Scene target;

    private final EventHandler<KeyEvent> handler = this::handleEvent;

    private final List<EventHandler<KeyEvent>> listeners = new ArrayList<>();

    public void attach(@NonNull Scene scene) {
        this.detach();
        this.target = scene;
        target.addEventFilter(KeyEvent.ANY, handler);
    }

    public void detach() {
        if (target != null) {
            target.removeEventFilter(KeyEvent.ANY,handler);
        }
        this.target = null;
    }

    public void removeListener(@NonNull EventHandler<KeyEvent> listener) {
        this.listeners.remove(listener);
    }

    public void addListener(@NonNull EventHandler<KeyEvent> listener) {
        this.listeners.add(listener);
    }

    private void handleEvent(KeyEvent e) {
        int size = this.listeners.size();
        for (int j = size-1; j >=0 ; j--) {
            if (!e.isConsumed()) {
                listeners.get(j).handle(e);
            }
        }
    }


}
