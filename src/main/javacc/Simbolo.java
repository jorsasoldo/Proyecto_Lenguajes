import java.util.ArrayList;
import java.util.List;

//Una entrada de la tabla de simbolos contiene su identificador y todas sus apariciones
public class Simbolo
{
    public final int indice;
    public final String lexema;
    public final String categoria;

    private final List<int[]> apariciones = new ArrayList<>();

    public Simbolo(int indice, String lexema, String categoria)
    {
        this.indice = indice;
        this.lexema = lexema;
        this.categoria = categoria;
    }

    public void agregar_aparicion(int linea, int columna)
    {
        apariciones.add(new int[]{linea, columna});
    }

    public int veces()
    {
        return apariciones.size();
    }

    public int primera_linea()
    {
        return apariciones.get(0)[0];
    }

    public int primera_columna()
    {
        return apariciones.get(0)[1];
    }

    //Devuelve las apariciones como un texto tipo "L1 C11, L3 C5"
    public String apariciones_texto()
    {
        StringBuilder sb = new StringBuilder();

        for(int[] a : apariciones)
        {
            if(sb.length() > 0)
                sb.append(", ");

            sb.append("L").append(a[0]).append(" C").append(a[1]);
        }

        return sb.toString();
    }
}