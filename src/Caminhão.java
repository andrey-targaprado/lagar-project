import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Caminhão implements Delayed {
    private String variedade;
    private String plantacao;
    private long instanteInicial;    

    public Caminhão(String variedade, String plantacao, long atrasoMilis) {
        this.variedade = variedade;
        this.plantacao = plantacao;
        instanteInicial = System.currentTimeMillis() + atrasoMilis;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diferenca = instanteInicial - System.currentTimeMillis();
        return unit.convert(diferenca, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Math.toIntExact(this.instanteInicial - ((Caminhão) o).instanteInicial);
    }

}
