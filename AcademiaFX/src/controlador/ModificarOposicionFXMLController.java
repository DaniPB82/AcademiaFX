/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.FamiliaProfesional;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class ModificarOposicionFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTFIELD
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtCoste;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //BUTTON
    @FXML private Button btnModificar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Oposicion> tblOposiciones;

    public TableView<Oposicion> getTblOposiciones() {
        return tblOposiciones;
    }
    
    //COLUMNAS DE LA TABLEVIEW
    @FXML private TableColumn<Oposicion, String> clmNombre;
    @FXML private TableColumn<Oposicion, String> clmAmbito;
    @FXML private TableColumn<Oposicion, Integer> clmDuracion;
    @FXML private TableColumn<Oposicion, Float> clmCoste;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Oposicion> listaOposiciones = FXCollections.observableArrayList();
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //OBJETOS
    private Oposicion oo = OposicionesFXMLController.o;
       
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(btnModificar)) {
            String nombre = txtNombre.getText().toUpperCase();
            Object objetoAmbito = cbbxAmbito.getValue();
            String ambito;
            if(objetoAmbito != null) {
                ambito = objetoAmbito.toString().toUpperCase();
            }
            else {
                ambito = "";
            }
            String duracionTexto = txtDuracion.getText();
            String costeTexto = txtCoste.getText();
            int duracion;
            float coste;
            if(duracionTexto.equals("")) {
                duracion = 0;
            }
            else {
                duracion = Integer.parseInt(duracionTexto);
            }
            if(costeTexto.equals("")) {
                coste = 0;
            }
            else {
                coste = Float.parseFloat(costeTexto);
            }
            if(!nombre.equals("")) {
                if(!nombre.equals(oo.getNombre())) {
                    int estado = Oposicion.comprobarNombres(con, nombre);
                    if(estado == 0) {
                        JOptionPane.showMessageDialog(null, "NOMBRE repetido");
                    }
                    else {
                        Oposicion.modificarOposicion(con, nombre, ambito, duracion, coste, oo.getNombre(), oo.getFamiliaProfesional());
                        JOptionPane.showMessageDialog(null, "Oposición modificada con éxito");
                        ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
                    }
                }
                else {
                    Oposicion.modificarOposicion(con, nombre, ambito, duracion, coste, oo.getNombre(), oo.getFamiliaProfesional());
                    JOptionPane.showMessageDialog(null, "Oposición modificada con éxito");
                    ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
                }   
            }
            else {
                JOptionPane.showMessageDialog(null, "El NOMBRE es campo obligatorio");
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
        }
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Oposicion.verOposiciones(con, listaOposiciones);
        listaAmbitos = FamiliaProfesional.dameFPsOposicion(con, listaAmbitos);
        
        cbbxAmbito.setItems(listaAmbitos);
        
        //Enlazar listas a TableView
        tblOposiciones.setItems(listaOposiciones);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Oposicion, Integer>("duracion"));
        clmCoste.setCellValueFactory(new PropertyValueFactory<Oposicion, Float>("coste"));
        
        txtNombre.setText(oo.getNombre());
        cbbxAmbito.setValue(oo.getFamiliaProfesional());
        txtDuracion.setText(String.valueOf(oo.getDuracion()));
        txtCoste.setText(String.valueOf(oo.getCoste()));
    }    
    
}
