import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Plantacao implements Runnable {
    private BlockingQueue<Map<String, String>> fila;
    private String variedade;
    private int tempoEntrega;

    public Plantacao(BlockingQueue<Map<String, String>> fila, String variedade, int tempoEntrega) {
        this.fila = fila;
        this.variedade = variedade;
        this.tempoEntrega = tempoEntrega;
    }

    @Override
    public void run() {
        long tempoCarregamento = 0;
        try {
            while (true) {
                // Enche um caminhão entre 2 a 8 segundos
                tempoCarregamento = (long)(Math.random()*(8000-2000)+2000);
                Thread.sleep(tempoCarregamento);
                
                // Considerar o tempo de deslocamento até o lagar (fila)
                fila.put(Map.of("variedade", variedade,
                                "carregamento", String.valueOf(tempoCarregamento),
                                "plantacao", Thread.currentThread().getName()
                ));
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
    
}