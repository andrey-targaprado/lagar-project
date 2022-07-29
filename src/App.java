import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {
        Map<String, String> regras = new HashMap<>();
        regras.put("ano", "1991");
        regras.put("mes", "02");
        regras.put("dia", "24");

        DelayQueue<Caminhão> filaLagar = new DelayQueue<>();
        LinkedBlockingQueue<String> filaMensagens = new LinkedBlockingQueue<>();
        
        Path pathRelatorio = Paths.get("arquivos/relatorio-" + regras.get("ano") + ".txt");
        filaMensagens.put(regras.get("dia") + "/" + regras.get("mes") + "\n");

        new Thread(new Plantacao(filaLagar, "Galega", 4), "Plantacao 1").start();
        new Thread(new Plantacao(filaLagar, "Galega", 4), "Plantacao 2").start();
        new Thread(new Plantacao(filaLagar, "Cordovil", 3), "Plantacao 3").start();
        new Thread(new Plantacao(filaLagar, "Cordovil", 3), "Plantacao 4").start();
        new Thread(new Plantacao(filaLagar, "Picual", 2), "Plantacao 5").start();

        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 1").start();
        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 2").start();
        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 3").start();

        new Thread(new Relatorio(filaMensagens, pathRelatorio), "Relatorio").start();
        
    }
}