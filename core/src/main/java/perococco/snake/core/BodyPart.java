package perococco.snake.core;

import lombok.NonNull;
import lombok.Value;

@Value
public class BodyPart {

    @NonNull Point position;
    @NonNull Direction to;
}
