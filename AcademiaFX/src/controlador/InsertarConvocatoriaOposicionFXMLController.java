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
import modelo.ConvocatoriaOposicion;
import modelo.Correo;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class InsertarConvocatoriaOposicionFXMLController implements Initializable {

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
    private ObservableList<String> listaNombresOposiciones = FXCollections.observableArrayList();
    
    //OBJETOS
    private ConvocatoriaOposicion convO = new ConvocatoriaOposicion();
    
    
    private void comprobarDias() {
        
        if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("V");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("J");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("JV");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("X");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("XV");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("XJ");
        }
        
        else if(!cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("XJV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("M");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("MV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("MJ");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("MJV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("MX");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("MXV");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("MXJ");
        }
        
        else if(!cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("MXJV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("L");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LJ");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LJV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LX");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LXV");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LXJ");
        }
        
        else if(cbxLunes.isSelected() && !cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LXJV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LM");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LMV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LMJ");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && !cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LMJV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LMX");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && !cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LMXV");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && !cbxViernes.isSelected()) {
            convO.setDias("LMXJ");
        }
        
        else if(cbxLunes.isSelected() && cbxMartes.isSelected() && cbxMiercoles.isSelected() && cbxJueves.isSelected() && cbxViernes.isSelected()) {
            convO.setDias("LMXJV");
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
                convO.setNombre(nombre);
                comprobarDias();
                convO.setHorario(txtHorario.getText().toUpperCase());
                convO.setInicio(Date.valueOf(dpInicio.getValue()));
                convO.setFin(Date.valueOf(dpFin.getValue()));
                ConvocatoriaOposicion.insertarConvocatoriaOposicion(con, convO.getNombre(), convO.getDias(), convO.getHorario(), convO.getInicio(), convO.getFin());
                JOptionPane.showMessageDialog(null, "Convocatoria de oposición introducida con éxito");
                //Correo.enviarEmails(con, nombre);
                ventana.cargarVentanaNueva("/vista/OposicionesFXML.fxml", event);
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarVentanaNueva("/vista/OposicionesFXML.fxml", event);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Oposicion.nombresOposiciones(con, listaNombresOposiciones);
        cbbxNombre.setItems(listaNombresOposiciones);
        
    }    
     
    
}
