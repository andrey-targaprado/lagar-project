import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.LinkedBlockingQueue;

public class Relatorio implements Runnable {
    private LinkedBlockingQueue<String> filaMensagens;
    private Path pathRelatorio;
    private int contadorEncerramentos;
    
    public Relatorio(LinkedBlockingQueue<String> filaMensagens, Path pathRelatorio) {
        this.filaMensagens = filaMensagens;
        this.pathRelatorio = pathRelatorio;
    }

    @Override
    public void run() {
        contadorEncerramentos = 0;
        String mensagem;
        try {

            if(Files.exists(pathRelatorio)) {
                Files.delete(pathRelatorio);
            }

            Files.createFile(pathRelatorio);

            while (true) {
                mensagem = filaMensagens.take();
                if(mensagem.equals("")) {
                    contadorEncerramentos++;
                }
                if(contadorEncerramentos == 3) {
                    return;
                }
                
                mensagem = "\n"
                        + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " - "
                        + " >> " + mensagem;

                Files.write(pathRelatorio, mensagem.getBytes(), StandardOpenOption.APPEND);
            }

        } catch(Exception e) {
            Thread.currentThread().interrupt();
        }
    }
    
}