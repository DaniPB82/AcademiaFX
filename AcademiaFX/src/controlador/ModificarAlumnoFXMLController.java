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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import modelo.Alumno;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;

/**
 * FXML Controller class
 *
 * @author -
 */
public class ModificarAlumnoFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTFIELD
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtPoblacion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    
    //BUTTON
    @FXML private Button btnModificar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Alumno> tblAlumnos;

    public TableView<Alumno> getTblAlumnos() {
        return tblAlumnos;
    }
    
    //COLUMNAS DE TABLEVIEW
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
    private Alumno b = AlumnosFXMLController.a;
       
    //EVENTO CLICK SOBRE LOS MENUITEM Y BUTTON
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
            String dni = txtDni.getText().toUpperCase();
            String nombre = txtNombre.getText().toUpperCase();
            String apellido = txtApellido.getText().toUpperCase();
            String poblacion = txtPoblacion.getText().toUpperCase();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();
            if(!dni.equals("") && !email.equals("")) {
                if(!dni.equals(b.getDni())) {
                    int estado = Alumno.comprobarDnis(con, dni);
                    if(estado == 0) {
                        JOptionPane.showMessageDialog(null, "DNI repetido");
                    }
                    else {
                        Alumno.modificarAlumno(con, dni, nombre, apellido, poblacion, telefono, email, b.getDni());
                        JOptionPane.showMessageDialog(null, "Alumno modificado con éxito");
                        ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
                    }
                }
                else {
                    Alumno.modificarAlumno(con, dni, nombre, apellido, poblacion, telefono, email, b.getDni());
                    JOptionPane.showMessageDialog(null, "Alumno modificado con éxito");
                    ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
                }   
            }
            else {
                JOptionPane.showMessageDialog(null, "El DNI y el E-MAIL son campos obligatorios");
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/AlumnosFXML.fxml", event);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        txtDni.setText(b.getDni());
        txtNombre.setText(b.getNombre());
        txtApellido.setText(b.getApellido());
        txtPoblacion.setText(b.getPoblacion());
        txtTelefono.setText(b.getTelefono());
        txtEmail.setText(b.getEmail());
    }    
    
}
