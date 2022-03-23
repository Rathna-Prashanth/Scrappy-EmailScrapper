package io.prashanth.scrappy.extensions;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SMPTHandler {

    public void getMail(String title, String subject, boolean cc) {
        final String username = "r-prashanth@outlook.com";
        final String password = "Optisol@2020";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("r-prashanth@outlook.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("<Email1>"));
            if (cc) {
                message.addRecipients(Message.RecipientType.CC, InternetAddress.parse("<Email2>"));
            }
            message.setSubject(title);
            message.setText(subject);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
