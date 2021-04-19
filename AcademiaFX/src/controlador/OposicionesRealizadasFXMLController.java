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
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class OposicionesRealizadasFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //LABEL
    @FXML private Label lblDni;
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    
    //BUTTON
    @FXML private Button btnVolver;
    
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
    
    //OBJETOS
    private Alumno a1 = AlumnosFXMLController.a;
    private Alumno a2 = BuscarAlumnoFXMLController.b;
    private Oposicion o = new Oposicion();
    
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
            ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(AlumnosFXMLController.estado == 1) {
            lblDni.setText(a1.getDni().toUpperCase());
            lblNombre.setText(a1.getNombre().toUpperCase());
            lblApellido.setText(a1.getApellido().toUpperCase());

            Oposicion.buscarOposicionPorDni(con, listaOposiciones, a1.getDni());

            //Enlazar listas a TableView
            tblOposiciones.setItems(listaOposiciones);
        }
        else if(BuscarAlumnoFXMLController.estado == 1) {
            lblDni.setText(a2.getDni().toUpperCase());
            lblNombre.setText(a2.getNombre().toUpperCase());
            lblApellido.setText(a2.getApellido().toUpperCase());

            Oposicion.buscarOposicionPorDni(con, listaOposiciones, a2.getDni());

            //Enlazar listas a TableView
            tblOposiciones.setItems(listaOposiciones);
        }
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Oposicion, Integer>("duracion"));
        clmCoste.setCellValueFactory(new PropertyValueFactory<Oposicion, Float>("coste"));
    }   
    
}
