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
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;

/**
 * FXML Controller class
 *
 * @author -
 */
public class BuscarAlumnoFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miHistorial;
    @FXML private MenuItem miCursosRealizados;
    @FXML private MenuItem miOposicionesRealizadas;
    @FXML private MenuItem miInsertarFP;
       
    //COMBOBOX
    @FXML private ComboBox cbbxPoblacion;
    
    //RADIOBUTTON
    @FXML private RadioButton rbtnDni;
    @FXML private RadioButton rbtnNombre;
    @FXML private RadioButton rbtnApellido;
    @FXML private RadioButton rbtnPoblacion;
    @FXML private RadioButton rbtnTelefono;
    @FXML private RadioButton rbtnEmail;
    
    //TEXTFIELD
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    
    //BUTTON
    @FXML private Button btnBuscar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Alumno> tblAlumnos;

    public TableView<Alumno> getTblAlumnos() {
        return tblAlumnos;
    }
    
    //COLUMNAS DE LA TABLEVIEW
    @FXML private TableColumn<Alumno, String> clmDni;
    @FXML private TableColumn<Alumno, String> clmNombre;
    @FXML private TableColumn<Alumno, String> clmApellido;
    @FXML private TableColumn<Alumno, String> clmPoblacion;
    @FXML private TableColumn<Alumno, String> clmTelefono;
    @FXML private TableColumn<Alumno, String> clmEmail;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList();
    private ObservableList<String> listaPoblaciones = FXCollections.observableArrayList();

    //OBJETOS
    public static Alumno b = new Alumno();
    
    public static int estado;
    
    //EVENTO CLICK SOBRE LOS RADIOBUTTON
    @FXML
    private void radioButtonAction(ActionEvent event) {
        
        if(rbtnDni.isSelected()) {
            txtDni.setDisable(false);
            txtNombre.setDisable(true);
            txtApellido.setDisable(true);
            cbbxPoblacion.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        
        else if(rbtnNombre.isSelected()) {
            txtDni.setDisable(true);
            txtNombre.setDisable(false);
            txtApellido.setDisable(true);
            cbbxPoblacion.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        
        else if(rbtnApellido.isSelected()) {
            txtDni.setDisable(true);
            txtNombre.setDisable(true);
            txtApellido.setDisable(false);
            cbbxPoblacion.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        
        else if(rbtnPoblacion.isSelected()) {
            txtDni.setDisable(true);
            txtNombre.setDisable(true);
            txtApellido.setDisable(true);
            cbbxPoblacion.setDisable(false);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        
        else if(rbtnTelefono.isSelected()) {
            txtDni.setDisable(true);
            txtNombre.setDisable(true);
            txtApellido.setDisable(true);
            cbbxPoblacion.setDisable(true);
            txtTelefono.setDisable(false);
            txtEmail.setDisable(true);
        }
        
        else if(rbtnEmail.isSelected()) {
            txtDni.setDisable(true);
            txtNombre.setDisable(true);
            txtApellido.setDisable(true);
            cbbxPoblacion.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(false);
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
        
        else if(evt.equals(miHistorial)) {
            estado = 1;
            b = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/HistorialFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miCursosRealizados)) {
            estado = 1;
            b = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosRealizadosFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(miOposicionesRealizadas)) {
            estado = 1;
            b = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesRealizadasFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(btnBuscar)) {
            listaAlumnos.clear();
            String dni = txtDni.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            Object objetoPoblacion = cbbxPoblacion.getSelectionModel().getSelectedItem();
            String poblacion;
            if(objetoPoblacion == null) {
                poblacion = "";
            }
            else {
                poblacion = objetoPoblacion.toString();
            }
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            if(!dni.equals("")) {
                Alumno.buscarAlumnoPorDni(con, listaAlumnos, dni);
            }
            else if(!nombre.equals("")) {
                Alumno.buscarAlumnoPorNombre(con, listaAlumnos, nombre);
            }
            else if(!apellido.equals("")) {
                Alumno.buscarAlumnoPorApellido(con, listaAlumnos, apellido);
            }
            else if(!poblacion.equals("")) {
                Alumno.buscarAlumnoPorPoblacion(con, listaAlumnos, poblacion);
            }
            else if(!telefono.equals("")) {
                Alumno.buscarAlumnoPorTelefono(con, listaAlumnos, telefono);
            }
            else if(!email.equals("")) {
                Alumno.buscarAlumnoPorEmail(con, listaAlumnos, email);
            }
        enlazarListadoTabla();
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        cbbxPoblacion.setValue(null);
        txtTelefono.setText("");
        txtEmail.setText("");
    }
    
    private void enlazarListadoTabla() {
        //Enlazar listas a TableView
        tblAlumnos.setItems(listaAlumnos);

        //Enlazar columnas con atributos
        clmDni.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        clmNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        clmApellido.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido"));
        clmPoblacion.setCellValueFactory(new PropertyValueFactory<Alumno, String>("poblacion"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<Alumno, String>("telefono"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Alumno, String>("email"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado = 0;
        
        Alumno.poblaciones(con, listaPoblaciones);
        cbbxPoblacion.setItems(listaPoblaciones);
    }    
    
}
