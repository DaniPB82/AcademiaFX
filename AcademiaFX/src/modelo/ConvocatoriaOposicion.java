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
public class ConvocatoriaOposicion {
    
    /*PROPIEDADES*/
    private StringProperty nombre;
    private StringProperty dias;
    private StringProperty horario;
    private Date inicio;
    private Date fin;
    
    /*CONSTRUCTORES*/
    public ConvocatoriaOposicion() {
        
    }
   
    public ConvocatoriaOposicion(String nombre, String dias, String horario, Date inicio, Date fin) {
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
    
    public static void modificarConvocatoriaOposicion(ConexionMySQL con, String nombreNuevo, String d, String h, String i, String f, String nombreViejo) {
        try {
            String sql = "UPDATE CONVOCATORIA_OPOSICION SET NOMBRE_OPOSICION=?, DIAS_SEMANA=?, HORARIO=?, FECHA_INICIO=?, FECHA_FIN=? WHERE NOMBRE_OPOSICION=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, nombreNuevo);
            update.setString(2, d);
            update.setString(3, h);
            update.setString(4, i);
            update.setString(5, f);
            update.setString(6, nombreViejo);
            update.execute();
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaOposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarConvocatoriaOposicion(ConexionMySQL con, String nombre) {
        try {
            String sql = "DELETE FROM CONVOCATORIA_OPOSICION WHERE NOMBRE_OPOSICION=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, nombre);
            delete.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaOposicion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
        }
        try {
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaOposicion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la conexión");
        }
    }
    
    public static void verConvocatoriasOposicion(ConexionMySQL con, ObservableList<ConvocatoriaOposicion> lista, String nombre) {
        try {
            String sql = "SELECT CONVOCATORIA_OPOSICION.* FROM CONVOCATORIA_OPOSICION INNER JOIN OPOSICION USING (NOMBRE_OPOSICION) WHERE OPOSICION.NOMBRE_OPOSICION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new ConvocatoriaOposicion(
                    rs.getString("nombre_oposicion").toUpperCase(),
                    rs.getString("dias_semana").toUpperCase(),
                    rs.getString("horario").toUpperCase(),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin")));
                //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaOposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static void insertarConvocatoriaOposicion(ConexionMySQL con, String nombre, String d, String h, Date i, Date f) {
        try {
            String sql = "INSERT INTO CONVOCATORIA_OPOSICION VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insert = con.getConexion().prepareStatement(sql);
            insert.setString(1, nombre);
            insert.setString(2, d);
            insert.setString(3, h);
            insert.setDate(4, i);
            insert.setDate(5, f);
            insert.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConvocatoriaOposicion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
