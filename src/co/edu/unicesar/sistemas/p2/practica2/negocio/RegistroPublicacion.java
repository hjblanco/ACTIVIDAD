package co.edu.unicesar.sistemas.p2.practica2.negocio;

import co.edu.unicesar.sistemas.p2.practica2.datos.*;
import co.edu.unicesar.sistemas.p2.practica2.dominio.*;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import java.util.List;

public class RegistroPublicacion {
    
    private IAccesoDatos datos ;

    public RegistroPublicacion() {
        //this.datos= new ArrayAccesoDatos(100);
        //this.datos = new ListAccesoDatoss();
        this.datos = new ArchivoTexto();
        //this.datos = new ArchivoObjeto();
    }
    
    
    public void adicionarPublicacion(Publicacion p) throws ExcepcionAccesoDatos{
        if(p==null)
            throw new ExcepcionAccesoDatos("El registro a insertar es de tipo Null");
        if(p.getIsbn()==null)
            throw new ExcepcionAccesoDatos("El registro no tiene ISBN");
        
        this.datos.insertarPublicacion(p);
    }
    
    public List<Publicacion> consultarPublicaciones()throws ExcepcionAccesoDatos{
        return this.datos.leerPublicaciones();
    }
    
    public Publicacion buscarPublicacion(String isbn)throws ExcepcionAccesoDatos{
        return this.datos.buscarPublicacion(isbn);
    }
    
    public Publicacion eliminarPublicacion(Publicacion p)throws ExcepcionAccesoDatos{
        return this.datos.eliminarPublicacion(p);
    }
    
}
