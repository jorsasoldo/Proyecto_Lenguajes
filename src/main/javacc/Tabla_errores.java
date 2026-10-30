import java.util.ArrayList;
import java.util.List;

public class Tabla_errores
{
    private final List<Error_lexico> errores = new ArrayList<>();

    //Indica si el token es de error (osea no debe pasar al parser)
    public static boolean es_error(int tipo)
    {
        return tipo == ParserConstants.UNKNOWN || tipo == ParserConstants.ERR_STRING_UNCLOSED || tipo == ParserConstants.ERR_NUMBER || tipo == ParserConstants.ERR_ID || tipo == ParserConstants.ERR_CHAR_LENGTH || tipo == ParserConstants.ERR_CHAR_UNCLOSED || tipo == ParserConstants.ERR_COMMENT_UNCLOSED;
    }

    //Revisa un token si es error (o un entero fuera de rango) lo registra y retorena true
    public boolean procesar(Token t)
    {
        if(es_error(t.kind) || (t.kind == ParserConstants.INT_LITERAL && entero_fuera_de_rango(t.image)))
        {
            registrar(t);

            return true;
        }

        return false;
    }


    //Un int es de 32 bits: max 2147483647
    private static boolean entero_fuera_de_rango(String texto)
    {
        try
        {
            Integer.parseInt(texto);

            return false;
        }

        catch(NumberFormatException e)
        {
            return true;
        }
    }

    //Acorta el lexema para que la tabla no se rompa con textos largos o de varias lineas
    private static String resumir(String lexema)
    {
        String limpio = lexema.replaceAll("[\\r\\n\\t]+", " ");

        return limpio.length() > 14 ? limpio.substring(0, 11) + "..." : limpio;
    }


    //Clasifica el token del tipo de error y lo agrega a la tabla
    public void registrar(Token t)
    {
        String codigo, descripcion;

        switch(t.kind)
        {
            case ParserConstants.UNKNOWN:
                codigo = "LEX-01";
                descripcion = "Caracter no reconocido";
                break;

            case ParserConstants.ERR_STRING_UNCLOSED:
                codigo = "LEX-02";
                descripcion = "Cadena sin cerrar (falta la comilla doble final)";
                break;

            case ParserConstants.ERR_NUMBER:
                codigo = "LEX-03";
                descripcion = "Numero mal formado";
                break;

            case ParserConstants.ERR_ID:
                codigo = "LEX-04";
                descripcion = "Identificador mal formado (no puede empezar con digito)";
                break;

            case ParserConstants.ERR_CHAR_LENGTH:
                codigo = "LEX-05";
                descripcion = "Caracter invalido (debe contener exactamente un caracter)";
                break;

            case ParserConstants.ERR_CHAR_UNCLOSED:
                codigo = "LEX-06";
                descripcion = "Caracter sin cerrar (falta la comilla simple final)";
                break;

            case ParserConstants.INT_LITERAL:
                codigo = "LEX-07";
                descripcion = "Entero fuera de rango (maximo 2147483647)";
                break;

            case ParserConstants.ERR_COMMENT_UNCLOSED:
                codigo = "LEX-08";
                descripcion = "Comentario de bloque sin cerrar (falta */)";
                break;

            default:
                codigo = "LEX-??";
                descripcion = "Error lexico desconocido";
        }

        errores.add(new Error_lexico(codigo, descripcion, resumir(t.image), t.beginLine, t.beginColumn));
    }

    public boolean hayErrores()
    {
        return !errores.isEmpty();
    }

    public void imprimir()
    {
        System.out.println();

        System.out.println("=== TABLA DE ERRORES ===");

        if(errores.isEmpty())
        {
            System.out.println("Sin errores lexicos.");
            return;
        }

        System.out.printf("%-8s %-16s %-10s %s%n", "CODIGO", "LEXEMA", "POSICION", "DESCRIPCION");

        for(Error_lexico e : errores)
            System.out.printf("%-8s %-16s L%-3d C%-4d %s%n", e.codigo, e.lexema, e.linea, e.columna, e.descripcion);

        System.out.println("Total de errores: " + errores.size());
    }
}