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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import modelo.CambiarVentana;
import modelo.ConexionMySQL;
import modelo.FamiliaProfesional;

/**
 * FXML Controller class
 *
 * @author -
 */
public class InsertarFPFXMLController implements Initializable {

    //TEXTFIELD
    @FXML private TextField txtFP;
    
    //COMBOBOX
    @FXML private ComboBox cbbxCategoria;
    
    //BUTTON
    @FXML private Button btnInsertarFP;
    @FXML private Button btnCancelarFP;
    
    //CONEXIÓN A BBDD
    private ConexionMySQL con = new ConexionMySQL();
    
    //LISTAS
    private ObservableList<String> categoria = FXCollections.observableArrayList();
    
    //VARIABLES
    private int estado;
    
    @FXML
    private void eventAction(ActionEvent event) throws IOException {
        
        CambiarVentana ventana = new CambiarVentana();
        Object evt = event.getSource();
        
        if(evt.equals(btnCancelarFP)) {
            ventana.cargarVentanaNueva("/vista/PrincipalFXML.fxml", event);
        }
        
        else if(evt.equals(btnInsertarFP)) {
            if(!txtFP.getText().equals("") && cbbxCategoria.getValue() != null) {
                if(cbbxCategoria.getValue().toString().equals("EMPRESA")) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de añadir la FAMILIA PROFESIONAL: " + txtFP.getText().toUpperCase() + " en la lista de EMPRESAS?");
                    if(opcion == 0) {
                        estado = FamiliaProfesional.comprobarFPsEmpresa(con, txtFP.getText().toUpperCase());
                        if(estado == 0) {
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de EMPRESA repetida.");
                        }
                        else {
                            FamiliaProfesional.insertarFPEmpresa(con, txtFP.getText().toUpperCase());
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de EMPRESA añadida con éxito.");
                        }
                    }
                }
                else if(cbbxCategoria.getValue().toString().equals("CURSO")) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de añadir la FAMILIA PROFESIONAL: " + txtFP.getText().toUpperCase() + " en la lista de CURSOS?");
                    if(opcion == 0) {
                        estado = FamiliaProfesional.comprobarFPsCurso(con, txtFP.getText().toUpperCase());
                        if(estado == 0) {
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de CURSO repetida.");
                        }
                        else {
                            FamiliaProfesional.insertarFPCurso(con, txtFP.getText().toUpperCase());
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de CURSO añadida con éxito.");
                        }
                    }
                }
                else if(cbbxCategoria.getValue().toString().equals("OPOSICIÓN")) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de añadir la FAMILIA PROFESIONAL: " + txtFP.getText().toUpperCase() + " en la lista de OPOSICIONES?");
                    if(opcion == 0) {
                        estado = FamiliaProfesional.comprobarFPsOposicion(con, txtFP.getText().toUpperCase());
                        if(estado == 0) {
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de OPOSICIÓN repetida.");
                        }
                        else {
                            FamiliaProfesional.insertarFPOposicion(con, txtFP.getText().toUpperCase());
                            JOptionPane.showMessageDialog(null, "FAMILIA PROFESIONAL de OPOSICIÓN añadida con éxito.");
                        }
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Los dos campos son obligatorios");
            }
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoria.addAll("EMPRESA", "CURSO", "OPOSICIÓN");
        
        cbbxCategoria.setItems(categoria);
    }    
    
}
