package co.edu.unicesar.sistemas.p2.practica2.vista;

import co.edu.unicesar.sistemas.p2.practica2.dominio.AudioLibro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Libro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import co.edu.unicesar.sistemas.p2.practica2.negocio.RegistroPublicacion;
import java.util.List;
import java.util.Scanner;

public class VistaConsola {
    private String[] titulos = {"1. Insertar Publicacion",
                                "2. Leer Publicaciones",
                                "3. Buscar Publicacion",
                                "4.Eliminar Publicacion",
                                "0. Salir del Aplicativo"};
    private int opcion;
    private Scanner lector;
    private RegistroPublicacion registro;

    public VistaConsola() {
        this.lector = new Scanner(System.in);
        this.registro = new RegistroPublicacion();
        
    }
    
    public void ejecutarMenu(){
        
        do{
            this.imprimirMenu();
            this.leerOpcion();
            this.evaluarOpcion();
            
        }while(this.opcion!=0);
    }
    
    public void imprimirMenu(){
        System.out.println(" MENU DE APLICACION \n\n");
        for(String t: this.titulos){
            System.out.println(t);
        }
    }
    
    public void leerOpcion(){
        boolean excepcion = true;
        do{
            try{
                System.out.print("\nSeleccione su opcion: ");   
                this.opcion = this.lector.nextInt();
                excepcion=false;
            
            }catch(java.util.InputMismatchException ime){
                System.out.println("La opcion debe ser valor entero, registre nuevamente");
                excepcion=true;
                this.lector.nextLine();
            }    
        }while(excepcion);
        
    }
    
    public void evaluarOpcion(){
        
        switch(this.opcion){
            case 1: this.vistaInsertar();
                    break;
            case 2: this.vistaConsultar();
                    break;
            case 3: this.vistaBuscar();
                    break;
            case 4: this.vistaEliminar();
                    break;
            case 0: System.out.println("La aplicacion a finalizado");
                    break;
            default: System.out.println("Opcion no valida");
            
        }
    }
    
    public void vistaInsertar(){
        System.out.println(this.titulos[this.opcion-1]);
        Libro l1 = new  Libro(100,2021,"111","Libro","PROGRAMACION ORIENTADA A OBJETOS","AUTOR 1",2021,20000);
        Libro l2 = new  Libro(500,2021,"222","Libro","ESTRUCTURA DE DATOS","AUTOR 2",2021,50000);
        AudioLibro a1 = new AudioLibro(400,100,"AVI","A111","Audio","ALGORITMOS", "AUTOR 1",2000,1000);
        try {
            registro.adicionarPublicacion(l1);
            registro.adicionarPublicacion(l2);
            registro.adicionarPublicacion(a1);
        } catch (ExcepcionAccesoDatos ex) {
            System.out.println(ex);
        }
    }
    
    public void vistaConsultar(){
        System.out.println(this.titulos[this.opcion-1]);
        try {
            imprimirListado(registro.consultarPublicaciones());
        } catch (ExcepcionAccesoDatos ex) {
            System.out.println(ex);
        }
    }
    
    public void vistaBuscar(){
        System.out.println(this.titulos[this.opcion-1]);
        Libro l1 = new  Libro(100,2021,"111","Libro","PROGRAMACION ORIENTADA A OBJETOS","AUTOR 1",2021,20000);
        try {
            System.out.println(registro.buscarPublicacion(l1.getIsbn()));
        } catch (ExcepcionAccesoDatos ex) {
            System.out.println(ex);
        }
    }
    
    public void vistaEliminar(){
        System.out.println(this.titulos[this.opcion-1]);
        Libro l1 = new  Libro(100,2021,"111","Libro","PROGRAMACION ORIENTADA A OBJETOS","AUTOR 1",2021,20000);
        try {
            System.out.println(registro.eliminarPublicacion(l1));
        } catch (ExcepcionAccesoDatos ex) {
            System.out.println(ex);
        }
    }
    
    public static void imprimirListado(List<Publicacion> lista){
        for(Publicacion p : lista){
             if (p instanceof Libro){
                Libro libro =(Libro)p;
                 System.out.println(libro);
            }else{
               AudioLibro audioLibro = (AudioLibro)p;
               System.out.println(audioLibro);
            }  
        }
    }
    
}
