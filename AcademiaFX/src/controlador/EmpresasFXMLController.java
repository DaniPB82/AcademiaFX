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
import modelo.Empresa;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class EmpresasFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    @FXML private MenuItem miCandidatos;
    
    //BUTTON
    @FXML private Button btnInsertarEmpresa;
    @FXML private Button btnModificarEmpresa;
    @FXML private Button btnEliminarEmpresa;
    @FXML private Button btnBuscarEmpresa;
    
    //TABLEVIEW
    @FXML private TableView<Empresa> tblEmpresas;

    public TableView<Empresa> getTblEmpresas() {
        return tblEmpresas;
    }
    
    //COLUMNAS DE TABLEVIEW
    @FXML private TableColumn<Empresa, String> clmNombre;
    @FXML private TableColumn<Empresa, String> clmAmbito;
    @FXML private TableColumn<Empresa, String> clmPoblacion;
    @FXML private TableColumn<Empresa, String> clmResponsable;
    @FXML private TableColumn<Empresa, String> clmTelefono;
    @FXML private TableColumn<Empresa, String> clmEmail;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Empresa> listaEmpresas = FXCollections.observableArrayList();
    
    //OBJETOS
    public static Empresa e = new Empresa();
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(miCandidatos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/BuscarCandidatosFXML.fxml", btnInsertarEmpresa);
        }
        
        else if(evt.equals(btnInsertarEmpresa)) {
            ventana.cargarEnVentana("/vista/InsertarEmpresaFXML.fxml", event);
        }
        
        else if(evt.equals(btnModificarEmpresa)) {
            if(tblEmpresas.getSelectionModel().getSelectedItem() != null) {
                e = tblEmpresas.getSelectionModel().getSelectedItem();
                ventana.cargarEnVentana("/vista/ModificarEmpresaFXML.fxml", event);
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una empresa para modificarla");
            }
        }
        
        else if(evt.equals(btnEliminarEmpresa)) {
            if(tblEmpresas.getSelectionModel().getSelectedItem() != null) {
                Empresa a = new Empresa();
                a = tblEmpresas.getSelectionModel().getSelectedItem();
                String nombre = a.getNombre();
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar la empresa con NOMBRE: " + nombre + " ?");
                if(opcion == 0) {
                    Empresa.eliminarEmpresa(con, nombre);
                    JOptionPane.showMessageDialog(null, "Empresa eliminada con éxito.");
                    ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una empresa para eliminarla");
            }
        }
        
        else if(evt.equals(btnBuscarEmpresa)) {
            ventana.cargarEnVentana("/vista/BuscarEmpresaFXML.fxml", event);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Empresa.verEmpresas(con, listaEmpresas);
        
        //Enlazar listas a TableView
        tblEmpresas.setItems(listaEmpresas);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Empresa, String>("familiaProfesional"));
        clmPoblacion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("poblacion"));
        clmResponsable.setCellValueFactory(new PropertyValueFactory<Empresa, String>("responsable"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Empresa, String>("email"));
    }    
    
}
