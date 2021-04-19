/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class BuscarCandidatosFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //DATEPICKER
    @FXML private DatePicker dpInicio;
    
    //BUTTONS
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
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //OBJETOS
    
    //VARIABLES
    
    
    //EVENTO CLICK SOBRE MUNUITEM Y BUTTON
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
        
        else if(evt.equals(btnBuscar)) {
            listaAlumnos.clear();
            Object objetoAmbito = cbbxAmbito.getValue();
            String ambito;
            if(objetoAmbito == null) {
                ambito = "";
            }
            else {
                ambito = objetoAmbito.toString();
            }
            LocalDate dateInicio = dpInicio.getValue();
            Date inicio;
            if(dateInicio == null) {
                inicio = null;
            }
            else {
                inicio = Date.valueOf(dateInicio);
            }
            if(!ambito.equals("") && inicio != null) {
                Alumno.buscarCandidatos(con, listaAlumnos, ambito, inicio);
            }
            else {
                JOptionPane.showMessageDialog(null, "Rellena ambos campos");
            }
        enlazarListadoTabla();
        System.out.println(dateInicio + " " + inicio);
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
        }
        
    }    
    
    private void vaciarCampos() {
        cbbxAmbito.setValue(null);
        dpInicio.setValue(null);
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
        FamiliaProfesional.dameFPsCurso(con, listaAmbitos);
        cbbxAmbito.setItems(listaAmbitos);
    }    
    
}
