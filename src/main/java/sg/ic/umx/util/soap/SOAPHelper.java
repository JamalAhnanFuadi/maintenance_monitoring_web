package sg.ic.umx.util.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import sg.ic.umx.util.log.AppLogger;

public class SOAPHelper {

    private static final AppLogger log = new AppLogger(SOAPHelper.class);

    private static final String PREFERRED_PREFIX = "soap";
    private static final String SOAP_ENV_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final String WSSE = "wsse";

    private SOAPHelper() {}

    public static SOAPMessage createAuthenticatedSOAPMessage(String username, String password) {
        final String methodName = "createAuthenticatedSOAPMessage";
        SOAPMessage message = null;
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            message = messageFactory.createMessage();
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();

            envelope.removeNamespaceDeclaration(envelope.getPrefix());
            envelope.addNamespaceDeclaration(PREFERRED_PREFIX, SOAP_ENV_NAMESPACE);
            envelope.setPrefix(PREFERRED_PREFIX);
            envelope.getHeader().setPrefix(PREFERRED_PREFIX);
            envelope.getBody().setPrefix(PREFERRED_PREFIX);

            SOAPHeader header = envelope.getHeader();

            SOAPElement security = header.addChildElement("Security", WSSE,
                    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

            SOAPElement usernameToken = security.addChildElement("UsernameToken", WSSE);

            usernameToken.addChildElement("Username", WSSE).addTextNode(username);

            SOAPElement passwordNode = usernameToken.addChildElement("Password", WSSE);
            passwordNode.setAttribute("Type",
                    "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
            passwordNode.addTextNode(password);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return message;
    }

    public static String toString(SOAPMessage message) {
        final String methodName = "toString";
        String output = "";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            message.writeTo(baos);
            output = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } catch (SOAPException | IOException ex) {
            log.error(methodName, ex);
        }

        return output;
    }

}
