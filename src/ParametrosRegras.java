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

        return quantidadePlantacoes;
    }

    public static Map<String, Integer> duracaoEntrega(){

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
       
        return data;
    }

    public static Map<String, Integer> duracaoCarregamento(){ 
        
        Map<String, Integer> duracaoCarregamento = new HashMap<>() {{
            put("valorMin", 0);
            put("valorMax", 0);
        }};
        
        Pattern pattern1 = Pattern.compile("enche um caminhão entre");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        duracaoCarregamento.replace("valorMin", arrayTemp.get(0));
        duracaoCarregamento.replace("valorMax", arrayTemp.get(1));

        return duracaoCarregamento;
    }
    
    public static Map<String, Integer> duracaoRecepcao(){ 

        Map<String, Integer> duracaoRecepcao = new HashMap<>() {{
            put("valorMin", 0);
            put("valorMax", 0);
        }};
        
        Pattern pattern1 = Pattern.compile("recepção demora entre");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        duracaoRecepcao.replace("valorMin", arrayTemp.get(0));
        duracaoRecepcao.replace("valorMax", arrayTemp.get(1));

        return duracaoRecepcao;
    }

    public static Map<String, Integer> capacidadeCaminhao(){ 

        Map<String, Integer> capacidadeCaminhao = new HashMap<>() {{
            put("valorMin", 0);
            put("valorMax", 0);
        }};
        
        Pattern pattern1 = Pattern.compile("Varia entre \\d{1,2} até \\d{1,2} tonelada. de azeitona.");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        capacidadeCaminhao.replace("valorMin", arrayTemp.get(0));
        capacidadeCaminhao.replace("valorMax", arrayTemp.get(1));

        return capacidadeCaminhao;
    }

    public static Map<String, Integer> taxaCarregamentoDescarregamento(){ 
        
        Map<String, Integer> taxaCarregamentoDescarregamento = new HashMap<>() {{
            put("tempo", 0);
            put("carga", 0);
        }};
        
        Pattern pattern1 = Pattern.compile("\\d segundos corresponde a \\d toneladas");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        taxaCarregamentoDescarregamento.replace("tempo", arrayTemp.get(0));
        taxaCarregamentoDescarregamento.replace("carga", arrayTemp.get(1));

        return taxaCarregamentoDescarregamento;
    }

    public static long tempoExecucaoGeral(){ 
        
        Pattern pattern1 = Pattern.compile("minuto. de execução");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        long tempoExecucaoGeral = arrayTemp.get(0) * 1000 ;

        return tempoExecucaoGeral;
    }

    public static Map<String, Integer> filaCaminhao(){ 

        Map<String, Integer> filaCaminhao = new HashMap<>() {{
            put("caminhaoPlantacaoPara", 0);
            put("caminhaoPlantacaoRetoma", 0);
        }};
        
        Pattern pattern1 = Pattern.compile("caminh...+ em espera");
        Pattern pattern2 = Pattern.compile("\\d");
        
        List<Integer> arrayTemp = readRegras().
            stream().
            filter(pattern1.asPredicate()).
            flatMap(Pattern.compile(" ")::splitAsStream).
            filter(pattern2.asPredicate()).
            map(Integer::valueOf).
            collect(Collectors.toList());

        filaCaminhao.replace("caminhaoPlantacaoPara", arrayTemp.get(0));
        filaCaminhao.replace("caminhaoPlantacaoRetoma", arrayTemp.get(1));

        return filaCaminhao;
    }

}
