/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Dani
 */
public class CambiarVentana {
    
    /*MÉTODOS*/
    public void cargarVentanaNueva(String url, Event event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
        newStage.setTitle("BASE DE DATOS MEATZE");
    }
    
    public void cargarEnVentana(String url, Event event) throws IOException {
        
        Object eventSource = event.getSource();
        Node sourceAsNode = (Node)eventSource;
        Scene oldScene = sourceAsNode.getScene();
        Window window = oldScene.getWindow();
        Stage stage = (Stage)window;
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.setTitle("BASE DE DATOS MEATZE");
    }
    
    public void cargarEnVentanaMenuItemAlumnos(String url, Node nodo) throws IOException {
        
        Stage stage = (Stage) nodo.getScene().getWindow();
                        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.setTitle("BASE DE DATOS MEATZE");
    }
    
    /*public void cargarEnVentanaNuevaMenuItem(String url, Event event) throws IOException {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
        //Stage stage = (Stage) nodo.getScene().getWindow();
                        
        //Parent root = FXMLLoader.load(getClass().getResource(url));
        //Scene newScene = new Scene(root);
        //stage.setScene(newScene);
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
    }*/
    
    public void cargarEnVentanaNuevaMenuItem(String url, Node nodo) throws IOException {
        
        nodo.getScene().getWindow().hide();
                        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.show();
        newStage.setTitle("BASE DE DATOS MEATZE");
    }
}
