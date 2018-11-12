
/**
 * Clase principal del programa. Este programa separa las figuras 
 * encontradas en una imagen en grupos de área similar con el
 * objetivo de encontrar grupos de figuras de un mismo tipo o
 * similares entre sí.
 * 
 * @author Sergio Martínez, Jorge Porras, Paula Po Wo On
 */
public class Main
{
    public static void main(String argumentos[]){
        
        if(argumentos.length == 1){
            String entrada = argumentos[0];
            AnalizadorFiguras analizador = new AnalizadorFiguras(entrada);
            analizador.ejecutar();
        }
 
        else if(argumentos.length == 2){
            String entrada = argumentos[0];
            int grupos = Integer.parseInt(argumentos[1]);
            AnalizadorFiguras analizador = new AnalizadorFiguras(entrada,grupos);
            analizador.ejecutar();
        }
        
        else{
            AnalizadorFiguras analizador = new AnalizadorFiguras();
            analizador.ejecutar();
        }

    }
}
