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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AlumnosConvocatoriaOposicionFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miEliminarAlumno;
    @FXML private MenuItem miInsertarFP;
    
    //DATEPICKER
    @FXML private DatePicker dpInicio;
    
    //LABEL
    @FXML private Label lblNombre;
    
    //BUTTON
    @FXML private Button btnVolver;
    
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
    
    //OBJETOS
    private ConvocatoriaOposicion co = ConvocatoriasOposicionesFXMLController.convO;
    private Alumno alumno;
    
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miEliminarAlumno)) {
            alumno = tblAlumnos.getSelectionModel().getSelectedItem();
            Alumno.eliminarAlumnoDeConvocatoriaOposicion(con, alumno.getDni());
            Alumno.eliminarAlumnoDeOposicion(con, alumno.getDni());
            JOptionPane.showMessageDialog(null, "Alumno eliminado");
            listaAlumnos.clear();
            Alumno.alumnosDeConvocatoriaOposicion(con, listaAlumnos, co.getNombre(), co.getInicio());
        
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
        
        else if(evt.equals(btnVolver)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnVolver);
        }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(co.getNombre() + " " + co.getInicio());
        lblNombre.setText(co.getNombre());
        dpInicio.setValue(co.getInicio().toLocalDate());
        
        Alumno.alumnosDeConvocatoriaOposicion(con, listaAlumnos, co.getNombre(), co.getInicio());
        
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
