import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParametrosRegras {

    private static List<String> readRegras() {
        Path caminho = Paths.get("arquivos/regras.txt");

        List<String> texto = new ArrayList<>();

        try {
            texto = Files.readAllLines(caminho);
        } catch(Exception e) {
            
        }

        return texto;
    }

    public static Map<String, Integer> quantidadePlantacoes(){

        Map<String, Integer> quantidadePlantacoes = new HashMap<>() {{
            put("Galega", 0);
            put("Cordovil", 0);
            put("Picual", 0);
        }};
        
        Pattern pattern = Pattern.compile("\\d{1,2} plantaç...");
        
        List<Integer> quantidade = readRegras().
            stream().
            filter(pattern.asPredicate()).
            flatMap(Pattern.compile(".plantaç...+")::splitAsStream).
            map(k -> k.replaceAll(" ", "")).
            map(Integer::valueOf).
            collect(Collectors.toList());
        
        quantidadePlantacoes.replace("Galega", quantidade.get(0));
        quantidadePlantacoes.replace("Cordovil", quantidade.get(1));
        quantidadePlantacoes.replace("Picual", quantidade.get(2));

        System.out.println(quantidadePlantacoes);
        return quantidadePlantacoes;
    }

    public static Map<String, Integer> distanciaPlantacoes(){

        Map<String, Integer> distanciaPlantacoes = new HashMap<>() {{
            put("Galega", 0);
            put("Cordovil", 0);
            put("Picual", 0);
        }};
        
        Pattern pattern = Pattern.compile("distância de \\d{1,2} segundos ");
        
        List<Integer> distancia = readRegras().
            stream().
            filter(pattern.asPredicate()).
            map(k -> k.replaceAll("(.*)distância", "")).
            map(k -> k.replaceAll("[^0-99]", "")).
            map(Integer::valueOf).
            collect(Collectors.toList());
         
        distanciaPlantacoes.replace("Galega", distancia.get(0));
        distanciaPlantacoes.replace("Cordovil", distancia.get(1));
        distanciaPlantacoes.replace("Picual", distancia.get(2));

        System.out.println(distanciaPlantacoes);
        return distanciaPlantacoes;
    }

    public static LocalDate dataRegras(){
        
        Pattern pattern = Pattern.compile("\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d");
        
        List<Integer> dataArray = readRegras().
            stream().
            filter(pattern.asPredicate()).
            map(k -> k.replaceAll("  +", "")).
            flatMap(Pattern.compile("\\/")::splitAsStream).
            map(Integer::valueOf).
            collect(Collectors.toList());
        
        int dia = dataArray.get(0);
        int mes = dataArray.get(1);
        int ano = dataArray.get(2);
        LocalDate data = LocalDate.of(ano, mes, dia);
        System.out.println(data);
       
        return data;
    }

    //Sem implementação
    public static Map<String, Integer> variedadesAzeite(){ return null; }
    public static Map<String, Integer> duracaoCarregamento(){ return null; }
    public static Map<String, Integer> duracaoRecepcao(){ return null; }

    public static void main(String[] args) throws Exception {
        //quantidadePlantacoes();
        System.out.println(quantidadePlantacoes().get("Cordovil").getClass());
        
        dataRegras();

       
    }
}
