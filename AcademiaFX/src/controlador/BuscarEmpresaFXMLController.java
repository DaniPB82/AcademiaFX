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
import modelo.Empresa;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class BuscarEmpresaFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //RADIOBUTTON
    @FXML private RadioButton rbtnNombre;
    @FXML private RadioButton rbtnAmbito;
    @FXML private RadioButton rbtnPoblacion;
    @FXML private RadioButton rbtnResponsable;
    @FXML private RadioButton rbtnTelefono;
    @FXML private RadioButton rbtnEmail;
    
    //TEXTFIELD
    @FXML private TextField txtNombre;
    @FXML private TextField txtPoblacion;
    @FXML private TextField txtResponsable;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //BUTTON
    @FXML private Button btnBuscar;
    @FXML private Button btnCancelar;
    
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
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //EVENTO CLICK SOBRE RADIOBUTTON
    @FXML
    private void radioButtonAction(ActionEvent event) {
        if(rbtnNombre.isSelected()) {
            txtNombre.setDisable(false);
            cbbxAmbito.setDisable(true);
            txtPoblacion.setDisable(true);
            txtResponsable.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        else if(rbtnAmbito.isSelected()) {
            txtNombre.setDisable(true);
            cbbxAmbito.setDisable(false);
            txtPoblacion.setDisable(true);
            txtResponsable.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        else if(rbtnPoblacion.isSelected()) {
            txtNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtPoblacion.setDisable(false);
            txtResponsable.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        else if(rbtnResponsable.isSelected()) {
            txtNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtPoblacion.setDisable(true);
            txtResponsable.setDisable(false);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(true);
        }
        else if(rbtnTelefono.isSelected()) {
            txtNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtPoblacion.setDisable(true);
            txtResponsable.setDisable(true);
            txtTelefono.setDisable(false);
            txtEmail.setDisable(true);
        }
        else if(rbtnEmail.isSelected()) {
            txtNombre.setDisable(true);
            cbbxAmbito.setDisable(true);
            txtPoblacion.setDisable(true);
            txtResponsable.setDisable(true);
            txtTelefono.setDisable(true);
            txtEmail.setDisable(false);
        }
        vaciarCampos();
    }
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnBuscar);
        }
        
        if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnBuscar);
        }
        
        if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnBuscar);
        }
        
        if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnBuscar);
        }
         
        if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnBuscar);
        }
        
        else if(evt.equals(btnBuscar)) {
            listaEmpresas.clear();
            String nombre = txtNombre.getText();
            Object objetoAmbito = cbbxAmbito.getValue();
            String ambito;
            if(objetoAmbito != null) {
                ambito = objetoAmbito.toString();
            }
            else {
                ambito = "";
            }
            String poblacion = txtPoblacion.getText();
            String responsable = txtResponsable.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            if(!nombre.equals("")) {
                Empresa.buscarEmpresaPorNombre(con, listaEmpresas, nombre);
            }
            else if(!ambito.equals("")) {
                Empresa.buscarEmpresaPorAmbito(con, listaEmpresas, ambito);
            }
            else if(!poblacion.equals("")) {
                Empresa.buscarEmpresaPorPoblacion(con, listaEmpresas, poblacion);
            }
            else if(!responsable.equals("")) {
                Empresa.buscarEmpresaPorResponsable(con, listaEmpresas, responsable);
            }
            else if(!telefono.equals("")) {
                Empresa.buscarEmpresaPorTelefono(con, listaEmpresas, telefono);
            }
            else if(!email.equals("")) {
                Empresa.buscarEmpresaPorEmail(con, listaEmpresas, email);
            }
        enlazarListadoTabla();
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        txtNombre.setText("");
        cbbxAmbito.setValue(null);
        txtPoblacion.setText("");
        txtResponsable.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
    
    private void enlazarListadoTabla() {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaAmbitos = FamiliaProfesional.dameFPsEmpresa(con, listaAmbitos);
        
        cbbxAmbito.setItems(listaAmbitos);
    }    
    
}
