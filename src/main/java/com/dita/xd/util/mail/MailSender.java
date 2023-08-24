package com.dita.xd.util.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    private final Properties props;

    private String recipient;
    private String user;
    private String password;

    public MailSender() {
        props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }

    public MailSender(String user, String password, String recipient) {
        this();
        this.user = user;
        this.password = password;
        this.recipient = recipient;
    }

    public boolean sendMail(String code) {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        boolean result = true;

        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Verification Code");
            message.setText("The verification code is " + code);
            Transport.send(message);
        } catch (MessagingException e) {
            result = false;
        }
        return result;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
