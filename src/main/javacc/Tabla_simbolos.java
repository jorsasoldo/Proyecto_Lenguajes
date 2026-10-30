import java.util.LinkedHashMap;
import java.util.Map;

//Tabla de simbolos hace una entrada por identificador distinto
public class Tabla_simbolos
{
    //Lo huice un Linkedhashmap porque hace busqueda rapida y conserva el orden en que aparecen
    private final Map<String, Simbolo> tabla = new LinkedHashMap<>();

    //Registra la aparicion con un id (crea la entrada si es nueva o suma una aparicion si ya existe)
    public Simbolo registrar(Token t)
    {
        Simbolo s = tabla.get(t.image);

        if(s == null)
        {
            s = new Simbolo(tabla.size() + 1, t.image, "ID");

            tabla.put(t.image, s);
        }

        s.agregar_aparicion(t.beginLine, t.beginColumn);

        return s;
    }

    //Busca un simbolo por su lexema (null si no existe)
    public Simbolo buscar(String lexema)
    {
        return tabla.get(lexema);
    }

    public int tamano()
    {
        return tabla.size();
    }

    public void imprimir()
    {
        System.out.println();

        System.out.println("=== TABLA DE SIMBOLOS ===");

        if(tabla.isEmpty())
        {
            System.out.println("Sin identificadores.");
            return;
        }

        System.out.printf("%-4s %-16s %-10s %-13s %s%n", "No.", "LEXEMA", "CATEGORIA", "1RA APARICION", "VECES / APARICIONES");

        for(Simbolo s : tabla.values())
            System.out.printf("%-4d %-16s %-10s L%-3d C%-8d %d: %s%n", s.indice, s.lexema, s.categoria, s.primera_linea(), s.primera_columna(), s.veces(), s.apariciones_texto());

        System.out.println("Total de simbolos: " + tabla.size());
    }
}