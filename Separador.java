
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
     * Agrupa las figuras de la imagen que se está analizando. Busca un
     * grupo de representantes, si los representantes no son los indicados,
     * buscará nuevos representantes. Esta elección de representantes se 
     * basa en un promedio de las áreas de las figuras y elige la figura que
     * tenga el área más cercana a ese promedio. Este método hace uso de 
     * escogerRepresentantes,copiarVector, nuevosRepresentantes, pasarVector 
     * y anadirAlGrupo para realizar ese agrupamiento.
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
        while(!comparar(resultado,separados)); //la condición avisa si los últimos dos son iguales
        
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
    
    /**
     * Pasa una imagen de un vector a otro, reiniciando la imagen en el proceso
     *
     * @param  ImagenPrograma[] v1 el vector destino
     * @param  ImagenPrograma[] v2 el vector origen
     * 
     * @return No posee

     */
    private void pasarVector(ImagenPrograma[] v1,ImagenPrograma[] v2){
        
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
            v2[i].reiniciar();
        }
    }
    
    /**
     * Copia una imagen de un vector a otro, sin reiniciarla
     *
     * @param  ImagenPrograma[] v1 el vector destino
     * @param  ImagenPrograma[] v2 el vector origen
     * 
     * @return No posee

     */
    private void copiarVector(ImagenPrograma[] v1,ImagenPrograma[] v2){
        
        for(int i=0;i<v1.length;i++){
            v1[i].reiniciar();
            v1[i].unir(v2[i].getMatriz());
        }
    }

    /**
     * Compara si una imagen en un vector es igual a otra en su misma posición en un vector diferente
     *
     * @param  ImagenPrograma[] v1 el vector destino
     * @param  ImagenPrograma[] v2 el vector origen
     * 
     * @return No posee

     */
    private boolean comparar(ImagenPrograma[] v1,ImagenPrograma[] v2){
        boolean valido=true;
        
        for(int i=0;i<v1.length;i++){
            if(!v1[i].comparar(v2[i].getMatriz())){
                valido=false;
            }
        }
        
        return valido;
    }

    /**
     * Elige los nuevos representantes para cada grupo. Calcula un promedio de las áreas de un grupo 
     * y toma como representante a la figura con el área más cercana a ese promedio
     *
     * @param  No posee
     * 
     * @return int[] un vector que contiene los índices de los nuevos representantes de cada grupo 

     */
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

    /**
     * Elige representantes para los grupos al azar
     *
     * @param  No posee
     * 
     * @return int[] un vector que contiene los índices de los nuevos representantes de cada grupo 

     */
    private int[] escogerRepresentantes(){
        
        int[] rep=new int[grupos];
        for(int i=0;i<grupos;i++){
            do{
                rep[i]= (int)(Math.random()*cantFiguras);               
            }while(!valido(rep,i,rep[i]));
        }        
        
        return rep;
    }

    /**
     * Identifica si un número proveído es diferente a todos los números
     * de un vector en índices menores al proveído
     *
     * @param  int[] v el vector cuyos elementos se compararán
     * @param  int n el índice desde el que se comparará
     * @param int num un número a comparar
     * 
     * @return boolean un valor que representa si el número es válido

     */
    private boolean valido(int[] v,int n,int num){
        
        boolean valido=true;
        
        for(int i=n-1;i>-1;i--){
            if(num==v[i]){
                valido=false;
            }
        }
        
        return valido;
    }

    /**
     * Añade una figura a un grupo
     * 
     * @param  int grupo el grupo al que se va a añadir
     * @param  int anadir el índice de la figura en el vector de figuras actual
     * 
     * @return No posee

     */
    private void anadirAlGrupo(int grupo,int anadir){
        
        int f=0;
        
        while(comparar[grupo][f].getArea()!=0){
            f++;
        };
        
        comparar[grupo][f].unir(figuras[anadir].getMatriz());
        figuras[anadir].reiniciar();
        
    }
}
