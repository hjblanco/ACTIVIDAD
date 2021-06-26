/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicesar.sistemas.p2.practica2.vista;

import co.edu.unicesar.sistemas.p2.practica2.dominio.AudioLibro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Libro;
import co.edu.unicesar.sistemas.p2.practica2.dominio.Publicacion;
import co.edu.unicesar.sistemas.p2.practica2.excepciones.ExcepcionAccesoDatos;
import co.edu.unicesar.sistemas.p2.practica2.negocio.RegistroPublicacion;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NITRO 5
 */
public class FormGestionPublicacion extends JFrame implements ActionListener{
    private Container container;
    private JTable tabla;
    private JScrollPane scrollpane;
    private String titulos[] = { "ISBN", "TIPO", "TITULO", "AUTOR","AÑO","COSTO","NUMERO PAGINAS","EDICION" };
    private DefaultTableModel defaultTableModel;
    private JLabel labelIsbn, labelTipo, labelTitulo,labelAutor,labelAño,labelCosto,labelPaginas,labelEdicion, labelDuracion,labelPeso,labelFormato;
    private JTextField textoIsbn, textoTitulo, textoAutor, textoAño, textoCosto,textoPaginas,textoEdicion,textoDuracion,textoPeso,textoFormato;
    private JComboBox comboTipo;
    private JButton botonRegistrar, botonEliminar, botonConsultar, botonBuscar;
    private RegistroPublicacion registro;
    
    public FormGestionPublicacion() {
        super("GESTION PUBLICACIONES");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.initComponents();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.botonRegistrar){
            this.registrarPublicacion();
        }else if(e.getSource()==this.botonEliminar){
            this.eliminarPublicacion();
        }else if(e.getSource()==this.botonConsultar){
            this.consultarPublicacion();
        }else if(e.getSource()==this.botonBuscar){
            this.buscarPublicacion();
        }
    }
    
    public void crearLabels(){
        this.labelIsbn = new JLabel("Isbn: ");
        this.labelIsbn.setBounds(110, 10, 500, 40);
        
        this.labelTipo = new JLabel("Tipo: ");
        this.labelTipo.setBounds(110, 30, 500, 40);
        
        this.labelTitulo = new JLabel("Titulo: ");
        this.labelTitulo.setBounds(110, 54, 500, 40);
        
        this.labelAutor = new JLabel("Autor: ");
        this.labelAutor.setBounds(110, 74, 500, 40);
        
        this.labelAño = new JLabel("Año: ");
        this.labelAño.setBounds(110, 98, 500, 40);
        
        this.labelCosto = new JLabel("Costo: ");
        this.labelCosto.setBounds(110, 120, 500, 40);
        
        this.labelPaginas = new JLabel("N Paginas: ");
        this.labelPaginas.setBounds(80, 140, 500, 40);
        
        this.labelEdicion = new JLabel("Edicion: ");
        this.labelEdicion.setBounds(90, 160, 500, 40);
        
        this.labelDuracion = new JLabel("Duracion: ");
        this.labelDuracion.setBounds(90, 180, 500, 40);
        
        this.labelPeso = new JLabel("Peso: ");
        this.labelPeso.setBounds(110, 200, 500, 40);
        
        this.labelFormato = new JLabel("Formato: ");
        this.labelFormato.setBounds(90, 220, 500, 40);
    }
    
    public void crearCampos(){
        this.textoIsbn = new JTextField(null);
        this.textoIsbn.setBounds(150, 18, 200, 20);
        
        this.comboTipo = new JComboBox();
        this.comboTipo.addItem("");
        this.comboTipo.addItem("Libro");
        this.comboTipo.addItem("Audio");
        this.comboTipo.setBounds(150, 40, 200, 20);
        this.comboTipo.addActionListener(this);
        
        this.textoTitulo = new JTextField(null);
        this.textoTitulo.setBounds(150, 64, 200, 20);
        
        this.textoAutor = new JTextField(null);
        this.textoAutor.setBounds(150, 86, 200, 20);
        
        this.textoAño = new JTextField(null);
        this.textoAño.setBounds(150, 108, 200, 20);
        
        this.textoCosto = new JTextField(null);
        this.textoCosto.setBounds(150, 130, 200, 20);
        
        this.textoPaginas = new JTextField(null);
        this.textoPaginas.setBounds(150, 150, 200, 20);
        
        this.textoEdicion = new JTextField(null);
        this.textoEdicion.setBounds(150, 170, 200, 20);
        
        this.textoDuracion = new JTextField(null);
        this.textoDuracion.setBounds(150, 190, 200, 20);
        
        this.textoPeso = new JTextField(null);
        this.textoPeso.setBounds(150, 210, 200, 20);
        
        this.textoFormato = new JTextField(null);
        this.textoFormato.setBounds(150, 230, 200, 20);
    }
    
    public void crearBotones(){
        this.botonRegistrar = new JButton("Registrar");
        this.botonRegistrar.setBounds(100, 280, 200, 25);
        this.botonRegistrar.addActionListener(this);
        
        this.botonEliminar = new JButton("Eliminar");
        this.botonEliminar.setBounds(300, 280, 200, 25);
        this.botonEliminar.addActionListener(this);
        
        this.botonConsultar = new JButton("Consultar");
        this.botonConsultar.setBounds(500, 280, 200, 25);
        this.botonConsultar.addActionListener(this);
        
        this.botonBuscar = new JButton("Buscar");
        this.botonBuscar.setBounds(400, 18, 100, 25);
        this.botonBuscar.addActionListener(this);
    }
    
    public void crearTabla(){
        Object[][] data=new Object[0][0];
        getContentPane().setLayout(null);
        this.scrollpane = new JScrollPane();
        this.scrollpane.setBounds(27, 330, 740, 200);
        getContentPane().add(this.scrollpane);
        
        this.tabla = new JTable(); 
        this.defaultTableModel = new DefaultTableModel();
        this.tabla.setPreferredScrollableViewportSize(new Dimension(500, 80));
        this.scrollpane.setViewportView(this.tabla);
        this.defaultTableModel.setColumnIdentifiers(titulos);
        this.defaultTableModel = new DefaultTableModel(data, titulos); 
        this.tabla.setModel(defaultTableModel);
    }
    
    public void crearContenedor(){
        this.container.add(this.labelIsbn);
        this.container.add(this.textoIsbn);
        
        this.container.add(this.labelTipo);
        this.container.add(this.comboTipo);
        
        this.container.add(this.labelTitulo);
        this.container.add(this.textoTitulo);
        
        this.container.add(this.labelAutor);
        this.container.add(this.textoAutor);
       
        this.container.add(this.labelAño);
        this.container.add(this.textoAño);
        
        this.container.add(this.labelCosto);
        this.container.add(this.textoCosto);
        
        this.container.add(this.labelPaginas);
        this.container.add(this.textoPaginas);
        
        this.container.add(this.labelEdicion);
        this.container.add(this.textoEdicion);
        
        this.container.add(this.labelDuracion);
        this.container.add(this.textoDuracion);
        
        this.container.add(this.labelPeso);
        this.container.add(this.textoPeso);
        
        this.container.add(this.labelFormato);
        this.container.add(this.textoFormato);
        
        this.container.add(this.botonRegistrar);
        this.container.add(this.botonEliminar);
        this.container.add(this.botonConsultar);
        this.container.add(this.botonBuscar);
        this.container.add(this.scrollpane);
        //this.container.add(this.tabla);
    }
   
    public void initComponents(){
        this.container=this.getContentPane();
        this.container.setLayout(null);
        this.crearLabels();
        this.crearCampos();
        this.crearBotones();
        this.crearTabla();
        this.crearContenedor();
        this.registro = new RegistroPublicacion();
    }
    
    
    public void registrarPublicacion(){
        Publicacion publicacion = null;
        try {
            String isbn = this.textoIsbn.getText();
            String tipo = this.comboTipo.getSelectedItem().toString();
            String titulo = this.textoTitulo.getText();
            String autor = this.textoAutor.getText();
            int año = Integer.parseInt(this.textoAño.getText());
            double costo = Double.parseDouble(this.textoCosto.getText());
           
            int opcion = this.comboTipo.getSelectedIndex();
            
            if(opcion==1){
                 int nPaginas = Integer.parseInt(this.textoPaginas.getText());
                 int edicion = Integer.parseInt(this.textoEdicion.getText());
                 publicacion = new  Libro(nPaginas,edicion,isbn,tipo,titulo,autor,año,costo);   
            }else if(opcion==2){
                 double duracion = Double.parseDouble(this.textoDuracion.getText());
                 double peso = Double.parseDouble(this.textoPeso.getText());
                 String formato = this.textoFormato.getText();
                 publicacion = new AudioLibro(duracion,peso,formato,isbn,tipo,titulo,autor,año,costo);
            }else{
               JOptionPane.showMessageDialog(this, "Seleccione tipo", "Mensaje", JOptionPane.ERROR_MESSAGE);
            }
            
            if(publicacion != null){
                registro.adicionarPublicacion(publicacion);
                JOptionPane.showMessageDialog(this, "Publicacion registrada exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ExcepcionAccesoDatos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void buscarPublicacion(){
        Publicacion p = null;
        try {
            p = registro.buscarPublicacion(this.textoIsbn.getText());
            
            if(p instanceof Libro){
               Libro libro = (Libro)p;
                this.textoIsbn.setText(libro.getIsbn());
                this.comboTipo.setSelectedItem(libro.getTipo());
                this.textoTitulo.setText(libro.getTitulo());
                this.textoAutor.setText(libro.getAutor());
                this.textoAño.setText(String.valueOf(libro.getAnio()));
                this.textoCosto.setText(String.valueOf(libro.getCosto()));
                this.textoPaginas.setText(String.valueOf(libro.getnPaginas()));
                this.textoEdicion.setText(String.valueOf(libro.getEdicion()));
               
            }else if(p instanceof AudioLibro){
               AudioLibro audio = (AudioLibro)p;
                this.textoIsbn.setText(audio.getIsbn());
                this.comboTipo.setSelectedItem(audio.getTipo());
                this.textoTitulo.setText(audio.getTitulo());
                this.textoAutor.setText(audio.getAutor());
                this.textoAño.setText(String.valueOf(audio.getAnio()));
                this.textoCosto.setText(String.valueOf(audio.getCosto()));
                this.textoDuracion.setText(String.valueOf(audio.getDuracion()));
                this.textoPeso.setText(String.valueOf(audio.getPeso()));
                this.textoFormato.setText(audio.getFormato());
            }
            
            if(p!=null){
                JOptionPane.showMessageDialog(this, "Publicacion buscada exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (ExcepcionAccesoDatos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarPublicacion(){
        try {
             Publicacion p = registro.buscarPublicacion(this.textoIsbn.getText());
             Publicacion eliminada = registro.eliminarPublicacion(p);
             if(eliminada!=null){
                 JOptionPane.showMessageDialog(this, "Publicacion eliminada exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
             }
        } catch (ExcepcionAccesoDatos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void consultarPublicacion(){
        try {
            ArrayList<Publicacion> lista = (ArrayList<Publicacion>) registro.consultarPublicaciones();
            this.tabla.removeAll();
            this.defaultTableModel.setRowCount(0);
            
             if(lista!=null){
                for(Publicacion p: lista){
                   if(p instanceof Libro){
                       Libro l = (Libro)p;
                       String linea[]= {l.getIsbn(),l.getTipo(),l.getTitulo(),l.getAutor(),String.valueOf(l.getAnio()),String.valueOf(l.getCosto()),String.valueOf(l.getnPaginas()),String.valueOf(l.getEdicion())};
                       this.defaultTableModel.addRow(linea);
                   }else if(p instanceof AudioLibro){
                       AudioLibro a = (AudioLibro)p;
                       String linea[]= {a.getIsbn(),a.getTipo(),a.getTitulo(),a.getAutor(),String.valueOf(a.getAnio()),String.valueOf(a.getCosto()),String.valueOf(a.getDuracion()),String.valueOf(a.getPeso()),String.valueOf(a.getFormato())};
                       this.defaultTableModel.addRow(linea);
                   }
                }
                this.tabla.setModel(defaultTableModel);
             }
        } catch (ExcepcionAccesoDatos ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
