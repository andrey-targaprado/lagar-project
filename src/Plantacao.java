import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.DelayQueue;

public class Plantacao implements Runnable {
    private DelayQueue<Caminhão> fila;
    private String variedade;
    private long tempoEntrega;
    private Instant inicio;

    public Plantacao(DelayQueue<Caminhão> fila, String variedade, long tempoEntrega) {
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
                tempoCarregamento = Randomico.obterRandomico(2, 8);
                Thread.sleep(tempoCarregamento * 1000);

                fila.put(new Caminhão(variedade, Thread.currentThread().getName(), tempoCarregamento, tempoEntrega*1000));
                
                if(Instant.now().isAfter(inicio.plus(1, ChronoUnit.MINUTES))) {
                    System.out.println(Thread.currentThread().getName() + " encerrou. <<<<<");
                    return;
                }
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
