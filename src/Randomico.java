import java.util.concurrent.ThreadLocalRandom;

public class Randomico {
    private int inteiroMinimo;
    private int inteiroMaximo;

    public static int obterRandomico(int inteiroMinimo, int inteiroMaximo) {
        return ThreadLocalRandom.current().nextInt(inteiroMinimo, inteiroMaximo + 1);
    }
}
