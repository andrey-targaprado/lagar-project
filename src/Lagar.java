import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lagar implements Runnable {
    private DelayQueue<Caminhão> filaLagar;
    private LinkedBlockingQueue<String> filaMensagens;
    private Instant inicio;

    public Lagar(DelayQueue<Caminhão> filaLagar, LinkedBlockingQueue<String> filaMensagens) {
        this.filaLagar = filaLagar;
        this.filaMensagens = filaMensagens;
    }

    @Override
    public void run() {
        inicio = Instant.now();
        Caminhão entrega;
        String mensagem;

        try {
            while(true) {
                entrega = filaLagar.take();
                mensagem = 
                    entrega.getPlantacao() + " - " +
                    entrega.getVariedade() + " - " +
                    entrega.getTempoCarregamento() + " - " +
                    filaLagar.size();
                System.out.println(mensagem);
                
                Thread.sleep(entrega.getTempoCarregamento()*1000);

                filaMensagens.put(mensagem);

                if(Instant.now().isAfter(inicio.plus(1, ChronoUnit.MINUTES)) && filaLagar.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + " encerrou. <<<<<");
                    filaMensagens.put("");
                    return;
                }
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}
