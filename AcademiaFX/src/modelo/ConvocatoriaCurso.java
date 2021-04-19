/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author -
 */
public class ConvocatoriaCurso {
    
    /*PROPIEDADES*/
    private StringProperty nombre;
    private StringProperty dias;
    private StringProperty horario;
    private Date inicio;
    private Date fin;
    
    /*CONSTRUCTORES*/
    public ConvocatoriaCurso() {
        
    }
   
    public ConvocatoriaCurso(String nombre, String dias, String horario, /*String inicio, String fin*/Date inicio, Date fin) {
        this.nombre = new SimpleStringProperty(nombre);
        this.dias = new SimpleStringProperty(dias);
        this.horario = new SimpleStringProperty(horario);
        this.inicio = inicio;
        this.fin = fin;
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
    
    public String getDias() {
        return dias.get();
    }

    public void setDias(String dias) {
        this.dias = new SimpleStringProperty(dias);
    }

    public StringProperty diasProperty() {
        return dias;
    }
    
    public String getHorario() {
        return horario.get();
    }

    public void setHorario(String horario) {
        this.horario = new SimpleStringProperty(horario);
    }

    public StringProperty horarioProperty() {
        return horario;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
    
    public static void modificarConvocatoriaCurso(ConexionMySQL con, String nombreNuevo, String d, String h, String i, String f/*Date i, Date f*/, String nombreViejo) {
        try {
            String sql = "UPDATE CONVOCATORIA_CURSO SET NOMBRE_CURSO=?, DIAS_SEMANA=?, HORARIO=?, FECHA_INICIO=?, FECHA_FIN=? WHERE NOMBRE_CURSO=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, nombreNuevo);
            update.setString(2, d);
            update.setString(3, h);
            update.setString(4, i);
            update.setString(5, f);
            update.setString(6, nombreViejo);
            update.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarConvocatoriaCurso(ConexionMySQL con, String nombre) {
        try {
            String sql = "DELETE FROM CONVOCATORIA_CURSO WHERE NOMBRE_CURSO=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, nombre);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaCurso.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void verConvocatoriasCurso(ConexionMySQL con, ObservableList<ConvocatoriaCurso> lista, String nombre) {
        try {
            String sql = "SELECT CONVOCATORIA_CURSO.* FROM CONVOCATORIA_CURSO INNER JOIN CURSO USING (NOMBRE_CURSO) WHERE CURSO.NOMBRE_CURSO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new ConvocatoriaCurso(
                    rs.getString("nombre_curso").toUpperCase(),
                    rs.getString("dias_semana").toUpperCase(),
                    rs.getString("horario").toUpperCase(),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin")));
                //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertarConvocatoriaCurso(ConexionMySQL con, String nombre, String d, String h, Date i, Date f) {
        try {
            String sql = "INSERT INTO CONVOCATORIA_CURSO VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insert = con.getConexion().prepareStatement(sql);
            insert.setString(1, nombre);
            insert.setString(2, d);
            insert.setString(3, h);
            insert.setDate(4, i);
            insert.setDate(5, f);
            insert.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<Date> fechasInicioDeCurso(ConexionMySQL con, ObservableList<Date> lista, String nombre) {
        try {
            String sql = "SELECT inicio FROM CONVOCATORIA_CURSO INNER JOIN CURSO USING (NOMBRE_CURSO) WHERE CURSO.NOMBRE_CURSO=? ORDER BY INICIO DESC";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getDate(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
