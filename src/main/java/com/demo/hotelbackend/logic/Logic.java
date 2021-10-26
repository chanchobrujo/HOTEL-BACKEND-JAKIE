package com.demo.hotelbackend.logic;

import java.util.Properties;
import java.util.UUID;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Logic {

    public static String sendMail(String correro, String Asunto, String mensajee) throws Exception {
        try {
            Properties propiedad = new Properties();
            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedad.setProperty("mail.smtp.starttls.enable", "true");
            propiedad.setProperty("mail.smtp.port", "587");
            propiedad.setProperty("mail.smtp.auth", "true");
            Session sesion = Session.getDefaultInstance(propiedad);
            String correoEnvia = "cuentaempresarial526@gmail.com";
            String contrasena = "drcsfkzfhgjxwtjm";
            String destinatario = correro;
            String asunto = Asunto;
            String mensaje = mensajee;
            MimeMessage mail = new MimeMessage(sesion);
            mail.setFrom(new InternetAddress(correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            MimeMultipart mp = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent("" + "<br><h1> EMPRESA HOTEL </h1><br>" + "<h2>" + mensaje + "</h2><br>", "text/html");
            mp.addBodyPart(html);
            mail.setContent(mp);
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia, contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();
            return "Correo enviado";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String generatedID() {
        return (String) UUID.randomUUID().toString().toUpperCase().subSequence(0, 5);
    }
}
