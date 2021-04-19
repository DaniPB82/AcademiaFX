/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

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
 * @author Dani
 */
public class Empresa {
    /*PROPIEDADES*/
    //private StringProperty nif;
    private StringProperty nombre;
    private StringProperty familiaProfesional;
    private StringProperty poblacion;
    private StringProperty responsable;
    private StringProperty telefono;
    private StringProperty email;
    
    /*CONSTRUCTORES*/
    public Empresa() {
        
    }
    
    public Empresa(String nombre, String familiaProfesional, String poblacion, String responsable, String telefono, String email) {
        this.nombre = new SimpleStringProperty(nombre);
        this.familiaProfesional = new SimpleStringProperty(familiaProfesional);
        this.poblacion = new SimpleStringProperty(poblacion);
        this.responsable = new SimpleStringProperty(responsable);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
    }
    
    /*MÉTODOS*/
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }
    
    public String getFamiliaProfesional() {
        return familiaProfesional.get();
    }

    public void setFamiliaProfesional(String familiaProfesional) {
        this.familiaProfesional = new SimpleStringProperty(familiaProfesional);
    }

    public StringProperty familiaProfesionalProperty() {
        return familiaProfesional;
    }
    
    public String getPoblacion() {
        return poblacion.get();
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = new SimpleStringProperty(poblacion);
    }

    public StringProperty poblacionProperty() {
        return poblacion;
    }
    
    public String getResponsable() {
        return responsable.get();
    }

    public void setResponsable(String responsable) {
        this.responsable = new SimpleStringProperty(responsable);
    }

    public StringProperty responsableProperty() {
        return responsable;
    }
    
    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono = new SimpleStringProperty(telefono);
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    public static void insertarEmpresa(ConexionMySQL con, String n, String fp, String p, String r, String t, String e) {
        try {
            String sql = "INSERT INTO EMPRESA VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, n.toUpperCase());
            pst.setString(2, fp.toUpperCase());
            pst.setString(3, p.toUpperCase());
            pst.setString(4, r.toUpperCase());
            pst.setString(5, t);
            pst.setString(6, e);
            pst.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void modificarEmpresa(ConexionMySQL con, String nombreNuevo, String fp, String p, String r, String t, String e, String nombreViejo) {
        try {
            String sql = "UPDATE EMPRESA SET NOMBRE=?, FAMILIA_PROFESIONAL_EMPRESA=?, POBLACION=?, " +
                "RESPONSABLE=?, TELEFONO=?, EMAIL=? WHERE NOMBRE=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, nombreNuevo.toUpperCase());
            update.setString(2, fp.toUpperCase());
            update.setString(3, p.toUpperCase());
            update.setString(4, r.toUpperCase());
            update.setString(5, t);
            update.setString(6, e);
            update.setString(7, nombreViejo);
            update.execute();
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarEmpresa(ConexionMySQL con, String nombre) {
        try {
            String sql = "DELETE FROM EMPRESA WHERE NOMBRE=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, nombre);
            delete.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
        }
        try {
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la conexión");
        }
    }
    
    public static int comprobarNombres(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT nombre FROM EMPRESA";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("nombre").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    
    
    public static void verEmpresas(ConexionMySQL con, ObservableList<Empresa> lista) {
        try {
            String sql = "SELECT * FROM EMPRESA";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre").toUpperCase(),
                    rs.getString("familia_profesional_empresa").toUpperCase(),
                    rs.getString("poblacion").toUpperCase(),
                    rs.getString("responsable").toUpperCase(),
                    rs.getString("telefono"),
                    rs.getString("email")));
                    //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorNombre(ConexionMySQL con, ObservableList<Empresa> lista, String nombre) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE NOMBRE=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorAmbito(ConexionMySQL con, ObservableList<Empresa> lista, String fp) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE FAMILIA_PROFESIONAL_EMPRESA=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorPoblacion(ConexionMySQL con, ObservableList<Empresa> lista, String poblacion) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE POBLACION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, poblacion); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorResponsable(ConexionMySQL con, ObservableList<Empresa> lista, String responsable) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE RESPONSABLE=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, responsable); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorTelefono(ConexionMySQL con, ObservableList<Empresa> lista, String telefono) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE TELEFONO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, telefono); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarEmpresaPorEmail(ConexionMySQL con, ObservableList<Empresa> lista, String email) {
        try {
            String sql = "SELECT * FROM EMPRESA WHERE EMAIL=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, email); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Empresa(
                    rs.getString("nombre"),
                    rs.getString("familia_profesional_empresa"),
                    rs.getString("poblacion"),
                    rs.getString("responsable"),
                    rs.getString("telefono"),
                    rs.getString("email")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
