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
import modelo.Curso;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author -
 */
public class InsertarCursoFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTFIELD
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracion;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //BUTTON
    @FXML private Button btnInsertar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Curso> tblCursos;

    public TableView<Curso> getTblCursos() {
        return tblCursos;
    }
    
    //COLUMNAS DE TABLEVIEW
    @FXML private TableColumn<Curso, String> clmNombre;
    @FXML private TableColumn<Curso, String> clmAmbito;
    @FXML private TableColumn<Curso, Integer> clmDuracion;
        
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Curso> listaCursos = FXCollections.observableArrayList();
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerEmpresas)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/EmpresasFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerCursos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miVerOposiciones)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(miInsertarFP)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarFPFXML.fxml", btnInsertar);
        }
        
        else if(evt.equals(btnInsertar)) {
            String nombre = txtNombre.getText().toUpperCase();
            Object objetoAmbito = cbbxAmbito.getValue();
            String ambito;
            if(objetoAmbito != null) {
                ambito = objetoAmbito.toString().toUpperCase();
            }
            else {
                ambito = "";
            }
            String duracionTexto = txtDuracion.getText();
            int duracion;
            if(duracionTexto.equals("")) {
                duracion = 0;
            }
            else {
                duracion = Integer.parseInt(duracionTexto);
            }
            if(!nombre.equals("") && objetoAmbito != null) {
                int estado = Curso.comprobarNombresYFamilias(con, nombre, ambito);
                if(estado == 1) {
                    Curso.insertarCurso(con, nombre, ambito, duracion);
                    JOptionPane.showMessageDialog(null, "CURSO introducido con éxito");
                    vaciarCampos();
                    ventana.cargarEnVentana("/vista/InsertarCursoFXML.fxml", event);
                }
                else {
                    JOptionPane.showMessageDialog(null, "NOMBRE repetido");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "El NOMBRE y la FAMILIA PROFESIONAL son campos obligatorios");
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        txtNombre.setText("");
        cbbxAmbito.setValue(null);
        txtDuracion.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Curso.verCursos(con, listaCursos);
        listaAmbitos = FamiliaProfesional.dameFPsCurso(con, listaAmbitos);
        
        cbbxAmbito.setItems(listaAmbitos);
        
        //Enlazar listas a TableView
        tblCursos.setItems(listaCursos);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Curso, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Curso, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Curso, Integer>("duracion"));
    }    
    
}
