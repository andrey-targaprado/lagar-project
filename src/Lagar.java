import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Lagar implements Runnable {
    BlockingQueue<Map<String, String>> fila;

    public Lagar(BlockingQueue<Map<String, String>> fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        Map entrega;
        try {
            while(true) {
                entrega = fila.take();
                System.out.println(
                    entrega.get("plantacao") + " - " +
                    entrega.get("variedade") + " - " +
                    entrega.get("carregamento") + " - " +
                    fila.size());
                Thread.sleep(Integer.parseInt(entrega.get("carregamento").toString()));
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
    
}
