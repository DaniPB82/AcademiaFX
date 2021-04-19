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
import modelo.FamiliaProfesional;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class InsertarOposicionFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miInsertarFP;
    
    //TEXTFIELD
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtCoste;
    
    //COMBOBOX
    @FXML private ComboBox cbbxAmbito;
    
    //BUTTON
    @FXML private Button btnInsertar;
    @FXML private Button btnCancelar;
    
    //TABLEVIEW
    @FXML private TableView<Oposicion> tblOposiciones;

    public TableView<Oposicion> getTblOposiciones() {
        return tblOposiciones;
    }
    
    //COLUMNAS DE TABLEVIEW
    @FXML private TableColumn<Oposicion, String> clmNombre;
    @FXML private TableColumn<Oposicion, String> clmAmbito;
    @FXML private TableColumn<Oposicion, Integer> clmDuracion;
    @FXML private TableColumn<Oposicion, Float> clmCoste;
        
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<Oposicion> listaOposiciones = FXCollections.observableArrayList();
    private ObservableList<String> listaAmbitos = FXCollections.observableArrayList();
    
    //OBJETOS
    public static Oposicion op;
    
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
            //op.setNombre(txtNombre.getText().toUpperCase());
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
            if(txtDuracion.getText().equals("")) {
                //op.setDuracion(0);
                duracion = 0;
            }
            else {
                //op.setDuracion(Integer.parseInt(txtDuracion.getText()));
                duracion = Integer.parseInt(duracionTexto);
            }
            String costeTexto = txtCoste.getText();
            float coste;
            if(txtCoste.getText().equals("")) {
                //op.setCoste(Float.parseFloat(txtCoste.getText()));
                coste = 0;
            }
            else {
                //op.setCoste(0);
                coste = Float.parseFloat(costeTexto);
            }
            if(!nombre.equals("") && objetoAmbito != null) {
                int estado = Oposicion.comprobarNombresYFamilias(con, nombre, ambito);
                if(estado == 1) {
                    Oposicion.insertarOposicion(con, nombre, ambito, duracion, coste);
                    JOptionPane.showMessageDialog(null, "OPOSICIÓN introducida con éxito");
                    vaciarCampos();
                    ventana.cargarEnVentana("/vista/InsertarOposicionFXML.fxml", event);
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
            ventana.cargarEnVentana("/vista/OposicionesFXML.fxml", event);
        }
    }
    
    private void vaciarCampos() {
        txtNombre.setText("");
        cbbxAmbito.setValue(null);
        txtDuracion.setText("");
        txtCoste.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Oposicion.verOposiciones(con, listaOposiciones);
        listaAmbitos = FamiliaProfesional.dameFPsOposicion(con, listaAmbitos);
        
        cbbxAmbito.setItems(listaAmbitos);
        
        //Enlazar listas a TableView
        tblOposiciones.setItems(listaOposiciones);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("nombre"));
        clmAmbito.setCellValueFactory(new PropertyValueFactory<Oposicion, String>("familiaProfesional"));
        clmDuracion.setCellValueFactory(new PropertyValueFactory<Oposicion, Integer>("duracion"));
        clmCoste.setCellValueFactory(new PropertyValueFactory<Oposicion, Float>("coste"));
    }    
    
}
