/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicesar.sistemas.p2.practica2.datos;

import co.edu.unicesar.sistemas.p2.practica2.dominio.AudioLibro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Libro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author NITRO 5
 */
public class ArchivoTexto implements IAccesoDatos{
   
    private File archivo;
    private FileWriter aEscritura;
    private Scanner aLectura;

    public ArchivoTexto(String name) {
        this.archivo = new File(name);
    }

    public ArchivoTexto() {
        this("Publicaciones.txt");
    }
    
    
    @Override
    public void insertarPublicacion(Publicacion p) throws ExcepcionAccesoDatos {
        PrintWriter pw = null;
        try{
            this.aEscritura = new FileWriter(this.archivo, true);
            pw = new PrintWriter(this.aEscritura);
            if (p instanceof Libro){
                 Libro libro =(Libro)p;
                 pw.println(libro.toString());
            }else{
                AudioLibro audioLibro = (AudioLibro)p;
                pw.println(audioLibro.toString());
            }
            
        }catch(IOException ioe){
            throw new ExcepcionAccesoDatos("No se pudo ingresar la publicacion");
        }
        finally{
            if(pw!=null)
                pw.close();
            if(this.aEscritura!=null)
                try {
                    this.aEscritura.close();
            } catch (IOException ex) {
                throw new ExcepcionAccesoDatos("Error al cerrar el archivo");
            }
        }
    }
    
     private Publicacion crearPublicacion(String linea){
        String datos[] = linea.split(";");
        Publicacion publicacion = null;
        if(datos[1].equalsIgnoreCase("Audio")){
             AudioLibro audioLibro = new AudioLibro();
             audioLibro.setIsbn(datos[0]);
             audioLibro.setTipo(datos[1]);
             audioLibro.setTitulo(datos[2]);
             audioLibro.setAutor(datos[3]);
             audioLibro.setAnio(Integer.parseInt(datos[4]));
             audioLibro.setCosto(Double.parseDouble(datos[5]));
             audioLibro.setDuracion(Double.parseDouble(datos[6]));
             audioLibro.setPeso(Double.parseDouble(datos[7]));
             audioLibro.setFormato(datos[8]);
             publicacion = audioLibro;
        }else{
             Libro libro = new Libro();
             libro.setIsbn(datos[0]);
             libro.setTipo(datos[1]);
             libro.setTitulo(datos[2]);
             libro.setAutor(datos[3]);
             libro.setAnio(Integer.parseInt(datos[4]));
             libro.setCosto(Double.parseDouble(datos[5]));
             libro.setnPaginas(Integer.parseInt(datos[6]));
             libro.setEdicion(Integer.parseInt(datos[7]));
             publicacion = libro;
        } 
        return publicacion;
    }
    

    @Override
    public List<Publicacion> leerPublicaciones() throws ExcepcionAccesoDatos {
        List<Publicacion> lista = new ArrayList();
        try{
            this.aLectura = new Scanner(this.archivo);
            while(this.aLectura.hasNext()){
                String linea = this.aLectura.nextLine();
                Publicacion publicacion = this.crearPublicacion(linea);
                lista.add(publicacion);
            }
            return lista;
        }catch(IOException ioe){
            throw new ExcepcionAccesoDatos("No existen archivo de publicaciones");
        }
        finally{
            if(this.aLectura!=null)
                this.aLectura.close();
        }
    }

    @Override
    public Publicacion buscarPublicacion(String isbn) throws ExcepcionAccesoDatos {
        Publicacion encontrada = null;
           try{
                this.aLectura = new Scanner(this.archivo);
                while(this.aLectura.hasNext()){
                    String linea = this.aLectura.nextLine();
                    Publicacion publicacion = this.crearPublicacion(linea);
                    if(publicacion.getIsbn().equalsIgnoreCase(isbn)){
                        encontrada = publicacion;
                        break;
                    }
                }
                return encontrada;
           }
           catch(IOException ioe){
               throw new ExcepcionAccesoDatos("No se pudo buscar la publicacion");
           }
           finally{
               if(this.aEscritura!=null)
                   this.aLectura.close();
           }
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
        Publicacion eliminada = null;
        try{
             this.aLectura = new Scanner(this.archivo);
             ArchivoTexto tmp = new ArchivoTexto("Temporal.txt");
             while(this.aLectura.hasNext()){
                 Publicacion publicacion = this.crearPublicacion(this.aLectura.nextLine());
                 
                 if(publicacion.getIsbn().equalsIgnoreCase(p.getIsbn())){
                     eliminada = publicacion;
                 }else{
                     tmp.insertarPublicacion(publicacion);
                 } 
             }
             this.aLectura.close();
             this.actualizarArchivo(tmp.archivo);
             return  eliminada;
        }
        catch(IOException ioe){
            throw new ExcepcionAccesoDatos("No se pudo eliminar la publicacion");
        }
        finally{
            this.aLectura.close();
        }
    }
    
}
