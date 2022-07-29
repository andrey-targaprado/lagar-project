import java.util.concurrent.ThreadLocalRandom;

public abstract class Randomico {
    private int inteiroMinimo;
    private int inteiroMaximo;

    public int obterRandomico(int inteiroMinimo, int inteiroMaximo) {
        return ThreadLocalRandom.current().nextInt(inteiroMinimo, inteiroMaximo + 1);
    }

    /*
    public static void main(String[] args) {
        for(int i = 0; i <= 100; i++)
        System.out.println(obterRandomico(2, 8));
    }
    */
}
