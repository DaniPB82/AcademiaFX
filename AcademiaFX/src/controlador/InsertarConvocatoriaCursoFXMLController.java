/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.ConvocatoriaCurso;
import modelo.Correo;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author -
 */
public class InsertarConvocatoriaCursoFXMLController implements Initializable {

    //COMBOBOX
    @FXML private ComboBox cbbxNombre;
    
    //CHECKBOX
    @FXML private CheckBox cbxLunes;
    @FXML private CheckBox cbxMartes;
    @FXML private CheckBox cbxMiercoles;
    @FXML private CheckBox cbxJueves;
    @FXML private CheckBox cbxViernes;
    
    //TEXTFIELD
    @FXML private TextField txtHorario;
    
    //DATEPICKER
    @FXML private DatePicker dpInicio;
    @FXML private DatePicker dpFin;
    
    //BUTTON
    @FXML private Button btnInsertar;
    @FXML private Button btnCancelar;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<String> listaNombresCursos = FXCollections.observableArrayList();
    
    //OBJETOS
    private ConvocatoriaCurso convC = new ConvocatoriaCurso();
    
    
    private void comprobarDias() {
        
        if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("V");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("J");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("JV");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("X");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("XV");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("XJ");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("XJV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("M");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("MV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("MJ");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("MJV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("MX");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("MXV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("MXJ");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("MXJV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("L");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LJ");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LJV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LX");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LXV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LXJ");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LXJV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LM");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LMV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LMJ");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LMJV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LMX");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LMXV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convC.setDias("LMXJ");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convC.setDias("LMXJV");
        }
        
    }
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException, MessagingException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(btnInsertar)) {
            
            Object objetoNombre = cbbxNombre.getSelectionModel().getSelectedItem();
            String nombre = "";
            
            if(objetoNombre == null || dpInicio.getValue() == null) {
                JOptionPane.showMessageDialog(null, "El NOMBRE, el INICIO y el FIN son obligatorios");
            }
            else {
                nombre = objetoNombre.toString().toUpperCase();
                convC.setNombre(nombre);
                comprobarDias();
                convC.setHorario(txtHorario.getText().toUpperCase());
                convC.setInicio(Date.valueOf(dpInicio.getValue()));
                convC.setFin(Date.valueOf(dpFin.getValue()));
                ConvocatoriaCurso.insertarConvocatoriaCurso(con, convC.getNombre(), convC.getDias(), convC.getHorario(), convC.getInicio(), convC.getFin());
                JOptionPane.showMessageDialog(null, "Convocatoria de curso introducida con éxito");
                //Correo.enviarEmails(con, nombre);
                ventana.cargarVentanaNueva("/vista/CursosFXML.fxml", event);
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarVentanaNueva("/vista/CursosFXML.fxml", event);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Curso.nombresCursos(con, listaNombresCursos);
        cbbxNombre.setItems(listaNombresCursos);
        
    }    
    
}
