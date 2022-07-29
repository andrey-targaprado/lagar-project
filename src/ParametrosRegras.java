import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class ParametrosRegras {

    private static List<String> readRegras() {
        Path caminho = Paths.get("arquivo/regras.txt");

        List<String> texto = new ArrayList<>();

        try {
            texto = Files.readAllLines(caminho);
        } catch(Exception e) {
            
        }

        return texto;
    }

    public static Map<String, String> quantidadePlantacoes(){

        Map<String, String> quantidadePlantacoes = new HashMap<>() {{
            put("Galega", "0");
            put("Cordovil", "0");
            put("Picual", "0");
        }};
        
        Pattern pattern = Pattern.compile("\\d{1,2} plantaç...");
        
        List<String> quantidade = readRegras().
            stream().
            filter(pattern.asPredicate()).
            flatMap(Pattern.compile(".plantaç...+")::splitAsStream).
            map(k -> k.replaceAll(" ", "")).
            collect(Collectors.toList());
        
        quantidadePlantacoes.replace("Galega", quantidade.get(0));
        quantidadePlantacoes.replace("Cordovil", quantidade.get(1));
        quantidadePlantacoes.replace("Picual", quantidade.get(2));

        System.out.println(quantidadePlantacoes);
        return quantidadePlantacoes;
    }

    public static void main(String[] args) throws Exception {
        quantidadePlantacoes();

                  
    }
}
