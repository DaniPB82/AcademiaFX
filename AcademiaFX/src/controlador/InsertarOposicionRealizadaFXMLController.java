/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.ConvocatoriaOposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class InsertarOposicionRealizadaFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //LABEL
    @FXML private Label lblNombre;
    
    //TEXTFIELD
    @FXML private TextField txtDni;
    
    //DATEPICKER
    @FXML private DatePicker dpInicio;
    
    //BUTTON
    @FXML private Button btnInsertar;
    @FXML private Button btnCancelar;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<String> listaOposiciones = FXCollections.observableArrayList();
    private ObservableList<Date> listaFechasInicio = FXCollections.observableArrayList();
    
    //OBJETOS
    private ConvocatoriaOposicion co = ConvocatoriasOposicionesFXMLController.convO;
    
    //VARIABLES
    String nombre;
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(btnInsertar)) {
            String dni = txtDni.getText().toUpperCase();
            if(dni.length() == 9) {
                int estado = Alumno.comprobarDnis(con, dni);
                if(estado == 0) {
                    insertarAlumnoConvocatoriaOposicion(dni, lblNombre.getText().toUpperCase(), Date.valueOf(dpInicio.getValue()));
                    insertarAlumnoEnOposicion(con, dni.toUpperCase(), lblNombre.getText().toUpperCase());
                    JOptionPane.showMessageDialog(null, "Alumno introducido en convocatoria de oposición con éxito");
                    txtDni.setText("");
                }
                else {
                        JOptionPane.showMessageDialog(null, "El DNI no existe");
                    }
            }
            else {
                JOptionPane.showMessageDialog(null, "Introduce un DNI válido de 9 dígitos");
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertar);
        }
        
    }
    
    private void insertarAlumnoConvocatoriaOposicion(String dni, String nombre, Date inicio) {
        try {
            String sql = "INSERT INTO ALUMNOS_CONVOCATORIA_OPOSICION VALUES (?, ?, ?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setDate(3, inicio);
            pst.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(InsertarOposicionRealizadaFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insertarAlumnoEnOposicion(ConexionMySQL con, String dni, String nombre) {
        try {
            String sql = "INSERT INTO OPOSICION_REALIZADA VALUES (?, ?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(InsertarOposicionRealizadaFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNombre.setText(co.getNombre());
        dpInicio.setValue(co.getInicio().toLocalDate());
        
    }   
    
}
