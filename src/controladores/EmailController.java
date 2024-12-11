package controladores;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailController {

    public void enviarEmail(String receptor, String asunto, String mensaje, File selectedImageFile) {

        // Credenciales del correo de envío
        String correoEnvia = "matpadron26@gmail.com";  // Asegúrate de usar tu correo
        String contraseña = "libo axsh sebg kuaa";  // Usa una contraseña de aplicación si tienes habilitada la verificación en dos pasos

        // Configuración de propiedades para el servidor SMTP de Gmail
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587"); // Puerto para STARTTLS
        propiedad.setProperty("mail.smtp.auth", "true");
        propiedad.setProperty("mail.debug", "true"); // Activar el registro para ver detalles de la conexión y la autenticación

        // Configuración de la sesión de correo
        Session sesion = Session.getInstance(propiedad, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoEnvia, contraseña);
            }
        });

        try {
            // Crear el mensaje de correo
            MimeMessage mail = new MimeMessage(sesion);
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
            mail.setSubject(asunto);

            // Crear el cuerpo del mensaje
            MimeMultipart multipart = new MimeMultipart("mixed");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(mensaje);
            multipart.addBodyPart(textPart);

            // Adjuntar imagen si es proporcionada
            if (selectedImageFile != null) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(selectedImageFile);
                multipart.addBodyPart(attachPart);
            }

            mail.setContent(multipart);

            // Conectar y enviar el correo
            Transport transport = sesion.getTransport("smtp");
            transport.connect(correoEnvia, contraseña);
            transport.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transport.close();

            // Mensaje de éxito
            JOptionPane.showMessageDialog(null, "Correo enviado exitosamente.");
        } catch (MessagingException | IOException ex) {
            // Capturar errores y mostrar el mensaje
            JOptionPane.showMessageDialog(null, "Error al enviar el correo: " + ex.getMessage());
        }
    }
}
