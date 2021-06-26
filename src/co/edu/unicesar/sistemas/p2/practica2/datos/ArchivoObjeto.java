/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicesar.sistemas.p2.practica2.datos;

import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NITRO 5
 */
public class ArchivoObjeto implements IAccesoDatos{

    private File archivo;
    private FileInputStream leerO;
    private FileOutputStream escribirO;
    
    
    public ArchivoObjeto(String name) {
        this.archivo = new File(name);
    }

     public ArchivoObjeto() {
        this("Publicaciones.dat");
    }
     
      
     
    
    
    @Override
    public void insertarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
       List<Publicacion> lista = leerPublicaciones();
       lista.add(p);
       ObjectOutputStream oos=null;
       try{ 
         this.escribirO = new FileOutputStream(this.archivo);
         oos = new ObjectOutputStream(this.escribirO);
         oos.writeObject(lista);
       }catch(IOException fne){
           throw new ExcepcionAccesoDatos("Error al abrir archivo en escritura");
       }
       finally{
           try{
            if(oos!=null)
                oos.close();
           }catch(IOException ioe){
               throw new NullPointerException("Error al cerrar archivo en escritura");
           } 
       }
        
    }

    @Override
    public List<Publicacion> leerPublicaciones() throws ExcepcionAccesoDatos {
        List<Publicacion> lista = new ArrayList();
        if(this.archivo.exists()){
            ObjectInputStream ois = null;
            try{
                this.leerO = new FileInputStream(this.archivo);
                ois = new ObjectInputStream(this.leerO);
                lista = (ArrayList<Publicacion>) ois.readObject();
                return lista;

            }catch(IOException ioe){
                throw new ExcepcionAccesoDatos("Error al abrir archivo en lectura");
            } catch (ClassNotFoundException ex) {
                throw new ExcepcionAccesoDatos("Error al leer archivo ");
            }
        }else{
            return lista;
        }
    }

    @Override
    public Publicacion buscarPublicacion(String isbn) throws ExcepcionAccesoDatos {
        List<Publicacion> lista = leerPublicaciones();
        Publicacion encontrada = null;
        for(Publicacion publicacion: lista){
            if(publicacion.getIsbn().equalsIgnoreCase(isbn)){
                encontrada = publicacion;
                break;
            }
        }
        return encontrada;
    }
    
    private void actualizarArchivo(File nvoArchivo) throws ExcepcionAccesoDatos{
         if(nvoArchivo.exists()){
             try {
                 nvoArchivo.createNewFile();
             } catch (IOException ex) {
                 throw new ExcepcionAccesoDatos("El Archivo no fue creado");
             }
         }
         
         if(this.archivo.delete()){
             if(!nvoArchivo.renameTo(this.archivo)){
                 throw new ExcepcionAccesoDatos("El Archivo temporal no fue renombrado");
             }
         }
         else
             throw new ExcepcionAccesoDatos("El Archivo original no fue eliminado");
        
    }

    @Override
    public Publicacion eliminarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
        List<Publicacion> lista = leerPublicaciones();
        Publicacion eliminada = null;
        ArchivoObjeto tmp = new ArchivoObjeto("Temporal.dat");
        
        for(Publicacion publicacion: lista){
            if(publicacion.getIsbn().equalsIgnoreCase(p.getIsbn())){
                eliminada = publicacion;
                //lista.remove(p);
            }else{
                this.insertarPublicacion(p);
            }
        }
        
    
        this.actualizarArchivo(tmp.archivo);
        return  eliminada;
//        for (Publicacion p1 : lista) {
//            this.insertarPublicacion(p1);
//        }

    }

    
}
