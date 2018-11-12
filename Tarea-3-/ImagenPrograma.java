public class ImagenPrograma
{
    //atributos
    private int area; 
    private int [][] figura;
    private int fondo;
    //métodos
    public ImagenPrograma(int[][] matriz){
        area=0;
        fondo=matriz[0][0];
        figura=new int [matriz.length][matriz[0].length];
        reiniciar();
    }
     /**
     * Pinta el píxel actual de un color arbitrario e incrementa el área de la
     * figura en uno para indicar que ya se ha añadido
     *
     * @param  int f número de fila actual
     * @param  int c número de columna actual
     * 
     * @return No posee

     */
    public void añadir(int f,int c){
        figura[f][c]=4000;
        area=area+1;

    }

    /**
     * Compara si dos figuras son iguales entre sí
     *
     * @param  int[][] otra la matríz a comparar
     * 
     * @return No posee

     */
    public boolean comparar(int[][] otra){
        boolean iguales=true;
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){   
                if(otra[i][j]!=figura[i][j]){
                    iguales=false;
                }
            }
        }
        return iguales;
    }

    public int getArea(){
        return area;
    }

    /**
     * Reinicia una figura pintándola del color del fondo
     *
     * @param  No posee
     * 
     * @return No posee

     */
    public void reiniciar(){
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){
                figura[i][j]=fondo;
            }
        }
        area=0;
    }

    /**
     * Une la figura actual y la figura a comparar en una sola imagen del 
     * programa
     *
     * @param  int[][] otra la matríz a comparar
     * 
     * @return No posee

     */
    public void unir(int[][] otra){
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){
                if(otra[i][j]!=fondo){
                    añadir(i,j);
                }
            }
        }
    }
    
    /**
     * Pinta una copia de la otra figura en la figura actual
     *
     * @param  int[][] otra la otra matríz
     * 
     * @return No posee

     */
    public void pintar(int[][] otra){
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){
                if(figura[i][j]!=fondo){
                    figura[i][j]=otra[i][j];
                }
                }
            }
    }
    
    public int[][] getMatriz(){
        return figura;
    }
}
