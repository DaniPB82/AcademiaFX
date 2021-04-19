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
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class OposicionesFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miAlumnosOposicion;
    @FXML private MenuItem miConvocatoriasOposicion;
    @FXML private MenuItem miInsertarConvocatoria;
    @FXML private MenuItem miInsertarFP;
    
    //BUTTON
    @FXML private Button btnInsertarOposicion;
    @FXML private Button btnModificarOposicion;
    @FXML private Button btnEliminarOposicion;
    @FXML private Button btnBuscarOposicion;
    
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
    public static Oposicion o = new Oposicion();
    
    public static int estado;
    
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertarOposicion);
        }
        
        if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miAlumnosOposicion)) {
            estado = 1;
            o = tblOposiciones.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosOposicionFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miConvocatoriasOposicion)) {
            estado = 1;
            o = tblOposiciones.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/ConvocatoriasOposicionesFXML.fxml", btnInsertarOposicion);
        }
        
        else if(evt.equals(miInsertarConvocatoria)) {
            ventana.cargarEnVentanaNuevaMenuItem("/vista/InsertarConvocatoriaOposicionFXML.fxml", tblOposiciones);
        }
        
        else if(evt.equals(btnInsertarOposicion)) {
            ventana.cargarEnVentana("/vista/InsertarOposicionFXML.fxml", event);
        }
        
        else if(evt.equals(btnEliminarOposicion)) {
            if(tblOposiciones.getSelectionModel().getSelectedItem() != null) {
                o = tblOposiciones.getSelectionModel().getSelectedItem();
                String nombre = o.getNombre();
                String ambito = o.getFamiliaProfesional();
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar la oposición con NOMBRE: " + nombre + " ?" +
                    "\nSe eliminará también la relación de los alumnos que hayan realizado dicha oposición.");
                if(opcion == 0) {
                    Oposicion.eliminarOposicion(con, nombre, ambito);
                    JOptionPane.showMessageDialog(null, "Oposición eliminada con éxito.");
                    ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una oposición para eliminarla");
            }
        }
        else if(evt.equals(btnModificarOposicion)) {
            if(tblOposiciones.getSelectionModel().getSelectedItem() != null) {
                o = tblOposiciones.getSelectionModel().getSelectedItem();
                ventana.cargarEnVentana("/vista/ModificarOposicionFXML.fxml", event);
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una oposición para modificarla");
            }
        }
        else if(evt.equals(btnBuscarOposicion)) {
            ventana.cargarEnVentana("/vista/BuscarOposicionFXML.fxml", event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado = 0;
        
        Oposicion.verOposiciones(con, listaOposiciones);
        
        //Enlazar listas a TableView
        tblOposiciones.setItems(listaOposiciones);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Oposicion, Integer>("duracion"));
        clmCoste.setCellValueFactory(new PropertyValueFactory<Oposicion, Float>("coste"));
    }   
    
    
    
    
}
