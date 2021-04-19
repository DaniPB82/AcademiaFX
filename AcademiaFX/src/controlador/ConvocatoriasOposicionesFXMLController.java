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
import modelo.ConvocatoriaOposicion;
import modelo.Correo;
import modelo.Oposicion;

/**
 * FXML Controller class
 *
 * @author -
 */
public class ConvocatoriasOposicionesFXMLController implements Initializable {

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
    @FXML private Label lblCoste;
    
    //BUTTON
    @FXML private Button btnVolver;
    
    //TABLEVIEW
    @FXML private TableView<ConvocatoriaOposicion> tblConvocatoriasOposicion;

    public TableView<ConvocatoriaOposicion> getTblConvocatoriasOposicion() {
        return tblConvocatoriasOposicion;
    }    
    
    //COLUMNAS DE LA TABLEVIEW
    @FXML private TableColumn<ConvocatoriaOposicion, String> clmNombre;
    @FXML private TableColumn<ConvocatoriaOposicion, String> clmDias;
    @FXML private TableColumn<ConvocatoriaOposicion, String> clmHorario;
    @FXML private TableColumn<ConvocatoriaOposicion, Date> clmInicio;
    @FXML private TableColumn<ConvocatoriaOposicion, Date> clmFin;
    
   //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<ConvocatoriaOposicion> listaConvocatoriasOposicion = FXCollections.observableArrayList();
    
    //OBJETOS
    private Oposicion op = OposicionesFXMLController.o;
    private ConvocatoriaOposicion cop;
    public static ConvocatoriaOposicion convO;
    
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
            cop = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            convO = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            System.out.println(cop.getNombre() + " " + cop.getInicio());
            System.out.println(convO.getNombre() + " " + convO.getInicio());
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosConvocatoriaOposicionFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miInsertarAlumnosConvocatoria)) {
            convO = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarOposicionRealizadaFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miVerAlumnosConvocatoria)) {
            convO = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/AlumnosConvocatoriaOposicionFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miInsertarAlumnosConvocatoria)) {
            convO = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/InsertarOposicionRealizadaFXML.fxml", btnVolver);
        }
        
        else if(evt.equals(miLanzarConvocatoria)) {
            cop = tblConvocatoriasOposicion.getSelectionModel().getSelectedItem();
            //Correo.enviarEmails(con, op.getNombre());
            //correo.sendEmail();
        }
        
        else if(evt.equals(btnVolver)) {
            ventana.cargarEnVentanaMenuItemAlumnos("/vista/OposicionesFXML.fxml", btnVolver);
        }
    }
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNombre.setText(op.getNombre().toUpperCase());
        lblAmbito.setText(op.getFamiliaProfesional().toUpperCase());
        lblDuracion.setText(String.valueOf(op.getDuracion()));
        lblCoste.setText(String.valueOf(op.getCoste()));
        
        ConvocatoriaOposicion.verConvocatoriasOposicion(con, listaConvocatoriasOposicion, op.getNombre());
        
        //Enlazar listas a TableView
        tblConvocatoriasOposicion.setItems(listaConvocatoriasOposicion);
        
        //Enlazar columnas con atributos
        clmNombre.setCellValueFactory(new PropertyValueFactory<ConvocatoriaOposicion, String>("nombre"));
        clmDias.setCellValueFactory(new PropertyValueFactory<ConvocatoriaOposicion, String>("dias"));
        clmHorario.setCellValueFactory(new PropertyValueFactory<ConvocatoriaOposicion, String>("horario"));
        clmInicio.setCellValueFactory(new PropertyValueFactory<ConvocatoriaOposicion, Date>("inicio"));
        clmFin.setCellValueFactory(new PropertyValueFactory<ConvocatoriaOposicion, Date>("fin"));
    }    
    
}
