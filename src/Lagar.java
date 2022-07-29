import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lagar implements Runnable {
    private BlockingQueue<Map<String, String>> filaLagar;
    private LinkedBlockingQueue<String> filaMensagens;
    private Instant inicio;

    public Lagar(BlockingQueue<Map<String, String>> filaLagar, LinkedBlockingQueue<String> filaMensagens) {
        this.filaLagar = filaLagar;
        this.filaMensagens = filaMensagens;
    }

    @Override
    public void run() {
        inicio = Instant.now();
        Map entrega;
        String mensagem;

        try {
            while(true) {
                entrega = filaLagar.take();
                mensagem = 
                    entrega.get("plantacao") + " - " +
                    entrega.get("variedade") + " - " +
                    entrega.get("carregamento") + " - " +
                    filaLagar.size();
                System.out.println(mensagem);
                
                Thread.sleep(Integer.parseInt(entrega.get("carregamento").toString())*1000);

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