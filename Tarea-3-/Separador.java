
public class Separador
{
    //atributos
    private int[][] base;
    private int cantFiguras;
    private int grupos;
    private ImagenPrograma[] figuras;
    private ImagenPrograma[] figuras1;
    private ImagenPrograma[][] comparar;
    private ImagenPrograma[] resultado;
    private ImagenPrograma[] separados;
    private Imagen imagen;  
    //métodos
    public Separador(int [][]base,int cantFiguras,int grupos)
    {
        this.base=base;
        this.cantFiguras=cantFiguras;        
        this.grupos=grupos;
        figuras=new ImagenPrograma[cantFiguras];
        figuras1=new ImagenPrograma[cantFiguras];
        separados=new ImagenPrograma[grupos];
        resultado=new ImagenPrograma[grupos];
        
        for(int j=0;j<grupos;j++){
            separados[j]=new ImagenPrograma(this.base);
            resultado[j]=new ImagenPrograma(this.base);
        }
        
    }

    /**
     * Separa las figura de la imagen que se está analizando
     *
     * @param  No posee
     * 
     * @return ImagenPrograma[] las imágenes resultantes de la separación

     */
    public  ImagenPrograma[] separar(){
        for(int i=0;i<figuras.length;i++){
            figuras[i]=new ImagenPrograma(base);
            figuras1[i]=new ImagenPrograma(base);
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

    }
    
    /**
     * Agrupa las figuras de la imagen que se está analizando. Este método 
     * hace uso de escogerRepresentantes, copiarVector, nuevosRepresentantes,
     * pasarVector y anadirAlGrupo para realizar ese agrupamiento.
     *
     * @param  No posee
     * 
     * @return ImagenPrograma[] las imágenes resultantes del agrupamiento

     */
    public  ImagenPrograma[] agrupar(){
        comparar=new ImagenPrograma[grupos][cantFiguras];
        iniciarMatriz(comparar);
        int masParecido=0;
        int distancia=0;
        int distanciaMenor=0;
        int[] representantes=escogerRepresentantes();
        copiarVector(figuras,figuras1);
        //
        do{
            if(!comparar(resultado,separados)){
                pasarVector(separados,resultado);
                copiarVector(figuras,figuras1);
                representantes=nuevosRepresentantes();
                iniciarMatriz(comparar);                
            }
            
            for(int i=0;i<representantes.length;i++){
                anadirAlGrupo(i,representantes[i]);
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
                anadirAlGrupo(masParecido,i);
            }   
            
            for(int i=0;i<grupos;i++){    
                for(int j=0;j<cantFiguras;j++){            
                    resultado[i].unir(comparar[i][j].getMatriz());
                }
            }
            
        }
        while(!comparar(resultado,separados));
        
        return resultado;
    }
    
    /**
     * Inicializa una matríz de imágenes vacía utilizando la base para 
     * generar estas imágenes. Esta matríz representa los grupos de imágenes 
     * y las figuras dentro de ellas.
     *
     * @param  ImagenPrograma[][] m la matríz a modificar.
     * 
     * @return No posee

     */
    private void iniciarMatriz(ImagenPrograma[][] m){
        
        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[0].length;j++){
                m[i][j]=new ImagenPrograma(base);
            }
        }
        
    }

    private void pasarVector(ImagenPrograma[] v1,ImagenPrograma[] v2){
        
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
            v2[i].reiniciar();
        }
    }
    
    private void copiarVector(ImagenPrograma[] v1,ImagenPrograma[] v2){
        
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
        }
    }

    private boolean comparar(ImagenPrograma[] v1,ImagenPrograma[] v2){
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
        //-------------------------------------------------------------------
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
            //--------------------------------------------------------------
            promedioAreas=sumaAreas/areas;
            distanciaMenor=promedioAreas;
            nuevoRep=0;
            //--------------------------------------------------------------
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

    private void anadirAlGrupo(int grupo,int anadir){
        
        int f=0;
        
        while(comparar[grupo][f].getArea()!=0){
            f++;
        };
        
        comparar[grupo][f].unir(figuras[anadir].getMatriz());
        figuras[anadir].reiniciar();
        
    }
}
