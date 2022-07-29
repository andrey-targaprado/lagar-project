import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {

        DelayQueue<Caminhão> filaLagar = new DelayQueue<>();
        LinkedBlockingQueue<String> filaMensagens = new LinkedBlockingQueue<>();
        
        Path pathRelatorio = Paths.get("arquivos/relatorio-" + ParametrosRegras.dataRegras().getYear() + ".txt");
        filaMensagens.put(ParametrosRegras.dataRegras().getDayOfMonth() + "/" + ParametrosRegras.dataRegras().getMonthValue() + "\n");

        new Thread(new Plantacao(filaLagar, "Galega", ParametrosRegras.duracaoEntrega().get("Galega")), "Plantacao 1").start();
        new Thread(new Plantacao(filaLagar, "Galega", ParametrosRegras.duracaoEntrega().get("Galega")), "Plantacao 2").start();
        new Thread(new Plantacao(filaLagar, "Cordovil", ParametrosRegras.duracaoEntrega().get("Cordovil")), "Plantacao 3").start();
        new Thread(new Plantacao(filaLagar, "Cordovil", ParametrosRegras.duracaoEntrega().get("Cordovil")), "Plantacao 4").start();
        new Thread(new Plantacao(filaLagar, "Picual", ParametrosRegras.duracaoEntrega().get("Picual")), "Plantacao 5").start();

        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 1").start();
        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 2").start();
        new Thread(new Lagar(filaLagar, filaMensagens), "Recebimento 3").start();

        new Thread(new Relatorio(filaMensagens, pathRelatorio), "Relatorio").start();
        
    }
}
