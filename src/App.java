import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Map<String, String>> fila = new LinkedBlockingQueue<>(12);
        
        new Thread(new Plantacao(fila, "Galega", 4)).start();
        new Thread(new Plantacao(fila, "Galega", 4)).start();
        new Thread(new Plantacao(fila, "Cordovil", 3)).start();
        new Thread(new Plantacao(fila, "Cordovil", 3)).start();
        new Thread(new Plantacao(fila, "Picual", 2)).start();

        new Thread(new Lagar(fila)).start();
        new Thread(new Lagar(fila)).start();
        new Thread(new Lagar(fila)).start();
        
    }
}