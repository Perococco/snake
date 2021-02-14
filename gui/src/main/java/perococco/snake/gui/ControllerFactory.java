package perococco.snake.gui;

import javafx.util.Callback;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.snake.gui.game.GameController;
import perococco.snake.gui.game.GameManager;
import perococco.snake.gui.game.ROGameModel;
import perococco.snake.gui.game.SnakeDrawerMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RequiredArgsConstructor
public class ControllerFactory implements Callback<Class<?>, Object> {

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull GameManager gameManager;

    private final @NonNull KeyTracker keyTracker;

    @Override
    public Object call(Class<?> param) {
        try {
            return createController(param.getConstructors()[0]);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Object createController(Constructor<?> constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final var arguments = Arrays.stream(constructor.getParameterTypes())
                                     .sequential().map(this::createParameter)
                                     .toArray();
        return constructor.newInstance(arguments);
    }

    private Object createParameter(Class<?> aClass) {
        if (ApplicationCloser.class.isAssignableFrom(aClass)) {
            return this.applicationCloser;
        }
        if (SnakeDrawerMap.class.isAssignableFrom(aClass)) {
            return this.gameManager.getSnakeDrawers();
        }
        if (GameManager.class.isAssignableFrom(aClass)) {
            return this.gameManager;
        }
        if (KeyTracker.class.isAssignableFrom(aClass)) {
            return this.keyTracker;
        }
        if (ROGameModel.class.isAssignableFrom(aClass)) {
            return this.gameManager.getModel();
        }
        throw new IllegalStateException("Cannot provide argument of type "+aClass);
    }


}
