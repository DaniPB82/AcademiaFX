/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.InsertarOposicionFXMLController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author -
 */
public class FamiliaProfesional {
    
    /*PROPIEDADES*/
    private StringProperty familiaProfesional;
    
    //OBJETOS
    private Oposicion opos = InsertarOposicionFXMLController.op;
    private Curso curs;
    
    /*CONSTRUCTORES*/
    public FamiliaProfesional() {
        
    }
    
    public FamiliaProfesional(String fp) {
        familiaProfesional = new SimpleStringProperty(fp);
    }
    
    /*MÉTODOS*/
    public String getFamiliaProfesional() {
        return familiaProfesional.get();
    }

    public void setFamiliaProfesional(String familiaProfesional) {
        this.familiaProfesional = new SimpleStringProperty(familiaProfesional);
    }
    
    public StringProperty familiaProfesionalProperty() {
        return familiaProfesional;
    }
    
    public static void insertarFPEmpresa(ConexionMySQL con, String fp) {
        try {
            String sql = "INSERT INTO FAMILIA_PROFESIONAL_EMPRESA VALUES (?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp.toUpperCase());
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void insertarFPCurso(ConexionMySQL con, String fp) {
        try {
            String sql = "INSERT INTO FAMILIA_PROFESIONAL_CURSO VALUES (?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp.toUpperCase());
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public static void insertarFPOposicion(ConexionMySQL con, String fp) {
        try {
            String sql = "INSERT INTO FAMILIA_PROFESIONAL_OPOSICION VALUES (?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp.toUpperCase());
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static ObservableList<String> dameFPsEmpresa(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT DISTINCT * FROM FAMILIA_PROFESIONAL_EMPRESA ORDER BY FAMILIA_PROFESIONAL_EMPRESA ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString("familia_profesional_empresa"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> dameFPsCurso(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT DISTINCT * FROM FAMILIA_PROFESIONAL_CURSO ORDER BY FAMILIA_PROFESIONAL_CURSO ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString("familia_profesional_curso"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static ObservableList<String> dameFPsOposicion(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT DISTINCT * FROM FAMILIA_PROFESIONAL_OPOSICION ORDER BY FAMILIA_PROFESIONAL_OPOSICION ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString("familia_profesional_oposicion"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static int comprobarFPsEmpresa(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT * FROM FAMILIA_PROFESIONAL_EMPRESA";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("familia_profesional_empresa").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static int comprobarFPsCurso(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT * FROM FAMILIA_PROFESIONAL_CURSO";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("familia_profesional_curso").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static int comprobarFPsOposicion(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT * FROM FAMILIA_PROFESIONAL_OPOSICION";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("familia_profesional_oposicion").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    /*public static int comprobarFP(ConexionMySQL con, String nombre) {
        int estado = 1;
        
        
        
        
        
        
        try {
            String sql = "SELECT familia_profesional FROM FAMILIA_PROFESIONAL";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("familia_profesional").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FamiliaProfesional.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }*/
}
