
public class prueba2
{
    // instance variables - replace the example below with your own
    public static final int sumaFB[]={-1,0,1,0};
    public static final int sumaCB[]={0,-1,0,1,};
    public static final int sumaF[]={-1,-1,0,1,1,1,0,-1};
    public static final int sumaC[]={0,-1,-1,-1,0,1,1,1}; 
    String entrada = "pequeña1.gif";
    Imagen imagen = new Imagen(entrada);
    int original[][] = imagen.getMatriz();    
    int fondo=original[0][0];
    int cantidadDeFiguras=0;
    int grupos=10;
    Separar1 x;

    public prueba2()
    {
        prueba2();
    }

    public  void prueba2(){

        cantidadDeFiguras=0;
        int copia1[][]=new int[original.length][original[0].length];
        Imagen imagen2= new Imagen(copia1);
        copia1[0][0]=fondo;

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
        pintar(copia1);
        imagen.dibujar();
        imagen2= new Imagen(copia1);
        imagen2.dibujar();
        if(grupos>cantidadDeFiguras){
            grupos=cantidadDeFiguras;
        }
        //x=new Separar1(copia1,cantidadDeFiguras,grupos);

    }

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

    public void pintar(int f,int c ,int numeroFigura,int matriz[][]){
        matriz[f][c]=numeroFigura*4000;
        int filaNueva=0;
        int columnaNueva=0;
        for(int i=0;i<sumaF.length;i++){
            filaNueva=f+sumaF[i];
            columnaNueva=c+sumaC[i];
            if(valido(filaNueva,columnaNueva,0,matriz)){                
                pintar(filaNueva,columnaNueva,numeroFigura,matriz);//aqui se pasa de verga 
            }
        }

    }

    public void pintarFondo(int f,int c,int matriz[][]){
        if(matriz[f][c]==fondo){
            for(int i=0;i<sumaFB.length;i++){
                pintarFondo(f,c,i,matriz);                
            }
        }
    }

    public boolean valido(int f,int c,int color,int matriz[][] ){
        return 0<=f&&f<original.length&&0<=c&&c<original[0].length&&matriz[f][c]==color;
    }

    public void pintarFondo(int f,int c,int d,int matriz[][]){
        int filaNueva=f+sumaFB[d];
        int columnaNueva=c+sumaCB[d];
        if(valido(filaNueva,columnaNueva,fondo,original)){
            matriz[filaNueva][columnaNueva]=fondo;
            pintarFondo(filaNueva,columnaNueva,d,matriz);
        }
    }

}//java -cp .;Imagen.jar Main -Xss64m 

