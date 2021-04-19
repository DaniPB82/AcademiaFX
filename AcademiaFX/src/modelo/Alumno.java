/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author Dani
 */
public class Alumno {
    /*PROPIEDADES*/
    private StringProperty dni;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty poblacion;
    private StringProperty telefono;
    private StringProperty email;
    private StringProperty historial;
    //private File cv;
    
    /*CONSTRUCTORES*/
    public Alumno() {
    
    }

    /*public Alumno(String dni, String nombre, String apellido, String poblacion, String telefono, String email) {
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.poblacion = new SimpleStringProperty(poblacion);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
    }*/
    
    public Alumno(String dni, String nombre, String apellido, String poblacion, String telefono, String email, String historial) {
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.poblacion = new SimpleStringProperty(poblacion);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.historial = new SimpleStringProperty(historial);
    }
    
    /*MÉTODOS*/
    public String getDni() {
        return dni.get();
    }

    public void setDni(String dni) {
        this.dni = new SimpleStringProperty(dni);
    }

    public StringProperty dniProperty() {
        return dni;
    }
    
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }
    
    public StringProperty nombreProperty() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido = new SimpleStringProperty(apellido);
    }

    public StringProperty apellidoProperty() {
        return apellido;
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
    
    public String getHistorial() {
        return historial.get();
    }

    public void setHistorial(String historial) {
        this.historial = new SimpleStringProperty(historial);
    }

    public StringProperty historialProperty() {
        return historial;
    }
    
    
    public static void modificarAlumno(ConexionMySQL con, String dniNuevo, String n, String a, String po, String t, String e, String dniViejo) {
        try {
            String sql = "UPDATE ALUMNO SET DNI=?, NOMBRE_ALUMNO=?, APELLIDO=?, " +
                "POBLACION=?, TELEFONO=?, EMAIL=? WHERE DNI=?";
            PreparedStatement update = con.getConexion().prepareStatement(sql);
            update.setString(1, dniNuevo);
            update.setString(2, n);
            update.setString(3, a);
            update.setString(4, po);
            update.setString(5, t);
            update.setString(6, e);
            update.setString(7, dniViejo);
            update.execute();
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void eliminarAlumno(ConexionMySQL con, String dni) {
        try {
            String sql = "DELETE FROM ALUMNO WHERE DNI=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, dni);
            delete.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
        }
        try {
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la conexión");
        }
    }
    
    public static void eliminarAlumnoDeConvocatoriaCurso(ConexionMySQL con, String dni) {
        try {
            String sql = "DELETE FROM ALUMNOS_CONVOCATORIA_CURSO WHERE DNI=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, dni);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void eliminarAlumnoDeConvocatoriaOposicion(ConexionMySQL con, String dni) {
        try {
            String sql = "DELETE FROM ALUMNOS_CONVOCATORIA_OPOSICION WHERE DNI=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, dni);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void eliminarAlumnoDeOposicion(ConexionMySQL con, String dni) {
        try {
            String sql = "DELETE FROM OPOSICION_REALIZADA WHERE DNI=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, dni);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void eliminarAlumnoDeCurso(ConexionMySQL con, String dni) {
        try {
            String sql = "DELETE FROM CURSO_REALIZADO WHERE DNI=?";
            PreparedStatement delete = con.getConexion().prepareStatement(sql);
            delete.setString(1, dni);
            delete.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo en la sentencia");
            System.out.println("Fallo en la conexión");
        }
        
    }
    
    public static void verAlumnos(ConexionMySQL con, ObservableList<Alumno> lista) {
        try {
            Statement sql = con.getConexion().createStatement();
            ResultSet rs = sql.executeQuery("SELECT * FROM ALUMNO");
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni").toUpperCase(),
                    rs.getString("nombre_alumno").toUpperCase(),
                    rs.getString("apellido").toUpperCase(),
                    rs.getString("poblacion").toUpperCase(),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
                //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertarAlumno(ConexionMySQL con, String dni, String n, String a, String po, String t, String e, String h) {
        try {
            String sql = "INSERT INTO ALUMNO VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = con.getConexion().prepareStatement(sql);
            insert.setString(1, dni);
            insert.setString(2, n);
            insert.setString(3, a);
            insert.setString(4, po);
            insert.setString(5, t);
            insert.setString(6, e);
            insert.setString(7, "");
            insert.execute();
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String alumnoSeleccionado(TableView<Alumno> tablaAlumnos) {
        Alumno a = new Alumno();
        a = tablaAlumnos.getSelectionModel().getSelectedItem();
        String dni = a.getDni();
        return dni;
    }
    
    public static int comprobarDnis(ConexionMySQL con, String dni) {
        int estado = 1;
        try {
            String sql = "SELECT dni FROM ALUMNO";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                if(rs.getString("dni").equalsIgnoreCase(dni)) {
                    estado = 0;
                }
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static void guardarHistorial(ConexionMySQL con, String historial, String dni) {
        try {
            String sql = "UPDATE ALUMNO SET historial=? WHERE DNI=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, historial);
            pst.setString(2, dni);
            pst.execute();
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void verCursosRealizados(ConexionMySQL con, ObservableList<Curso> lista, String dni) {
        try {
            String sql = "SELECT * FROM CURSO INNER JOIN CURSOS_REALIZADOS USING (NOMBRE_CURSO) INNER JOIN ALUMNO USING (DNI) WHERE DNI=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Curso(
                    rs.getString("nombre_curso"),
                    rs.getString("ambito"),
                    rs.getInt("duracion")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorDni(ConexionMySQL con, ObservableList<Alumno> lista, String dni) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE DNI=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, dni); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorNombre(ConexionMySQL con, ObservableList<Alumno> lista, String nombre) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE NOMBRE_ALUMNO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorApellido(ConexionMySQL con, ObservableList<Alumno> lista, String apellido) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE APELLIDO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, apellido); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorPoblacion(ConexionMySQL con, ObservableList<Alumno> lista, String poblacion) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE POBLACION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, poblacion); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorTelefono(ConexionMySQL con, ObservableList<Alumno> lista, String telefono) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE TELEFONO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, telefono); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorEmail(ConexionMySQL con, ObservableList<Alumno> lista, String email) {
        try {
            String sql = "SELECT * FROM ALUMNO WHERE EMAIL=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, email); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorNombreOposicion(ConexionMySQL con, ObservableList<Alumno> lista, String nombre) {
        try {
            String sql = "SELECT ALUMNO.* FROM ALUMNO INNER JOIN OPOSICION_REALIZADA USING (DNI) " +
                "INNER JOIN OPOSICION USING (NOMBRE_OPOSICION) WHERE NOMBRE_OPOSICION=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni").toUpperCase(),
                    rs.getString("nombre_alumno").toUpperCase(),
                    rs.getString("apellido").toUpperCase(),
                    rs.getString("poblacion").toUpperCase(),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarAlumnoPorNombreCurso(ConexionMySQL con, ObservableList<Alumno> lista, String nombre) {
        try {
            String sql = "SELECT ALUMNO.* FROM ALUMNO INNER JOIN CURSO_REALIZADO USING (DNI) " +
                "INNER JOIN CURSO USING (NOMBRE_CURSO) WHERE NOMBRE_CURSO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre); 
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni").toUpperCase(),
                    rs.getString("nombre_alumno").toUpperCase(),
                    rs.getString("apellido").toUpperCase(),
                    rs.getString("poblacion").toUpperCase(),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ObservableList<String> poblaciones(ConexionMySQL con, ObservableList<String> lista) {
        try {
            String sql = "SELECT DISTINCT poblacion FROM ALUMNO ORDER BY POBLACION ASC";
            Statement stm = con.getConexion().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()) {
                lista.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public static void alumnosDeConvocatoriaCurso(ConexionMySQL con, ObservableList<Alumno> lista, String nombre, Date inicio) {
        try {
            String sql = "SELECT ALUMNO.* FROM ALUMNO INNER JOIN ALUMNOS_CONVOCATORIA_CURSO USING (DNI) " +
                    "WHERE NOMBRE_CURSO=? AND FECHA_INICIO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDate(2, inicio);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void alumnosDeConvocatoriaOposicion(ConexionMySQL con, ObservableList<Alumno> lista, String nombre, Date inicio) {
        try {
            String sql = "SELECT ALUMNO.* FROM ALUMNO INNER JOIN ALUMNOS_CONVOCATORIA_OPOSICION USING (DNI) " +
                    "WHERE NOMBRE_OPOSICION=? AND FECHA_INICIO=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setDate(2, inicio);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
            //con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void buscarCandidatos(ConexionMySQL con, ObservableList<Alumno> lista, String fp, Date inicio) {
        try {
            //String fecha = inicio.toString();
            String sql = "SELECT DISTINCT ALUMNO.* FROM ALUMNO INNER JOIN ALUMNOS_CONVOCATORIA_CURSO USING (DNI) " +
                "INNER JOIN CONVOCATORIA_CURSO USING (NOMBRE_CURSO) INNER JOIN CURSO USING (NOMBRE_CURSO) " +
                "WHERE CURSO.FAMILIA_PROFESIONAL_CURSO=? AND ALUMNOS_CONVOCATORIA_CURSO.FECHA_INICIO>=?";
            PreparedStatement pst = con.getConexion().prepareStatement(sql);
            pst.setString(1, fp);
            pst.setDate(2, inicio);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                lista.add(new Alumno(
                    rs.getString("dni"),
                    rs.getString("nombre_alumno"),
                    rs.getString("apellido"),
                    rs.getString("poblacion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("historial")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
