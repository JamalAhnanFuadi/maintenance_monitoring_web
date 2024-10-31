package id.tsi.mmw.console;

import id.tsi.mmw.util.helper.EmailHelper;

public class SendEmailConsole {


    public static void main(String[] args) {

        String subject = "Test Email";
        String body = "This is a test email message.";
        String recipients = "bachtiar.madya.p@gmail.com";

        EmailHelper.sendEmail(subject, body, recipients, null);

    }
}
