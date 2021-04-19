/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Dani
 */
public class Correo {
    /*ATRIBUTOS*/
    private String remitente;
    private String asunto;
    private String mensaje;
    private String destinatario;
    
    /*CONSTRUCTORES*/
    public Correo(String r, String a, String m, String d) {
        remitente = r;
        asunto = a;
        mensaje = m;
        destinatario = d;
    }
    
    /*MÉTODOS*/
    private static String construirMensajePersonalizado(String nombre_alumno, String nombre_curso) {
        String mensajeFinalPersonalizado = "Hola " + nombre_alumno + "," +
            "\n\nVamos a impartir el curso/preparación para oposición '" + nombre_curso + "'" +
            "\nSi te interesa, no dudes en visitar nuestra Web e inscribirte o contactar con nosotros para más información." +
            "\n\nUn saludo.\n";
        
        return mensajeFinalPersonalizado;
    }
    
    private static String construirMensaje(String nombre_curso) {
        String mensajeFinal = "Hola, " +
            "\nVamos a impartir el curso/oposición '" + nombre_curso + "'" +
            "\nSi te interesa, no dudes en visitar nuestra Web e inscribirte o contactar con nosotros para más información." +
            "\n\nUn saludo.\n";
            
        return mensajeFinal;
    }
    
    public static void enviarEmails(ConexionMySQL con, String id) throws NoSuchProviderException, MessagingException {
        
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");            //Datos del servidor de correo saliente, en este caso, gmail
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        //propiedad.setProperty("mail.smtp.startssl.enable", "true"); 
        propiedad.setProperty("mail.smtp.ssl.enable", "true");                          //Habilitar SSL
        propiedad.setProperty("mail.smtp.port", "465");                                 //Puerto SMTP Meatze - GMAIL=587 dependiendo si se utiliza SSL o TLS
        propiedad.setProperty("mail.smtp.auth", "true");                                //Habilitar autorización
            
        Session sesion = Session.getDefaultInstance(propiedad);
            
        String remitente = "";                                     // Colocar entre comillas el correo de salida de correos automáticos
        String contrasena = "";                                    // Colocar entre comillas la contraseña del correo
        String asunto = "ACADEMIAFX - NUEVA CONVOCATORIA DE CURSO / PREPARACIÓN PARA OPOSICIONES";
        String mensaje = construirMensaje(id);
           
        //MimeMessage mail = new MimeMessage(sesion);
            
        Pattern pattern = Pattern                                                   //Patrón de validación E-mail
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        try {    
            String sql = "SELECT DISTINCT EMAIL FROM ALUMNO";
            Statement emails = con.getConexion().createStatement();
            ResultSet listaEmails = emails.executeQuery(sql);
            while(listaEmails.next()) {
                                
                String email = listaEmails.getString(1);
                Matcher mather = pattern.matcher(email);                    //Comprobación del email con el patrón
                if (mather.find() == true) {
                    MimeMessage mail = new MimeMessage(sesion);
                    mail.setFrom(new InternetAddress(remitente));
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    mail.setSubject(asunto);
                    mail.setText(mensaje);

                    Transport transporte = sesion.getTransport("smtp");
                    transporte.connect(remitente, contrasena);
                    transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                    transporte.close();

                    //System.out.println(email + " " + "Email válido");

                } else {
                    //System.out.println(email + " " + "Email inválido");
                }

            }
            JOptionPane.showMessageDialog(null, "Emails enviados");
            
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int comprobarCorreos(ConexionMySQL con) {
        int estado = -1;
        try {
            String sql = "SELECT DISTINCT EMAIL FROM ALUMNO";
            Statement emails = con.getConexion().createStatement();
            ResultSet listaEmails = emails.executeQuery(sql);
            if(listaEmails.next()) {
                estado = 1;
            }
            else {
                estado = 0;
            }
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estado;
    }
    
    public static ResultSet dameCorreos(ConexionMySQL con) {
        ResultSet correos = null;
        try {
            String sql = "SELECT DISTINCT EMAIL FROM ALUMNO";
            Statement emails = con.getConexion().createStatement();
            correos = emails.executeQuery(sql);
            con.getConexion().close();
        } catch (SQLException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correos;
    }
    
    
}
