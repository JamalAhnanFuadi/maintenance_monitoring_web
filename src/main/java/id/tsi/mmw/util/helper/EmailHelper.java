package id.tsi.mmw.util.helper;

import id.tsi.mmw.property.Constants;
import id.tsi.mmw.util.log.AppLogger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

public class EmailHelper {

    private static final AppLogger log = new AppLogger(EmailHelper.class);

    public EmailHelper() {
    }

    public static String sendEmail(String host, int port, String username, String password, boolean authRequired,
                                   boolean startTls, String sender, String subject, String body, String recipients, List<String> ccList) {
        final String methodName = "sendEmail";
        log.debug(methodName, "Start");

        String result = null;

        // Initialize the email connection properties
        Properties props = initializeProperties(host, port, authRequired, startTls);
        // Create a JavaMail Session object based on the provided properties
        Session session = createSession(props, authRequired, username, password);

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender)); // Set the sender of the email

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));// Set the recipients of
            // the email
            // Set the CC recipients if provided
            if (ccList != null && !ccList.isEmpty()) {
                InternetAddress[] ccAddresses = new InternetAddress[ccList.size()];
                for (int i = 0; i < ccList.size(); i++) {
                    ccAddresses[i] = new InternetAddress(ccList.get(i));
                }
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }

            // Create a new multipart message for the email body and attachments
            Multipart multipart = new MimeMultipart();
            // Create a new MimeBodyPart for the email body
            MimeBodyPart bodyPart = new MimeBodyPart();
            // Set the content of the body part
            bodyPart.setContent(body, "text/html; charset=utf-8");
            // Add the body part to the multipart message
            multipart.addBodyPart(bodyPart);
            // Attach the multipart to the message
            message.setContent(multipart);

            // Send the email using the session and message
            log.debug(methodName, "Sending email ... ");
            Transport.send(message);
            // Set the result to indicate success
            result = Constants.MESSAGE_SUCCESS;
            log.debug(methodName, "Email sent successfully!");

        } catch (MessagingException e) {
            log.error(methodName, e);
        }
        log.debug(methodName, "Completed");
        return result;
    }

    private static Properties initializeProperties(String host, int port, boolean authRequired, boolean startTls) {
        // Create a new Properties object.
        Properties props = new Properties();
        props.put("mail.smtp.auth", authRequired); // Set the authentication requirement property.
        props.put("mail.smtp.starttls.enable", startTls); // Set the TLS requirement property.
        props.put("mail.smtp.host", host); // Set the host property.
        props.put("mail.smtp.port", port);// Set the port property.
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return props;
    }

    private static Session createSession(Properties properties, boolean authRequired, String username,
                                         String password) {
        // Check if authentication is required and if username and password are provided
        if (authRequired && username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            // If authentication is required and credentials are provided, create a session with an authenticator that
            // provides the username and password
            return Session.getInstance(properties, new javax.mail.Authenticator() {
                /**
                 * This method is called by the JavaMail framework to obtain the username and password for
                 * authentication. It returns a PasswordAuthentication object containing the username and password.
                 */
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        } else {
            // If authentication is not required or credentials are not provided, create a session without an
            // authenticator
            return Session.getInstance(properties);
        }
    }
}
