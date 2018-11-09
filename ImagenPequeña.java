
/**
 * Write a description of class ImagenPequeña here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ImagenPequeña
{
    // instance variables - replace the example below with your own
    int area; 
    int [][] figura;
    int fondo;
    public ImagenPequeña(int[][] matriz)
    {
        area=0;
        fondo=matriz[0][0];
        figura=new int [matriz.length][matriz[0].length];
        reiniciar();
    }

    public void añadir(int f,int c){
        figura[f][c]=4000;
        area=area+1;

    }

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

    public void reiniciar(){
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){
                figura[i][j]=fondo;
            }
        }
        area=0;
    }

    public void unir(int[][] otra){
        for(int i=0;i<figura.length;i++){
            for(int j=0;j<figura[0].length;j++){
                if(otra[i][j]!=fondo){
                    añadir(i,j);
                }
            }
        }
    }
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
