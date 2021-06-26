package co.edu.unicesar.sistemas.p2.practica2.datos;

import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import java.util.ArrayList;
import java.util.List;

public class ArrayAccesoDatos implements IAccesoDatos {
    private Publicacion array[];
    private int n;

    public ArrayAccesoDatos(int tam) {
        this.array = new Publicacion[tam];
        this.n=0;
    }
    
    
    @Override
    public void insertarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
        if(n<this.array.length){ //
            this.array[this.n]=p;
            this.n++;
        }
        else{
            throw new ExcepcionAccesoDatos("El limite de tamaño del array esta completo");
        }
    }

    @Override
    public List<Publicacion> leerPublicaciones() throws ExcepcionAccesoDatos {
        if(this.n>0){
            List<Publicacion> publicaciones = new ArrayList();
            for(int i=0; i<this.n;i++){ 
                publicaciones.add(this.array[i]);
            }
            return publicaciones;
        }
        else{
            throw new ExcepcionAccesoDatos("No hay publicaciones registradas");
        }
    }

    @Override
    public Publicacion buscarPublicacion(String isbn) throws ExcepcionAccesoDatos {
        if(this.n==0)
           throw new ExcepcionAccesoDatos("No hay publicaciones registradas"); 
        if(isbn.isEmpty())
           throw new ExcepcionAccesoDatos("Parametro no permitido para la busqueda"); 
        
        Publicacion buscada=null;
        for(int i=0; i<this.n;i++){
            Publicacion pub = this.array[i];
            if(pub.getIsbn().equals(isbn)){
                buscada = pub;
                break;
            }
        }
        return buscada;
    }

    @Override
    public Publicacion eliminarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
        return null;
    }
    
}
