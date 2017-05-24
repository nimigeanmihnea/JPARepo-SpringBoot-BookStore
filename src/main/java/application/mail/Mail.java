package application.mail;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Mihnea on 24/05/2017.
 */

@Component
public class Mail {

    public Mail(){

    }

    public void send(String from, String to, String subject, String message){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("bookstoreapplication@gmail.com", "Bookstore123");
                    }
                });
        try{
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addFrom(new InternetAddress[]{new InternetAddress(from)});
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        }catch (MessagingException ex){
            ex.printStackTrace();
        }
    }
}
