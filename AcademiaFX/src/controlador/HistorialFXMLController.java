/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import modelo.CambiarVentana;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.ConexionMySQL;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class HistorialFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTAREA
    @FXML private TextArea txtHistorial;
    
    //LABELS
    @FXML private Label lblDni;
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    
    //CHECKBOX
    @FXML private CheckBox cbxEditar;
    
    //BUTTONS
    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //OBJETOS
    private Alumno c = AlumnosFXMLController.a;
    private Alumno d = BuscarAlumnoFXMLController.b;
    
    //EVENTO CLICK EN CHECKBOX
    @FXML
    private void editarTextArea(ActionEvent event) {
        
        if(cbxEditar.isSelected()) {
            txtHistorial.setEditable(true);
        }
        
        else {
            txtHistorial.setEditable(false);
        }
    }
    
    //EVENTO CLICK EN MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnGuardar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnGuardar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnGuardar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnGuardar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnGuardar);
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
        }
        
        else if(evt.equals(btnGuardar)) {
            if(AlumnosFXMLController.estado == 1) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de guardar el historial del alumno con DNI: " + c.getDni() + " ?");
                if(opcion == 0) {
                    Alumno.guardarHistorial(con, txtHistorial.getText(), c.getDni());
                    JOptionPane.showMessageDialog(null, "Historial guardado con éxito.");
                    ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
                }
            }
            else if(BuscarAlumnoFXMLController.estado == 1) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de guardar el historial del alumno con DNI: " + d.getDni() + " ?");
                if(opcion == 0) {
                    Alumno.guardarHistorial(con, txtHistorial.getText(), d.getDni());
                    JOptionPane.showMessageDialog(null, "Historial guardado con éxito.");
                    ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
                }
            }
            
            
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(AlumnosFXMLController.estado == 1) {
            lblDni.setText(c.getDni());
            lblNombre.setText(c.getNombre());
            lblApellido.setText(c.getApellido());
            txtHistorial.setText(c.getHistorial());
        }
        else if(BuscarAlumnoFXMLController.estado == 1) {
            lblDni.setText(d.getDni());
            lblNombre.setText(d.getNombre());
            lblApellido.setText(d.getApellido());
            txtHistorial.setText(d.getHistorial());
        }
    }    
    
}
