/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dani
 */
public class ConexionMySQL {
    private Connection conexion;
       
    public ConexionMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("El driver se ha cargado correctamente");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se puede cargar el Driver");
        }
        try {
            conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306/academiafx", "user", "password");      // Introducir el 'user' y la 'password' del gestor de la BBDD
            System.out.println("Se ha conectado a la base de adtos correctamente");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos");
        }
    }

    public Connection getConexion() {
        return conexion;
    }
    
    
    
}
