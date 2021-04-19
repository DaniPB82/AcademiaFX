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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.Empresa;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class ModificarEmpresaFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTFIELD
    @FXML private TextField txtNombre;
    @FXML private TextField txtPoblacion;
    @FXML private TextField txtResponsable;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //BUTTON
    @FXML private Button btnModificar;
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
    
    //OBJETOS
    private Empresa b = EmpresasFXMLController.e;
    
    //EVENTO CLICK SOBRE MENUITE Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnModificar);
        }
        
        else if(evt.equals(btnModificar)) {
            String nombre = txtNombre.getText().toUpperCase();
            Object objetoAmbito = cbbxAmbito.getValue();
            String ambito;
            if(objetoAmbito != null) {
                ambito = objetoAmbito.toString();
            }
            else {
                ambito = "";
            }
            String poblacion = txtPoblacion.getText().toUpperCase();
            String responsable = txtResponsable.getText().toUpperCase();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            if(!nombre.equals("") && !ambito.equals("") && !responsable.equals("") && !email.equals("")) {
                //if((telefono.length() == 9) || telefono.equals("")) {
                    if(!nombre.equals(b.getNombre())) {
                        int estado = Empresa.comprobarNombres(con, nombre);
                        if(estado == 1) {
                            Empresa.modificarEmpresa(con, nombre, ambito, poblacion, responsable, telefono, email, b.getNombre());
                            JOptionPane.showMessageDialog(null, "Empresa modificada con éxito");
                            ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "NOMBRE repetido");
                        }
                    }
                    else {
                        Empresa.modificarEmpresa(con, nombre, ambito, poblacion, responsable, telefono, email, b.getNombre());
                        JOptionPane.showMessageDialog(null, "Empresa modificada con éxito");
                        ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
                    }
                //}
                //else {
                    //JOptionPane.showMessageDialog(null, "Introduce un TELÉFONO de 9 dígitos o vacío");
                //}
            }
            else {
                JOptionPane.showMessageDialog(null, "El NOMBRE, la FAMILIA PROFESIONAL, el RESPONSABLE y el E-MAIL son campos obligatorios");
            }
        }
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/EmpresasFXML.fxml", event);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Empresa.verEmpresas(con, listaEmpresas);
        listaAmbitos = FamiliaProfesional.dameFPsEmpresa(con, listaAmbitos);
        
        cbbxAmbito.setItems(listaAmbitos);
        
        //Enlazar listas a TableView
        tblEmpresas.setItems(listaEmpresas);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Empresa, String>("familiaProfesional"));
        clmPoblacion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("poblacion"));
        clmResponsable.setCellValueFactory(new PropertyValueFactory<Empresa, String>("responsable"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<Empresa, String>("email"));
        
        txtNombre.setText(b.getNombre());
        cbbxAmbito.setValue(b.getFamiliaProfesional());
        txtPoblacion.setText(b.getPoblacion());
        txtResponsable.setText(b.getResponsable());
        txtTelefono.setText(b.getTelefono());
        txtEmail.setText(b.getEmail());
    }    
    
}
