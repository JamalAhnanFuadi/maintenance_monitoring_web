package id.tsi.mmw.util.helper;

import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.manager.PropertyManager;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.util.log.AppLogger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

public class EmailHelper {

    private static final AppLogger log = new AppLogger(EmailHelper.class);

    /**
     * This function initializes the properties for an email connection. It takes in the host, port, authentication
     * requirement, and TLS requirement as parameters.
     */
    private static Properties initializeProperties() {
        // Create a new Properties object.
        Properties props = new Properties();
        props.put("mail.smtp.auth", PropertyManager.getInstance().getBoolProperty(Property.MAIL_SMTP_AUTH_REQUIRED)); // Set the authentication requirement property.
        props.put("mail.smtp.starttls.enable", PropertyManager.getInstance().getBoolProperty(Property.MAIL_SMTP_STARTTLS)); // Set the TLS requirement property.
        props.put("mail.smtp.host",  PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_HOST)); // Set the host property.
        props.put("mail.smtp.port",  PropertyManager.getInstance().getIntProperty(Property.MAIL_SMTP_PORT));// Set the port property.
        props.put("mail.smtp.socketFactory.port", PropertyManager.getInstance().getIntProperty(Property.MAIL_SMTP_SOCKET_PORT));
        props.put("mail.smtp.socketFactory.class", PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_SOCKET_CLASS));

        return props;
    }

    /**
     * This method creates a JavaMail Session object based on the provided properties. If authentication is required, it
     * creates a session with an authenticator that provides the username and password. If authentication is not
     * required, it creates a session without an authenticator.
     *
     * @param properties The properties that configure the session, including host, port, authentication requirement,
     *        and TLS requirement.
     * @return A JavaMail Session object.
     */
    private static Session createSession(Properties properties) {

        boolean authRequired = PropertyManager.getInstance().getBoolProperty(Property.MAIL_SMTP_AUTH_REQUIRED);
        String username = PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_USERNAME);
        String password = EncryptionManager.getInstance().decrypt(PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_PASSWORD));

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

    /**
     * Adds an attachment to the email message.
     *
     * @param multipart       The multipart message to add the attachment to.
     * @param attachmentPaths The path to the file to attach.
     * @throws IOException        If there is an error reading the file.
     * @throws MessagingException If there is an error creating the attachment.
     */
    private static void addAttachments(Multipart multipart, String attachmentPaths) throws IOException, MessagingException {

        // Create a new File object from the attachment path
        File file = new File(attachmentPaths);

        // Check if the file exists and is a valid file
        if (file.exists() && file.isFile()) {
            // Create a new MimeBodyPart for the attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();

            // Attach the file to the MimeBodyPart
            attachmentPart.attachFile(file);

            // Add the attachment to the multipart message
            multipart.addBodyPart(attachmentPart);
        } else {
            // Log an error if the file is not found or is not a valid file
            log.error("addAttachments", "File not found or is not a valid file: " + attachmentPaths);
        }
    }

    /**
     * Sends an email with an attachment.
     *
     * @param subject         The subject of the email.
     * @param body            The body of the email.
     * @param recipients      The email addresses of the recipients.
     * @param ccList          The email addresses of the CC recipients.
     * @param attachmentPaths The paths to the files to attach.
     * @return A string indicating the result of the email send.
     */
    public static String sendEmailWithAttachment(String subject, String body, String recipients, List<String> ccList, String attachmentPaths){
        final String methodName = "sendEmailWithAttachment";
        log.debug(methodName, "Start");

        String result = null;

        // Initialize the email connection properties
        Properties props = initializeProperties();

        // Create a JavaMail Session object based on the provided properties
        Session session = createSession(props);

        try {
            // Create a new email message
            Message message = new MimeMessage(session);

            // Set the sender of the email
            message.setFrom(new InternetAddress(PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_SENDER)));

            // Set the subject of the email
            message.setSubject(subject);

            // Set the recipients of the email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

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

            // Add attachments if provided
            if (attachmentPaths != null && !attachmentPaths.isEmpty()) {
                addAttachments(multipart, attachmentPaths);
            }

            // Attach the multipart to the message
            message.setContent(multipart);

            log.debug(methodName, "Sending email ... ");

            // Send the email
            Transport.send(message);

            // Set the result to indicate success
            result = Constants.SUCCESS;

            log.debug(methodName, "Email sent successfully to "+recipients);
        } catch (MessagingException | IOException e) {
            // Log an error if there is an exception sending the email
            log.error(methodName, e);
        }

        log.debug(methodName, "Completed");

        // Return the result
        return result;
    }


    /**
     * This method sends an email using the provided parameters.
     *
     * @param subject The subject of the email.
     * @param body The body of the email.
     * @param recipients The list of email addresses of the recipients.
     * @param ccList The list of email addresses to cc
     * @return A string indicating whether the email was sent successfully or an error message.
     */
    public static String sendEmail(String subject, String body, String recipients, List<String> ccList) {
        final String methodName = "sendEmail";
        log.debug(methodName, "Start");

        String result = null;

        // Initialize the email connection properties
        Properties props = initializeProperties();
        // Create a JavaMail Session object based on the provided properties
        Session session = createSession(props);

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            String senderEmail = PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_USERNAME);
            String alias = PropertyManager.getInstance().getProperty(Property.MAIL_SMTP_SENDER);
            message.setFrom(new InternetAddress(senderEmail, alias));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            // the email
            // Set the CC recipients if provided
            if (ccList != null && !ccList.isEmpty()) {
                InternetAddress[] ccAddresses = new InternetAddress[ccList.size()];
                for (int i = 0; i < ccList.size(); i++) {
                    ccAddresses[i] = new InternetAddress(ccList.get(i));
                }
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }

            message.setSubject(subject); // Set the subject of the email

            // Set the content of the body part
            message.setContent(body, "text/html; charset=utf-8");

            // Send the email using the session and message
            log.debug(methodName, "Sending email ... ");
            Transport.send(message);
            // Set the result to indicate success
            result = Constants.SUCCESS;
            log.debug(methodName, "Email sent successfully to "+recipients);

        } catch (MessagingException e) {
            log.error(methodName, e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.debug(methodName, "Completed");
        return result;
    }
}
