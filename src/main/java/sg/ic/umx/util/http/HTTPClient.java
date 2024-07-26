package sg.ic.umx.util.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import sg.ic.umx.util.http.handler.HTTPResponseHandler;
import sg.ic.umx.util.http.helper.HTTPClientHelper;
import sg.ic.umx.util.http.model.HTTPDeleteWithBody;
import sg.ic.umx.util.http.model.HTTPParameter;
import sg.ic.umx.util.http.model.HTTPRequest;
import sg.ic.umx.util.http.model.HTTPResponse;
import sg.ic.umx.util.log.BaseLogger;

public class HTTPClient {
    private static final BaseLogger log = new BaseLogger(HTTPClient.class);
    private static final HTTPResponseHandler responseHandler = new HTTPResponseHandler();

    private static final String PUT = "put";
    private static final String DELETE = "delete";
    private static final String POST = "post";

    private HTTPClient() {}

    // GET METHODS
    public static HTTPResponse get(HTTPRequest request) {
        return get(request, null);
    }

    public static HTTPResponse get(final HTTPRequest request, HTTPParameter parameters) {

        HTTPResponse response = new HTTPResponse();

        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {
            // Construct URI
            URIBuilder builder = new URIBuilder(request.getUrl());

            // Add URL Parameters
            if (HTTPClientHelper.validateHttpParameter(parameters)) {
                parameters.entrySet().forEach(entry -> builder.addParameter(entry.getKey(), entry.getValue()));
            }

            HttpGet httpRequest = new HttpGet(builder.build());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error("get", ex.getMessage(), ex);
        }
        return response;
    }

    // POST METHODS
    public static HTTPResponse post(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse post(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse postWithProxy(final HTTPRequest request, String body, HttpHost proxy) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClientWithProxy(cookieStore, proxy)) {

            HttpPost httpRequest = new HttpPost(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(POST, ex.getMessage(), ex);
        }
        return result;
    }

    // PUT METHODS
    public static HTTPResponse put(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPut httpRequest = new HttpPut(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse put(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPut httpRequest = new HttpPut(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    // PATCH METHODS
    public static HTTPResponse patch(final HTTPRequest request, HTTPParameter params) {
        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPatch httpRequest = new HttpPatch(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (HTTPClientHelper.validateHttpParameter(params)) {

                // Set Parameters
                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse patch(final HTTPRequest request, String body) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpPatch httpRequest = new HttpPatch(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }

    public static HTTPResponse patchWithProxy(final HTTPRequest request, String body, HttpHost proxy) {

        HTTPResponse result = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClientWithProxy(cookieStore, proxy)) {

            HttpPatch httpRequest = new HttpPatch(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            result = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(PUT, ex.getMessage(), ex);
        }
        return result;
    }


    // DELETE METHODS
    public static HTTPResponse delete(final HTTPRequest request) {
        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HttpDelete httpRequest = new HttpDelete(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse delete(final HTTPRequest request, HTTPParameter params) {

        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HTTPDeleteWithBody httpRequest = new HTTPDeleteWithBody(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            // Set Parameters
            if (HTTPClientHelper.validateHttpParameter(params)) {

                List<NameValuePair> parameterList = HTTPClientHelper.getNVPList(params);

                httpRequest.setEntity(new UrlEncodedFormEntity(parameterList));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    public static HTTPResponse delete(final HTTPRequest request, String body) {

        HTTPResponse response = new HTTPResponse();
        CookieStore cookieStore = HTTPClientHelper.getCookieStore(request);

        try (CloseableHttpClient client = getHttpClient(cookieStore)) {

            HTTPDeleteWithBody httpRequest = new HTTPDeleteWithBody(request.getUrl());

            // Set Headers
            HTTPClientHelper.populateHeader(httpRequest, request.getHeaders());

            if (body != null && !body.isEmpty()) {
                // Set Request Body
                httpRequest.setEntity(new StringEntity(body));
            }

            // Execute Request
            response = execute(client, httpRequest, cookieStore);

        } catch (Exception ex) {
            log.error(DELETE, ex.getMessage(), ex);
        }
        return response;
    }

    private static CloseableHttpClient getHttpClient(CookieStore cookieStore) {

        return HttpClients.custom().setConnectionManager(HTTPConnectionManager.getInstance())
                .setConnectionManagerShared(true).setDefaultCookieStore(cookieStore).disableRedirectHandling().build();
    }

    private static CloseableHttpClient getHttpClientWithProxy(CookieStore cookieStore, HttpHost proxy) {

        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        return HttpClients.custom().setRoutePlanner(routePlanner).setConnectionManager(HTTPConnectionManager.getInstance())
                .setConnectionManagerShared(true).setDefaultCookieStore(cookieStore).disableRedirectHandling().build();
    }

    private static HTTPResponse execute(CloseableHttpClient client, HttpUriRequest request, CookieStore cookieStore)
            throws IOException {

        HTTPResponse response = client.execute(request, responseHandler);
        response.setCookies(cookieStore.getCookies());

        return response;
    }
}
