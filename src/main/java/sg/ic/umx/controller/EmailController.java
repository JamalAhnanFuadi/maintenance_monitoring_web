/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Identiticoders, and individual contributors as indicated by the @author tags. All Rights Reserved
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License (the License).
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not
 * allowed.
 *
 */
package sg.ic.umx.controller;

import java.util.List;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailController extends BaseController {

    private String host;
    private int port;
    private String sender;

    public EmailController() {
        log = getLogger(this.getClass());
    }

    public boolean send(List<String> recipient, String subject, String content) {

        final String methodName = "send";
        start(methodName);

        Email email = new SimpleEmail();
        email.setHostName(host);
        email.setSmtpPort(port);

        boolean success = false;

        try {
            email.setSSLOnConnect(false);
            email.setFrom(sender);

            for (String addr : recipient) {
                email.addTo(addr);
            }

            email.setSubject(subject);
            email.setMsg(content);
            email.send();

            log.info(methodName, "--- email begin ---");
            log.info(methodName, "From    : " + sender);
            log.info(methodName, "To      : " + recipient);
            log.info(methodName, "Subject : " + subject);
            log.info(methodName, content);
            log.info(methodName, "--- email end ---");
            success = true;
        } catch (EmailException ex) {
            log.error(methodName, ex);
        }
        return success;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {

        this.port = port;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
