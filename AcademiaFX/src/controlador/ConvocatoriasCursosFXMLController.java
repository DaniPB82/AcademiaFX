/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javax.mail.MessagingException;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.ConvocatoriaCurso;
import modelo.Correo;
import modelo.Curso;

/**
 * FXML Controller class
 *
 * @author -
 */
public class ConvocatoriasCursosFXMLController implements Initializable {

    //MENUITEM
    @FXML private MenuItem miVerAlumnos;
    @FXML private MenuItem miVerEmpresas;
    @FXML private MenuItem miVerCursos;
    @FXML private MenuItem miVerOposiciones;
    @FXML private MenuItem miVerAlumnosConvocatoria;
    @FXML private MenuItem miInsertarAlumnosConvocatoria;
    @FXML private MenuItem miLanzarConvocatoria;
    @FXML private MenuItem miInsertarFP;
    
    //LABEL
    @FXML private Label lblNombre;
    @FXML private Label lblAmbito;
    @FXML private Label lblDuracion;
    
    //BUTTON
    @FXML private Button btnVolver;
    
    //TABLEVIEW
    @FXML private TableView<ConvocatoriaCurso> tblConvocatoriasCurso;

    public TableView<ConvocatoriaCurso> getTblConvocatoriasCurso() {
        return tblConvocatoriasCurso;
    }    
    
    //COLUMNAS DE LA TABLEVIEW
    @FXML private TableColumn<ConvocatoriaCurso, String> clmNombre;
    @FXML private TableColumn<ConvocatoriaCurso, String> clmDias;
    @FXML private TableColumn<ConvocatoriaCurso, String> clmHorario;
    @FXML private TableColumn<ConvocatoriaCurso, Date> clmInicio;
    @FXML private TableColumn<ConvocatoriaCurso, Date> clmFin;
    
   //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<ConvocatoriaCurso> listaConvocatoriasCurso = FXCollections.observableArrayList();
    
    //OBJETOS
    private Curso cu = CursosFXMLController.c;
    private ConvocatoriaCurso cc;
    public static ConvocatoriaCurso convC;
    
    //EVENTO CLICK SOBRE MENUITEM Y BUTTON
    @FXML
    private void eventAction(ActionEvent event) throws IOException, MessagingException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(miVerAlumnos)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosFXML.fxml", btnVolver);
        }
        
        if(evt.equals(miVerEmpresas)) {
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
        
        else if(evt.equals(miVerAlumnosConvocatoria)) {
            convC = tblConvocatoriasCurso.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosConvocatoriaCursoFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miInsertarAlumnosConvocatoria)) {
            convC = tblConvocatoriasCurso.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarCursoRealizadoFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miLanzarConvocatoria)) {
            cc = tblConvocatoriasCurso.getSelectionModel().getSelectedItem();
            //Correo.enviarEmails(con, cc.getNombre());
        }
        
        else if(evt.equals(btnVolver)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/CursosFXML.fxml", btnVolver);
        }
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNombre.setText(cu.getNombre().toUpperCase());
        lblAmbito.setText(cu.getFamiliaProfesional().toUpperCase());
        lblDuracion.setText(String.valueOf(cu.getDuracion()));
        
        ConvocatoriaCurso.verConvocatoriasCurso(con, listaConvocatoriasCurso, cu.getNombre());
        
        //Enlazar listas a TableView
        tblConvocatoriasCurso.setItems(listaConvocatoriasCurso);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<ConvocatoriaCurso, String>("nombre"));
        clmDias.setCellValueFactory(new PropertyValueFactory<ConvocatoriaCurso, String>("dias"));
        clmHorario.setCellValueFactory(new PropertyValueFactory<ConvocatoriaCurso, String>("horario"));
        clmInicio.setCellValueFactory(new PropertyValueFactory<ConvocatoriaCurso, Date>("inicio"));
        clmFin.setCellValueFactory(new PropertyValueFactory<ConvocatoriaCurso, Date>("fin"));
    }    
    
}
