package sg.ic.umx.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sg.ic.umx.idg.model.RoleListResponse;
import sg.ic.umx.idg.model.UserFieldsResponse;
import sg.ic.umx.idg.model.UserListResponse;
import sg.ic.umx.model.Server;
import sg.ic.umx.util.http.HTTPClient;
import sg.ic.umx.util.http.model.HTTPRequest;
import sg.ic.umx.util.http.model.HTTPResponse;
import sg.ic.umx.util.json.JsonHelper;
import sg.ic.umx.util.property.Property;
import sg.ic.umx.util.soap.SOAPHelper;

public class IDGController extends BaseController {

    private final String apiPath;

    public IDGController() {
        log = getLogger(this.getClass());
        apiPath = getProperty(Property.IDG_API_PATH);
    }

    public UserFieldsResponse getUserFields(Server server, String configurationName) {
        final String methodName = "getUserFields";
        start(methodName);
        UserFieldsResponse result = null;
        final String url = server.getUrl() + apiPath + configurationName + "/userFields";

        HTTPRequest request =
                new HTTPRequest.Builder(url).setBasicAuthentication(server.getUsername(), server.getPassword()).build();

        HTTPResponse response = HTTPClient.get(request);
        result = JsonHelper.fromJson(response.getBody(), UserFieldsResponse.class);
        completed(methodName);
        return result;
    }

    public UserListResponse getUsers(Server server, String configurationName) {
        final String methodName = "getUsers";
        start(methodName);
        UserListResponse result = null;

        final String url = server.getUrl() + apiPath + configurationName + "/users";
        log.info(methodName, "URL: " + url);
        HTTPRequest request =
                new HTTPRequest.Builder(url).setBasicAuthentication(server.getUsername(), server.getPassword()).build();
        HTTPResponse response = HTTPClient.get(request);
        log.info(methodName, "Response Body : " + response.getBody());
        result = JsonHelper.fromJson(response.getBody(), UserListResponse.class);
        completed(methodName);
        return result;
    }

    public RoleListResponse getRoles(Server server, String configurationName) {
        final String methodName = "getRoles";
        start(methodName);
        RoleListResponse result = null;
        final String url = server.getUrl() + apiPath + configurationName + "/userRoleLinks/ByUsers?linkType=DIRECT";
        HTTPRequest request =
                new HTTPRequest.Builder(url).setBasicAuthentication(server.getUsername(), server.getPassword())
                        .setContentType("application/json").build();

        HTTPResponse response = HTTPClient.post(request, "{}");

        result = JsonHelper.fromJson(response.getBody(), RoleListResponse.class);

        completed(methodName);
        return result;
    }

    public List<String> getConfigurations(Server server) {
        final String methodName = "getConfigurations";
        start(methodName);
        final String url = server.getUrl() + "/eurekify/services/sageBrowsingService";
        HTTPRequest request = new HTTPRequest.Builder(url).setContentType("application/xml").build();
        log.info(methodName, "URL : " + url);
        log.info(methodName, "Request Body : " + getUniverseRequest(server));
        HTTPResponse response = HTTPClient.post(request, getUniverseRequest(server));
        log.info(methodName, "Response Body : " + response.getBody());
        List<String> configList = parseConfigurations(response.getBody());
        completed(methodName);

        return configList;
    }

    private String getUniverseRequest(Server server) {
        final String methodName = "getUniverses";
        start(methodName);
        SOAPMessage message = SOAPHelper.createAuthenticatedSOAPMessage(server.getUsername(), server.getPassword());
        try {
            SOAPBody body = message.getSOAPBody();
            body.addChildElement("getUniverses");
        } catch (SOAPException ex) {
            log.error(methodName, ex);
        }

        String output = SOAPHelper.toString(message);
        completed(methodName);
        return output;
    }

    private List<String> parseConfigurations(String xmlStr) {

        final String methodName = "parseConfigurations";
        List<String> configList = new ArrayList<>();
        start(methodName);

        if (xmlStr != null && !xmlStr.isEmpty()) {
            try {

                DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
                df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

                DocumentBuilder builder = df.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlStr)));

                XPath xPath = XPathFactory.newInstance().newXPath();
                NodeList nodeList =
                        (NodeList) xPath.compile("//masterConfig").evaluate(document, XPathConstants.NODESET);

                for (int i = 0; i < nodeList.getLength(); i++) {
                    configList.add(nodeList.item(i).getTextContent());
                }

            } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException ex) {
                log.error(methodName, ex);
            }
        }

        completed(methodName);
        return configList;
    }

}
