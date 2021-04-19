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
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author Dani
 */
public class Oposicion {
    
    /*PROPIEDADES*/
    private StringProperty nombre;
    private StringProperty familiaProfesional;
    private IntegerProperty duracion;
    private FloatProperty coste;
    
    /*CONSTRUCTORES*/
    public Oposicion() {
        
    }
    
    public Oposicion(String nombre, String familiaProfesional, int duracion, float coste) {
        this.nombre = new SimpleStringProperty(nombre);
        this.familiaProfesional = new SimpleStringProperty(familiaProfesional);
        this.duracion = new SimpleIntegerProperty(duracion);
        this.coste = new SimpleFloatProperty(coste);
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
    
    public int getDuracion() {
        return duracion.get();
    }

    public void setDuracion(int duracion) {
        this.duracion = new SimpleIntegerProperty(duracion);
    }

    public IntegerProperty duracionProperty() {
        return duracion;
    }
    
    public float getCoste() {
        return coste.get();
    }

    public void setCoste(float coste) {
        this.coste = new SimpleFloatProperty(coste);
    }
    
    public FloatProperty costeProperty() {
        return coste;
    }
    
    public static void modificarOposicion(ConexionMySQL con, String nombreNuevo, String fp, int d, float c, String nombreViejo, String familiaVieja) {
        try {
            String sql = "UPDATE OPOSICION SET NOMBRE_OPOSICION=?, FAMILIA_PROFESIONAL_OPOSICION=?, DURACION=?, COSTE=? WHERE NOMBRE_OPOSICION=? AND FAMILIA_PROFESIONAL_OPOSICION=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, nombreNuevo.toUpperCase());
            update.setString(2, fp.toUpperCase());
            update.setInt(3, d);
            update.setFloat(4, c);
            update.setString(5, nombreViejo);
            update.setString(6, familiaVieja);
            update.execute();
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarOposicion(ConexionMySQL con, String nombre, String ambito) {
        try {
            String sql = "DELETE FROM OPOSICION WHERE NOMBRE_OPOSICION=? AND FAMILIA_PROFESIONAL_OPOSICION=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, nombre);
            delete.setString(2, ambito);
            delete.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
        }
        try {
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la conexión");
        }
    }
    
    public static void verOposiciones(ConexionMySQL con, ObservableList<Oposicion> lista) {
        try {
            Statement sql = con.getConexion().createStatement();
            ResultSet rs = sql.executeQuery("SELECT * FROM OPOSICION");
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
                //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertarOposicion(ConexionMySQL con, String nombre, String fp, int d, float c) {
        try {
            String sql = "INSERT INTO OPOSICION VALUES (?, ?, ?, ?)";
            PreparedStatement insert = con.getConexion().prepareStatement(sql);
            insert.setString(1, nombre.toUpperCase());
            insert.setString(2, fp.toUpperCase());
            insert.setInt(3, d);
            insert.setFloat(4, c);
            insert.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String oposicionSeleccionada(TableView<Oposicion> tablaOposiciones) {
        Oposicion o = new Oposicion();
        o = tablaOposiciones.getSelectionModel().getSelectedItem();
        String nombre = o.getNombre();
        return nombre;
    }
    
    public static ObservableList<String> nombresOposiciones(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT nombre_oposicion FROM OPOSICION ORDER BY NOMBRE_OPOSICION ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static int comprobarNombres(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT nombre_oposicion FROM OPOSICION";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("nombre_oposicion").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static int comprobarNombresYFamilias(ConexionMySQL con, String nombre, String ambito) {
        int estado = 1;
        try {
            String sql = "SELECT nombre_oposicion, familia_profesional_oposicion FROM OPOSICION";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("nombre_oposicion").equalsIgnoreCase(nombre) && rs.getString("familia_profesional_oposicion").equalsIgnoreCase(ambito)) {
                    estado = 0;
                }
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static void buscarOposicionPorDni(ConexionMySQL con, ObservableList<Oposicion> lista, String dni) {
        try {
            String sql = "SELECT OPOSICION.* FROM OPOSICION INNER JOIN OPOSICION_REALIZADA USING (NOMBRE_OPOSICION) " +
                "INNER JOIN ALUMNO USING (DNI) WHERE DNI=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarOposicionPorNombre(ConexionMySQL con, ObservableList<Oposicion> lista, String nombre) {
        try {
            String sql = "SELECT * FROM OPOSICION WHERE NOMBRE_OPOSICION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarOposicionPorAmbito(ConexionMySQL con, ObservableList<Oposicion> lista, String fp) {
        try {
            String sql = "SELECT * FROM OPOSICION WHERE FAMILIA_PROFESIONAL_OPOSICION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarOposicionPorDuracion(ConexionMySQL con, ObservableList<Oposicion> lista, int duracion) {
        try {
            String sql = "SELECT * FROM OPOSICION WHERE DURACION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setInt(1, duracion); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarOposicionPorCoste(ConexionMySQL con, ObservableList<Oposicion> lista, float coste) {
        try {
            String sql = "SELECT * FROM OPOSICION WHERE COSTE=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setFloat(1, coste); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Oposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("familia_profesional_oposicion").toUpperCase(),
                    rs.getInt("duracion"),
                    rs.getFloat("coste")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<String> ambitos(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT familia_profesional_oposicion FROM OPOSICION ORDER BY FAMILIA_PROFESIONAL_OPOSICION ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Oposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
