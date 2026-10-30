public class Error_lexico
{
    public final String codigo;
    public final String descripcion;
    public final String lexema;
    public final int linea;
    public final int columna;

    public Error_lexico(String codigo, String descripcion, String lexema, int linea, int columna)
    {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
    }
}