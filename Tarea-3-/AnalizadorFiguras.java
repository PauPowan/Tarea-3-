
public class AnalizadorFiguras
{
    //atributos
    public static final int sumaFB[]={-1,0,1,0};
    public static final int sumaCB[]={0,-1,0,1,};
    public static final int sumaF[]={-1,-1,0,1,1,1,0,-1};
    public static final int sumaC[]={0,-1,-1,-1,0,1,1,1}; 
    private String entrada;
    private Imagen imagen;
    private int original[][];    
    private int fondo;
    private int cantidadDeFiguras;
    private int grupos;
    private ImagenPrograma[] separados;
    private Separador separador;
    
    //métodos
    public AnalizadorFiguras()
    {
        //valores por default
        entrada = "ej.gif";
        grupos = 2;
        //------------------------------------------------------------------
        imagen = new Imagen(entrada);
        original = imagen.getMatriz();
        fondo = original[0][0];
        cantidadDeFiguras = 0;
        grupos = 2;
    }
    
    public AnalizadorFiguras(String entrada,int grupos)
    {
        //valores por parámetro
        this.entrada = entrada;
        this.grupos = grupos;
        //------------------------------------------------------------------
        imagen = new Imagen(entrada);
        original = imagen.getMatriz();
        fondo = original[0][0];
        cantidadDeFiguras = 0;
    }
    
    public AnalizadorFiguras(String entrada)
    {
        //solo entrada por parámetro
        this.entrada = entrada;
        //------------------------------------------------------------------
        grupos = 2;
        imagen = new Imagen(entrada);
        original = imagen.getMatriz();
        fondo = original[0][0];
        cantidadDeFiguras = 0;
    }
    
    /**
     * Método que ejecuta la mayoría de procesos necesarios para el análisis
     * de la imagen. Utiliza una copia de la matríz original en la que separa
     * los pixeles que componen las figuras de aquellos que componen el fondo.
     * Para esta separación recorre la matríz en dos direcciones para evitar
     * obviar píxeles. Luego procede a invocar al separador para generar cada
     * figura por separado. Este método muestra gráficamente las diferentes
     * partes del proceso (Imagen original, imagen procesada y grupos de imágenes)
     * 
     * @param No posee
     * @return No posee
     * 
     */
   
    public  void ejecutar(){

        int copia1[][] =new int[original.length][original[0].length];
        Imagen imagen2 = new Imagen(copia1);
        copia1[0][0] = fondo;
        //------------------------------------------------------------------
        for(int i=0;i<original.length;i++){
            for(int j=0;j<original[0].length;j++){
                pintarFondo(i,j,copia1);
            }
        }
        
        for(int i=original.length-1;i>-1;i--){
            for(int j=original[0].length-1;j>-1;j--){
                pintarFondo(i,j,copia1);
            }
        }
        //-------------------------------------------------------------------
        pintar(copia1);
        imagen.dibujar();
        imagen2= new Imagen(copia1);
        imagen2.dibujar();
        //En caso de que el usuario ingrese más grupos de los permitidos
        if(grupos>cantidadDeFiguras){
            grupos=cantidadDeFiguras;
        }  
        //------------------------------------------------------------------
        separador = new Separador(copia1,cantidadDeFiguras,grupos);
        separados= separador.separar();
        //------------------------------------------------------------------
        
        for(int i=0;i<separados.length;i++){
            separados[i].pintar(original);
            imagen = new Imagen(separados[i].getMatriz());
            imagen.dibujar();
        }
        
    }
    
    /**
     * Primer método para la recursión que "pinta" las figuras y lleva un 
     * control del número de ellas.
     *
     * @param  int matriz[][] la matríz que se encuentra analizando
     * @return No posee

     */
    public void pintar(int matriz[][]){
        Imagen imagen2= new Imagen(matriz);
        for(int i=0;i<original.length;i++){
            for(int j=0;j<original[0].length;j++){
                if(matriz[i][j]==0){
                    cantidadDeFiguras++;
                    pintar(i,j,cantidadDeFiguras,matriz);
                }
            }
        }
    }
    /**
     * Para cada figura que encuentre le asigna un color nuevo en el que se
     * pintará para diferenciarla y la seguirá recorriendo hasta pintarla en 
     * su totalidad
     *
     * @param  int f número de fila actual
     * @param  int c número de columna actual
     * @param  int numeroFigura el número de la figura que se está pintando
     * @param  int[][] matriz la matríz que se encuentra analizando
     * 
     * @return No posee
     * 
     */
    public void pintar(int f,int c ,int numeroFigura,int matriz[][]){
        //try
        matriz[f][c]=numeroFigura*4000;
        int filaNueva=0;
        int columnaNueva=0;
        for(int i=0;i<sumaF.length;i++){
            filaNueva=f+sumaF[i];
            columnaNueva=c+sumaC[i];
            if(valido(filaNueva,columnaNueva,0,matriz)){                
                pintar(filaNueva,columnaNueva,numeroFigura,matriz); 
            }
        }

    }
    
    /**
     * Primer método para la recursión que "pinta" el fondo de la
     * imagen en la matríz para separarlo de las figuras. A partir de un píxel
     * del color del fondo, analiza los píxeles adyacentes en todas las 
     * direcciones.
     *
     * @param  int f número de fila actual
     * @param  int c número de columna actual
     * @param  int[][] matriz la matríz que se encuentra analizando
     * 
     * @return No posee
     * 
     */
    public void pintarFondo(int f,int c,int matriz[][]){
        if(matriz[f][c]==fondo){
            for(int i=0;i<sumaFB.length;i++){
                pintarFondo(f,c,i,matriz);                
            }
        }
    }
    /**
     * Comprueba si el píxel actual se encuentra dentro de los límites de la 
     * imagen y si es del color solicitado.
     *
     * @param  int f número de fila actual
     * @param  int c número de columna actual
     * @param  int color una representación numérica del color indicado
     * @param  int[][] matriz la matríz que se encuentra analizando
     * 
     * @return boolean una variable booleana que representa si el pixel actual
     * presenta las características solicitadas.
     * 
     */
    public boolean valido(int f,int c,int color,int matriz[][] ){
        return 0<=f && f<original.length && 0<=c && c<original[0].length && matriz[f][c]==color;
    }
    
    /**
     * Comprueba si el píxel adyancente al actual en la dirección indicada es
     * parte del fondo y lo pinta en caso de que sea así.
     *
     * @param  int f número de fila actual
     * @param  int c número de columna actual
     * @param  int d un índice que indica la dirección en que se mueve esta iteración
     * @param  int[][] matriz la matríz que se encuentra analizando
     * 
     * @return No posee
     * 
     */
    public void pintarFondo(int f,int c,int d,int matriz[][]){
        int filaNueva=f+sumaFB[d];
        int columnaNueva=c+sumaCB[d];
        if(valido(filaNueva,columnaNueva,fondo,original)){
            matriz[filaNueva][columnaNueva]=fondo;
            pintarFondo(filaNueva,columnaNueva,d,matriz);
        }
    }
    
}

