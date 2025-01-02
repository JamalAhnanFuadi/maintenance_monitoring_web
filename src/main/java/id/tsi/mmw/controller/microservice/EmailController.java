package id.tsi.mmw.controller.microservice;

import id.tsi.mmw.controller.BaseController;
import id.tsi.mmw.controller.Controller;
import id.tsi.mmw.controller.model.APIResponse;
import id.tsi.mmw.model.Email;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.util.http.HTTPClient;
import id.tsi.mmw.util.http.model.HTTPContentType;
import id.tsi.mmw.util.http.model.HTTPRequest;
import id.tsi.mmw.util.http.model.HTTPResponse;
import id.tsi.mmw.util.json.JsonHelper;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailController extends BaseController {

    public EmailController() {
        log = getLogger(this.getClass());
    }

    public void send(String recipients, String subject, String body) {
        final String methodName = "send";
        start(methodName);

        String uri = getProperty(Property.MAIL_MS_SERVER) + getProperty(Property.MAIL_MS_SEND_EMAIL_API);

        List<String> recipientsList = new ArrayList<>();
        recipientsList.add(recipients);

        Email payload = new Email();
        payload.setSender(getProperty(Property.MAIL_SMTP_SENDER));
        payload.setRecipients(recipientsList);
        payload.setSubject(subject);
        payload.setBody(body);

        //log.debug(methodName, JsonHelper.toJson(payload));

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        log.debug(methodName, httpResponse.getBody());

        APIResponse response = JsonHelper.fromJson(httpResponse.getBody(), APIResponse.class);
        if(response.getCode() == HttpStatus.SC_OK)
        {
            log.debug(methodName, "Email sent successfully to " + recipients);
        }

        completed(methodName);
    }

    private HTTPRequest buildHTTPRequest(String uri) {

        return new HTTPRequest.Builder(uri)
                .setContentType("application/json")
                .addHeader(getProperty(Property.MAIL_MS_KEY_NAME), getProperty(Property.MAIL_MS_API_KEY))
                .build();
    }
}
