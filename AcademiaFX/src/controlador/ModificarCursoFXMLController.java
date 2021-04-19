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
public class ModificarCursoFXMLController implements Initializable {

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
    @FXML private Button btnModificar;
    @FXML private Button btnCancelar;
    
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
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //OBJETOS
    private Curso cc = CursosFXMLController.c;
       
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
            if(!nombre.equals("")) {
                if(!nombre.equals(cc.getNombre())) {
                    int estado = Curso.comprobarNombres(con, nombre);
                    if(estado == 0) {
                        JOptionPane.showMessageDialog(null, "NOMBRE repetido");
                    }
                    else {
                        Curso.modificarCurso(con, nombre, ambito, duracion, cc.getNombre(), cc.getFamiliaProfesional());
                        JOptionPane.showMessageDialog(null, "Curso modificado con éxito");
                        ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
                    }
                }
                else {
                    Curso.modificarCurso(con, nombre, ambito, duracion, cc.getNombre(), cc.getFamiliaProfesional());
                    JOptionPane.showMessageDialog(null, "Curso modificado con éxito");
                    ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
                }   
            }
            else {
                JOptionPane.showMessageDialog(null, "El NOMBRE es campo obligatorio");
            }
        }
        
        else if(evt.equals(btnCancelar)) {
            ventana.cargarEnVentana("/vista/CursosFXML.fxml", event);
        }
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
        
        txtNombre.setText(cc.getNombre());
        cbbxAmbito.setValue(cc.getFamiliaProfesional());
        txtDuracion.setText(String.valueOf(cc.getDuracion()));
    }    
    
}
