
/**
 * Write a description of class PruebaSergio here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class PruebaSergio
{   
    String entrada="1234.gif";
    public static final String direccion[]={"Norte","Noroeste","Oeste","Suroeste","Sur","Sureste","Este","Noreste"};
    public static final String direccionBasica[]={"Norte","Oeste","Sur","Este"};
    public static final int sumaFB[]={-1,0,1,0};
    public static final int sumaCB[]={0,-1,0,1,};
    public static final int sumaF[]={-1,-1,0,1,1,1,0,-1};
    public static final int sumaC[]={0,-1,-1,-1,0,1,1,1};  
    Imagen i = new Imagen(entrada);
    int original[][]=i.getMatriz();
    int nueva[][] =new int[original.length][original[0].length];
    int fondo=original[0][0];

    public void a(){
        System.out.print(original[0].length+"\n"+original.length);
    }

    public void Pintar(){
        Imagen imagen2 = new Imagen( nueva );
        for(int i=0;i<original.length;i++){
            for(int j=0;j<original[0].length;j++){
                if(i==0 &&j==0){
                    nueva [0][0]=fondo;
                }else{
                    if(revisar(i,j)){
                        nueva [i][j]=fondo;
                    }else{
                        nueva [i][j]=1000;
                    }
                }
            }
        }
        imagen2 = new Imagen( nueva );
        i.dibujar();
        imagen2.dibujar();
    }

    public boolean revisar(int x,int y){
        boolean valido=false;
        if(original[x][y]!=fondo){
            valido=false;
        }else{
        for(int i=0;i<sumaFB.length;i++){
            if(igualAFondo(x+sumaFB[i],y+sumaCB[i])){
                valido=true;
            }
        }
        if(!valido){
            for(int i=0;i<sumaF.length;i++){
                if(igualAFondo(x+sumaF[i],y+sumaC[i])){
                    valido=true;
                }
            }
        }
    }
        return valido;
    }

    public boolean igualAFondo(int x,int y){
        boolean valido=true;
        if(x>=0&&x<nueva.length&&y>=0&&y<nueva[0].length){
            if((nueva[x][y]!=fondo||nueva[x][y]==0)){
                valido=false;
            }
        }
        return valido;
    }
}

