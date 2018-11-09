
/**
 * Write a description of class Separar1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Separar1
{
    int[][] base;
    int cantFiguras;
    int grupos;
    ImagenPequeña[] figuras;
    ImagenPequeña[] figuras1;
    ImagenPequeña[][] comparar;
    ImagenPequeña[] resultado;
    ImagenPequeña[] separados;
    Imagen imagen;  
    public Separar1(int [][]base,int cantFiguras,int grupos)
    {
        this.base=base;
        this.cantFiguras=cantFiguras;        
        this.grupos=grupos;
        figuras=new ImagenPequeña[cantFiguras];
        figuras1=new ImagenPequeña[cantFiguras];
        separados=new ImagenPequeña[grupos];
        resultado=new ImagenPequeña[grupos];
        for(int j=0;j<grupos;j++){
            separados[j]=new ImagenPequeña(this.base);
            resultado[j]=new ImagenPequeña(this.base);
        }
        //separar();
    }

    public  ImagenPequeña[] separar(){
        for(int i=0;i<figuras.length;i++){
            figuras[i]=new ImagenPequeña(base);
            figuras1[i]=new ImagenPequeña(base);
        }
        int fondo=base[0][0];
        for(int i=0;i<base.length;i++){
            for(int j=0;j<base[0].length;j++){
                if(base[i][j]!=fondo){
                    figuras1[(base[i][j]/4000)-1].añadir(i,j);
                }
            }
        }
        return agrupar();
        //int x= (int)(Math.random()*3);

    }

    public  ImagenPequeña[] agrupar(){
        comparar=new ImagenPequeña[grupos][cantFiguras];
        iniciarMatriz(comparar);
        int masParecido=0;
        int distancia=0;
        int distanciaMenor=0;
        int[] representantes=escogerRepresentantes();
        copiarVector(figuras,figuras1);
        do{
            if(!comparar(resultado,separados)){
                pasarVector(separados,resultado);
                copiarVector(figuras,figuras1);
                representantes=nuevosRepresentantes();
                iniciarMatriz(comparar);                
            }
            
            for(int i=0;i<representantes.length;i++){
                añadirAlGrupo(i,representantes[i]);
            }
            for(int i=0;i<figuras.length;i++){
                distanciaMenor=base.length*base[0].length;
                for(int j=0;j<representantes.length;j++){
                    if(figuras[i].getArea()!=0){
                        distancia=Math.abs(figuras[i].getArea()-comparar[j][0].getArea());
                        if(distancia<=distanciaMenor){
                            distanciaMenor=distancia;
                            masParecido=j;
                        }
                    }
                }
                añadirAlGrupo(masParecido,i);
            }            
            for(int i=0;i<grupos;i++){    
                for(int j=0;j<cantFiguras;j++){            
                    resultado[i].unir(comparar[i][j].getMatriz());
                }
            }
            // for(int i=0;i<separados.length;i++){
            // imagen = new Imagen(resultado[i].getMatriz());
            // imagen.dibujar();
            // }
        }while(!comparar(resultado,separados));
        return resultado;
    }

    private void iniciarMatriz(ImagenPequeña[][] m){
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                m[i][j]=new ImagenPequeña(base);
            }
        }
    }

    private void pasarVector(ImagenPequeña[] v1,ImagenPequeña[] v2){
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
            v2[i].reiniciar();
        }
    }
    private void copiarVector(ImagenPequeña[] v1,ImagenPequeña[] v2){
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
        }
    }

    private boolean comparar(ImagenPequeña[] v1,ImagenPequeña[] v2){
        boolean valido=true;
        for(int i=0;i<v1.length;i++){
            if(!v1[i].comparar(v2[i].getMatriz())){
                valido=false;
            }
        }
        return valido;
    }

    private int[] nuevosRepresentantes(){
        int[] nuevosRep=new int[grupos];
        int sumaAreas;
        int areas;
        double promedioAreas;
        double distancia=0.0;
        double distanciaMenor=0.0;
        int nuevoRep=0;
        for(int i=0;i<comparar.length;i++){
            sumaAreas=0;
            promedioAreas=0;
            areas=0;
            for(int j=0;j<comparar[0].length;j++){ 
                if(comparar[i][j].getArea()!=0 ){
                    areas++;
                    sumaAreas+=comparar[i][j].getArea();
                }
            }
            promedioAreas=sumaAreas/areas;
            distanciaMenor=promedioAreas;
            nuevoRep=0;
            for(int j=0;j<comparar[0].length;j++){ 
                distancia=Math.abs(promedioAreas-comparar[i][j].getArea());
                if(distancia<distanciaMenor){
                    distanciaMenor=distancia;
                    nuevoRep=j;
                }
               
            }
            for(int k=0;k<figuras.length;k++){
                if(figuras[k].comparar(comparar[i][nuevoRep].getMatriz())){
                    nuevosRep[i]=k;
                }
            }
        }       
        return nuevosRep;
    }

    private int[] escogerRepresentantes(){
        int[] rep=new int[grupos];
        for(int i=0;i<grupos;i++){
            do{
                rep[i]= (int)(Math.random()*cantFiguras);               
            }while(!valido(rep,i,rep[i]));
        }        
        return rep;
    }

    private boolean valido(int[] v,int n,int num){
        boolean valido=true;
        for(int i=n-1;i>-1;i--){
            if(num==v[i]){
                valido=false;
            }
        }
        return valido;
    }

    private void añadirAlGrupo(int grupo,int añadir){
        int f=0;
        while(comparar[grupo][f].getArea()!=0){
            f++;
        };
        comparar[grupo][f].unir(figuras[añadir].getMatriz());
        figuras[añadir].reiniciar();
    }
}
