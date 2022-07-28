import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Plantacao implements Runnable {
    private BlockingQueue<Map<String, String>> fila;
    private String variedade;
    private int tempoEntrega;
    private Instant inicio;

    public Plantacao(BlockingQueue<Map<String, String>> fila, String variedade, int tempoEntrega) {
        this.fila = fila;
        this.variedade = variedade;
        this.tempoEntrega = tempoEntrega;
    }

    @Override
    public void run() {
        inicio = Instant.now();
        long tempoCarregamento = 0;
        try {
            while (true) {
                // Carrega um caminhão entre 2 e 8 segundos
                tempoCarregamento = (long) (Math.random() * (8 - 2) + 2);
                Thread.sleep(tempoCarregamento * 1000);

                // Considerar o tempo de deslocamento até o lagar (fila)
                fila.put(Map.of("variedade", variedade,
                        "carregamento", String.valueOf(tempoCarregamento),
                        "plantacao", Thread.currentThread().getName()));
                
                if(Instant.now().isAfter(inicio.plus(2, ChronoUnit.MINUTES))) {
                    System.out.println(Thread.currentThread().getName() + " encerrou. <<<<<");
                    return;
                }
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}