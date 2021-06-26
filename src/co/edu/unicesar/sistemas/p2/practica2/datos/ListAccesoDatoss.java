/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicesar.sistemas.p2.practica2.datos;

import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jairo
 * Implementar metodos utilizando colecciones de datos, clase List y ArrayList
 */
public class ListAccesoDatoss implements IAccesoDatos {

    private List<Publicacion> lista;
    
    public ListAccesoDatoss() {
        this.lista = new ArrayList();
    }
    
    @Override
    public void insertarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
       if(!this.lista.add(p)){
             throw new ExcepcionAccesoDatos("No se pudo ingresar la publicacion");
        } 
    }

    @Override
    public List<Publicacion> leerPublicaciones() throws ExcepcionAccesoDatos {
        if(lista.size() > 0){
            return this.lista;
        }
        else{
            throw new ExcepcionAccesoDatos("No hay publicaciones registradas");
        }
    }

    @Override
    public Publicacion buscarPublicacion(String isbn) throws ExcepcionAccesoDatos {
        if(lista.size() < 0)
           throw new ExcepcionAccesoDatos("No hay publicaciones registradas"); 
        if(isbn.isEmpty())
           throw new ExcepcionAccesoDatos("Parametro no permitido para la busqueda"); 
        
       Publicacion publicacion = null;
        for(Publicacion p1: this.lista){
            if(p1.getIsbn().equalsIgnoreCase(isbn)){
                publicacion = p1;
                break;
            }
        }
        return publicacion;
    }

    @Override
    public Publicacion eliminarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
        Iterator<Publicacion> iterator = this.lista.iterator();
        Publicacion eliminada = null;
        while(iterator.hasNext()){
             Publicacion p1 = iterator.next();
             if(p1.getIsbn().equalsIgnoreCase(p.getIsbn())){
                  iterator.remove();
                  eliminada = p1;
                  break;
             }
        }
        return  eliminada;
    }
    
}
