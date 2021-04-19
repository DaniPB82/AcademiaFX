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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.Curso;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class AlumnosFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miHistorial;
    @FXML private MenuItem miCursosRealizados;
    @FXML private MenuItem miOposicionesRealizadas;
    @FXML private MenuItem miInsertarFP;
    
    //BUTTONS
    @FXML private Button btnInsertarAlumno;
    @FXML private Button btnModificarAlumno;
    @FXML private Button btnEliminarAlumno;
    @FXML private Button btnBuscarAlumno;
    
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
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<Oposicion> listaOposiciones = FXCollections.observableArrayList();
    
    //OBJETOS
    public static Alumno a = new Alumno();
    
    //VARIABLES
    public static int estado;
    
    //EVENTO CLICK SOBRE MUNUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertarAlumno);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertarAlumno);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertarAlumno);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertarAlumno);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertarAlumno);
        }
        
        else if(evt.equals(miHistorial)) {
            estado = 1;
            a = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/HistorialFXML.fxml", tblAlumnos);
        }
        
        else if(evt.equals(miCursosRealizados)) {
            estado = 1;
            a = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosRealizadosFXML.fxml", tblAlumnos);
        }
        
        else if(evt.equals(miOposicionesRealizadas)) {
            estado = 1;
            a = tblAlumnos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesRealizadasFXML.fxml", tblAlumnos);
        }
        
        else if(evt.equals(btnInsertarAlumno)) {
            ventana.cargarEnVentana("/vista/InsertarAlumnoFXML.fxml", event);
        }
        
        else if(evt.equals(btnEliminarAlumno)) {
            if(tblAlumnos.getSelectionModel().getSelectedItem() != null) {
                a = tblAlumnos.getSelectionModel().getSelectedItem();
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el alumno con DNI: " + a.getDni() + " ?" +
                    "\nSe eliminará también la relación de los cursos y oposiciones que haya realizado.");
                if(opcion == 0) {
                    Alumno.eliminarAlumno(con, a.getDni());
                    JOptionPane.showMessageDialog(null, "Alumno eliminado con éxito.");
                    ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno para eliminarlo");
            }
        }
        
        else if(evt.equals(btnModificarAlumno)) {
            if(tblAlumnos.getSelectionModel().getSelectedItem() != null) {
                a = tblAlumnos.getSelectionModel().getSelectedItem();
                ventana.cargarEnVentana("/vista/ModificarAlumnoFXML.fxml", event);
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno para modificarlo");
            }
        }
        
        else if(evt.equals(btnBuscarAlumno)) {
            ventana.cargarEnVentana("/vista/BuscarAlumnoFXML.fxml", event);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado = 0;
        
        Alumno.verAlumnos(con, listaAlumnos);
        
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
    
}
