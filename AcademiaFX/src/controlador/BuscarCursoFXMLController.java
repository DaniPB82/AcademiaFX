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
import modelo.Curso;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class BuscarCursoFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miAlumnosCurso;
    @FXML private MenuItem miInsertarFP;
       
    //COMBOBOX
    @FXML private ComboBox cbbxNombre;
    @FXML private ComboBox cbbxAmbito;
    
    //RADIOBUTTON
    @FXML private RadioButton rbtnNombre;
    @FXML private RadioButton rbtnAmbito;
    @FXML private RadioButton rbtnDuracion;
    
    //TEXTFIELD
    @FXML private TextField txtDuracion;
    
    //BUTTON
    @FXML private Button btnBuscar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Curso> tblCursos;

    public TableView<Curso> getTblCursos() {
        return tblCursos;
    }
    
    //COLUMNAS DE LA TABLEVIEW
    @FXML private TableColumn<Curso, String> clmNombre;
    @FXML private TableColumn<Curso, String> clmAmbito;
    @FXML private TableColumn<Curso, Integer> clmDuracion;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<String> listaNombres = FXCollections.observableArrayList();
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();

    //OBJETOS
    public static Curso curso;
    
    //VARIABLES
    public static int estado;
    
    //EVENTO CLICK SOBRE LOS RADIOBUTTON
    @FXML
    private void radioButtonAction(ActionEvent event) {
        
        if(rbtnNombre.isSelected()) {
            cbbxNombre.setDisable(false);
            cbbxAmbito.setDisable(true);
            txtDuracion.setDisable(true);
        }
        
        else if(rbtnAmbito.isSelected()) {
            cbbxNombre.setDisable(true);
            cbbxAmbito.setDisable(false);
            txtDuracion.setDisable(true);
        }
        
        else if(rbtnDuracion.isSelected()) {
            cbbxNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtDuracion.setDisable(false);
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
        
        else if(evt.equals(miAlumnosCurso)) {
            estado = 1;
            curso = tblCursos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosCursoFXML.fxml", tblCursos);
        }
                
        else if(evt.equals(btnBuscar)) {
            listaCursos.clear();
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
            String duraciontexto = txtDuracion.getText();
            int duracion;
            if(duraciontexto.equals("")) {
                duracion = 0;
            }
            else {
                duracion = Integer.parseInt(duraciontexto);
            }
            if(!nombre.equals("")) {
                Curso.buscarCursoPorNombre(con, listaCursos, nombre);
            }
            else if(!ambito.equals("")) {
                Curso.buscarCursoPorAmbito(con, listaCursos, ambito);
            }
            else if(!duraciontexto.equals("")) {
                Curso.buscarCursoPorDuracion(con, listaCursos, duracion);
            }
        enlazarListadoTabla();
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        cbbxNombre.setValue(null);
        cbbxAmbito.setValue(null);
        txtDuracion.setText("");
    }
    
    private void enlazarListadoTabla() {
        //Enlazar listas a TableView
        tblCursos.setItems(listaCursos);

        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Curso, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Curso, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Curso, Integer>("duracion"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //estado = 0;
        
        Curso.nombresCursos(con, listaNombres);
        cbbxNombre.setItems(listaNombres);
        
        FamiliaProfesional.dameFPsCurso(con, listaAmbitos);
        cbbxAmbito.setItems(listaAmbitos);
    }    
    
}
