package id.tsi.mmw.console;

import id.tsi.mmw.util.helper.EmailHelper;

public class SendEmailConsole {


    public static void main(String[] args) {

        String host = "smtp.gmail.com";
        int port = 465;
        boolean authRequired = true;
        boolean startTls = false;
        String username = "bachtiar.madya.p@gmail.com";
        String password = "mozx izbl ageo wkzw";

        String from = "tj5Rz@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email message.";
        String recipients = "bmp@ic.sg";

        EmailHelper.sendEmail(host, port, username, password, authRequired, startTls, from, subject, body, recipients, null);

    }
}
