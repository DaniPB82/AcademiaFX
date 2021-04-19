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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author -
 */
public class AlumnosCursoFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //LABEL
    @FXML private Label lblNombre;
    @FXML private Label lblAmbito;
    @FXML private Label lblDuracion;
    
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
    private Curso cu = CursosFXMLController.c;
    private Curso cuBuscar = BuscarCursoFXMLController.curso;
    public static Alumno al = new Alumno();
    
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
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
        
        else if(evt.equals(btnVolver)) {
            ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CursosFXMLController.estado == 1) {
            lblNombre.setText(cu.getNombre().toUpperCase());
            lblAmbito.setText(cu.getFamiliaProfesional().toUpperCase());
            lblDuracion.setText(String.valueOf(cu.getDuracion()));

            Alumno.buscarAlumnoPorNombreCurso(con, listaAlumnos, cu.getNombre());

            //Enlazar listas a TableView
            tblAlumnos.setItems(listaAlumnos);
        }
        else if(BuscarCursoFXMLController.estado == 1) {
            lblNombre.setText(cuBuscar.getNombre().toUpperCase());
            lblAmbito.setText(cuBuscar.getFamiliaProfesional().toUpperCase());
            lblDuracion.setText(String.valueOf(cuBuscar.getDuracion()));

            Alumno.buscarAlumnoPorNombreCurso(con, listaAlumnos, cuBuscar.getNombre());

            //Enlazar listas a TableView
            tblAlumnos.setItems(listaAlumnos);
        }
            //Enlazar columnas con atributos
            clmDni.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
            clmNombre.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
            clmApellido.setCellValueFactory(new PropertyValueFactory<Alumno, String>("apellido"));
            clmPoblacion.setCellValueFactory(new PropertyValueFactory<Alumno, String>("poblacion"));
            clmTelefono.setCellValueFactory(new PropertyValueFactory<Alumno, String>("telefono"));
            clmEmail.setCellValueFactory(new PropertyValueFactory<Alumno, String>("email"));
    }    
    
}
