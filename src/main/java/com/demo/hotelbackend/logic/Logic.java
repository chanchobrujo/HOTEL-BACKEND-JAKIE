package com.demo.hotelbackend.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static Boolean verifyCross(String date1, String date2, Date datein, Date dateend) {
        try {
            if (
                (Logic.convertDate(date1).after(datein) && Logic.convertDate(date1).before(dateend)) ||
                (Logic.convertDate(date1).equals(datein) || Logic.convertDate(date1).equals(dateend)) ||
                (Logic.convertDate(date1).after(datein) && Logic.convertDate(date1).before(dateend)) ||
                (Logic.convertDate(date1).equals(datein) || Logic.convertDate(date1).equals(dateend))
            ) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date convertDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date _date1 = dateFormat.parse(date);

            return _date1;
        } catch (Exception e) {
            return null;
        }
    }

    public static int DifferenceOfDaysBetweenDates(String date1, String date2) {
        Date da1 = Logic.convertDate(date1);
        Date da2 = Logic.convertDate(date2);
        int milisecondsByDay = 86400000;

        return (int) ((da2.getTime() - da1.getTime()) / milisecondsByDay);
    }

    public static int DifferenceOfDaysBetweenDates2(Date date1, Date date2) {
        int milisecondsByDay = 86400000;

        return (int) ((date1.getTime() - date2.getTime()) / milisecondsByDay);
    }
}
