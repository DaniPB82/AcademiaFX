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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.FamiliaProfesional;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class BuscarOposicionFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miAlumnosOposicion;
    @FXML private MenuItem miInsertarFP;
       
    //COMBOBOX
    @FXML private ComboBox cbbxNombre;
    @FXML private ComboBox cbbxAmbito;
    
    //RADIOBUTTON
    @FXML private RadioButton rbtnNombre;
    @FXML private RadioButton rbtnAmbito;
    @FXML private RadioButton rbtnDuracion;
    @FXML private RadioButton rbtnCoste;
    
    //TEXTFIELD
    @FXML private TextField txtDuracion;
    @FXML private TextField txtCoste;
    
    //BUTTON
    @FXML private Button btnBuscar;
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
    private ObservableList<String> listaNombres = FXCollections.observableArrayList();
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();

    //OBJETOS
    public static Oposicion oposicion;
    
    //VARIABLES
    public static int estado;
    
    //EVENTO CLICK SOBRE LOS RADIOBUTTON
    @FXML
    private void radioButtonAction(ActionEvent event) {
        
        if(rbtnNombre.isSelected()) {
            cbbxNombre.setDisable(false);
            cbbxAmbito.setDisable(true);
            txtDuracion.setDisable(true);
            txtCoste.setDisable(true);
        }
        
        else if(rbtnAmbito.isSelected()) {
            cbbxNombre.setDisable(true);
            cbbxAmbito.setDisable(false);
            txtDuracion.setDisable(true);
            txtCoste.setDisable(true);
        }
        
        else if(rbtnDuracion.isSelected()) {
            cbbxNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtDuracion.setDisable(false);
            txtCoste.setDisable(true);
        }
        
        else if(rbtnCoste.isSelected()) {
            cbbxNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtDuracion.setDisable(true);
            txtCoste.setDisable(false);
        }
        vaciarCampos();
    }
    
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miAlumnosOposicion)) {
            estado = 1;
            oposicion = tblOposiciones.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosOposicionFXML.fxml", tblOposiciones);
        }
                
        else if(evt.equals(btnBuscar)) {
            listaOposiciones.clear();
            Object objetoNombre = cbbxNombre.getSelectionModel().getSelectedItem();
            String nombre;
            if(objetoNombre == null) {
                nombre = "";
            }
            else {
                nombre = objetoNombre.toString();
            }
            Object objetoAmbito = cbbxAmbito.getSelectionModel().getSelectedItem();
            String ambito;
            if(objetoAmbito == null) {
                ambito = "";
            }
            else {
                ambito = objetoAmbito.toString();
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
                Oposicion.buscarOposicionPorNombre(con, listaOposiciones, nombre);
            }
            else if(!ambito.equals("")) {
                Oposicion.buscarOposicionPorAmbito(con, listaOposiciones, ambito);
            }
            else if(!duracionTexto.equals("")) {
                Oposicion.buscarOposicionPorDuracion(con, listaOposiciones, duracion);
            }
            else if(!costeTexto.equals("")) {
                Oposicion.buscarOposicionPorCoste(con, listaOposiciones, coste);
            }
        enlazarListadoTabla();
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        cbbxNombre.setValue(null);
        cbbxAmbito.setValue(null);
        txtDuracion.setText("");
        txtCoste.setText("");
    }
    
    private void enlazarListadoTabla() {
        //Enlazar listas a TableView
        tblOposiciones.setItems(listaOposiciones);

        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Oposicion, Integer>("duracion"));
        clmCoste.setCellValueFactory(new PropertyValueFactory<Oposicion, Float>("coste"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //estado = 0;
        
        Oposicion.nombresOposiciones(con, listaNombres);
        cbbxNombre.setItems(listaNombres);
        
        FamiliaProfesional.dameFPsOposicion(con, listaAmbitos);
        cbbxAmbito.setItems(listaAmbitos);
    }
    
}
