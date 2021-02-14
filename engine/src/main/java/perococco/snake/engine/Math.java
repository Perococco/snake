package perococco.snake.engine;

public class Math {

    public static int mod(int value, int size) {
        int result = value%size;
        if (result<0) {
            return result+size;
        }
        return result;
    }

}
