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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author -
 */
public class Curso {
    /*PROPIEDADES*/
    private StringProperty nombre;
    private StringProperty familiaProfesional;
    private IntegerProperty duracion;
    
    /*CONSTRUCTORES*/
    public Curso() {
        
    }
    
    public Curso(String nombre, String familiaProfesional, int duracion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.familiaProfesional = new SimpleStringProperty(familiaProfesional);
        this.duracion = new SimpleIntegerProperty(duracion);
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
    
    public static void modificarCurso(ConexionMySQL con, String nombreNuevo, String fp, int d, String nombreViejo, String familiaVieja) {
        try {
            String sql = "UPDATE CURSO SET NOMBRE_CURSO=?, FAMILIA_PROFESIONAL_CURSO=?, DURACION=? WHERE NOMBRE_CURSO=? AND FAMILIA_PROFESIONAL_CURSO=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, nombreNuevo.toUpperCase());
            update.setString(2, fp.toUpperCase());
            update.setInt(3, d);
            update.setString(4, nombreViejo);
            update.setString(5, familiaVieja);
            update.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarCurso(ConexionMySQL con, String nombre, String ambito) {
        try {
            String sql = "DELETE FROM CURSO WHERE NOMBRE_CURSO=? AND FAMILIA_PROFESIONAL_CURSO=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, nombre);
            delete.setString(2, ambito);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void verCursos(ConexionMySQL con, ObservableList<Curso> lista) {
        try {
            Statement sql = con.getConexion().createStatement();
            ResultSet rs = sql.executeQuery("SELECT * FROM CURSO");
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso").toUpperCase(),
                    rs.getString("familia_profesional_curso").toUpperCase(),
                    rs.getInt("duracion")));
                //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertarCurso(ConexionMySQL con, String nombre, String fp, int d) {
        try {
            String sql = "INSERT INTO CURSO VALUES (?, ?, ?)";
            PreparedStatement insert = con.getConexion().prepareStatement(sql);
            insert.setString(1, nombre.toUpperCase());
            insert.setString(2, fp.toUpperCase());
            insert.setInt(3, d);
            insert.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String cursoSeleccionado(TableView<Curso> tablaCursos) {
        Curso c = new Curso();
        c = tablaCursos.getSelectionModel().getSelectedItem();
        String nombre = c.getNombre();
        return nombre;
    }
    
    public static ObservableList<String> nombresCursos(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT nombre_curso FROM CURSO ORDER BY NOMBRE_CURSO ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString(1).toUpperCase());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static int comprobarNombres(ConexionMySQL con, String nombre) {
        int estado = 1;
        try {
            String sql = "SELECT nombre_curso FROM CURSO";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("nombre_curso").equalsIgnoreCase(nombre)) {
                    estado = 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static void buscarCursoPorDni(ConexionMySQL con, ObservableList<Curso> lista, String dni) {
        try {
            String sql = "SELECT * FROM CURSO INNER JOIN CURSO_REALIZADO USING (NOMBRE_CURSO) " +
                "INNER JOIN ALUMNO USING (DNI) WHERE DNI=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso").toUpperCase(),
                    rs.getString("familia_profesional_curso").toUpperCase(),
                    rs.getInt("duracion")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarCursoPorNombre(ConexionMySQL con, ObservableList<Curso> lista, String nombre) {
        try {
            String sql = "SELECT * FROM CURSO WHERE NOMBRE_CURSO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso"),
                    rs.getString("familia_profesional_curso"),
                    rs.getInt("duracion")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarCursoPorAmbito(ConexionMySQL con, ObservableList<Curso> lista, String fp) {
        try {
            String sql = "SELECT * FROM CURSO WHERE FAMILIA_PROFESIONAL_CURSO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso"),
                    rs.getString("familia_profesional_curso"),
                    rs.getInt("duracion")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarCursoPorDuracion(ConexionMySQL con, ObservableList<Curso> lista, int duracion) {
        try {
            String sql = "SELECT * FROM CURSO WHERE DURACION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setInt(1, duracion); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso"),
                    rs.getString("familia_profesional_curso"),
                    rs.getInt("duracion")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<String> ambitos(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT DISTINCT familia_profesional_curso FROM CURSO ORDER BY FAMILIA_PROFESIONAL_CURSO ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString(1).toUpperCase());
            }
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static int comprobarNombresYFamilias(ConexionMySQL con, String nombre, String ambito) {
        int estado = 1;
        try {
            String sql = "SELECT nombre_curso, familia_profesional_curso FROM CURSO";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("nombre_curso").equalsIgnoreCase(nombre) && rs.getString("familia_profesional_curso").equalsIgnoreCase(ambito)) {
                    estado = 0;
                }
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(Curso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
}
