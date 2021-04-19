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
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author -
 */
public class CursosFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miAlumnosCurso;
    @FXML private MenuItem miConvocatoriasCurso;
    @FXML private MenuItem miInsertarConvocatoria;
    @FXML private MenuItem miInsertarFP;
    
    //BUTTON
    @FXML private Button btnInsertarCurso;
    @FXML private Button btnModificarCurso;
    @FXML private Button btnEliminarCurso;
    @FXML private Button btnBuscarCurso;
    
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
    
    //OBJETOS
    public static Curso c = new Curso();
    
    //VARIABLES
    public static int estado;
    
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertarCurso);
        }
        
        if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertarCurso);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertarCurso);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertarCurso);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertarCurso);
        }
        
        else if(evt.equals(miAlumnosCurso)) {
            estado = 1;
            c = tblCursos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosCursoFXML.fxml", tblCursos);
        }
        
        else if(evt.equals(miConvocatoriasCurso)) {
            estado = 1;
            c = tblCursos.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/ConvocatoriasCursosFXML.fxml", tblCursos);
        }
        
        else if(evt.equals(miInsertarConvocatoria)) {
            ventana.cargarEnVentanaNuevaMenuItem("/vista/InsertarConvocatoriaCursoFXML.fxml", tblCursos);
        }
        
        else if(evt.equals(btnInsertarCurso)) {
            ventana.cargarEnVentana("/vista/InsertarCursoFXML.fxml", event);
        }
        
        else if(evt.equals(btnEliminarCurso)) {
            if(tblCursos.getSelectionModel().getSelectedItem() != null) {
                c = tblCursos.getSelectionModel().getSelectedItem();
                String nombre = c.getNombre();
                String ambito = c.getFamiliaProfesional();
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el curso con NOMBRE: " + nombre + " ?" +
                    "\nSe eliminarán también sus convocatorias.");
                if(opcion == 0) {
                    Curso.eliminarCurso(con, nombre, ambito);
                    JOptionPane.showMessageDialog(null, "Curso eliminado con éxito.");
                    ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un curso para eliminarlo");
            }
        }
        else if(evt.equals(btnModificarCurso)) {
            if(tblCursos.getSelectionModel().getSelectedItem() != null) {
                c = tblCursos.getSelectionModel().getSelectedItem();
                ventana.cargarEnVentana("/vista/ModificarCursoFXML.fxml", event);
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un curso para modificarlo");
            }
        }
        else if(evt.equals(btnBuscarCurso)) {
            ventana.cargarEnVentana("/vista/BuscarCursoFXML.fxml", event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado = 0;
        
        Curso.verCursos(con, listaCursos);
        
        //Enlazar listas a TableView
        tblCursos.setItems(listaCursos);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Curso, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Curso, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Curso, Integer>("duracion"));
    }    
    
}
