package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthUser;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 20:23:47
 */
public class ProviderGoogle
        extends AbstractProvider {

    private Log log = LogFactory.getLog(getClass());

    public void buildRedirectUrl(OAuthUser user) {
        OAuthService service = new ServiceBuilder()
                .provider(GoogleApi.class)
                .apiKey("anonymous")
                .apiSecret("anonymous")
                .scope("http://www.google.com/m8/feeds/")
                .callback("http://localhost:8080/callbackoauth")
                .build();
        Token token = service.getRequestToken();
        log.info("requestToken: " + token);

        String redirect = service.getAuthorizationUrl(token);
        log.info("Redirect URL: " + redirect);

        user.setOAuthToken(token.getToken());
        user.setOAuthSecret(token.getSecret());
        user.setOAuthRawResponse(token.getRawResponse());

        user.setOAuthAuthorizationUrl(redirect);
    }


    public void readUserData(OAuthUser user)
            throws OAuthProviderException {

        OAuthService service = new ServiceBuilder()
                .provider(GoogleApi.class)
                .apiKey("anonymous")
                .apiSecret("anonymous")
                .scope("http://www.google.com/m8/feeds/")
                .callback("http://localhost:8080/callbackoauth")
                .build();

        Verifier verifier = new Verifier(user.getOAuthVerifier());
        Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
        Token accessToken = service.getAccessToken(token, verifier);


        OAuthRequest request = new OAuthRequest(Verb.GET, "http://www.google.com/m8/feeds/contacts/default/full");
        service.signRequest(accessToken, request);
        request.addHeader("GData-Version", "3.0");

        Response response = request.send();

        checkResponseCode(response.getCode());

        String xml = response.getBody();
        log.debug("XML response: " + xml);

        String xPathExpressionName = "/feed/author/name/text()";
        try {
            String authorName = getValueOverXPath(xml, xPathExpressionName);
            user.setFullName(authorName);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionName + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionName);
        }

        String xPathExpressionEMail = "/feed/author/email/text()";
        try {
            String authorEMail = getValueOverXPath(xml, xPathExpressionEMail);
            user.setEMail(authorEMail);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionEMail + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionEMail);
        }

    }


    protected String getValueOverXPath(String xml, String xPathExpression)
            throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
//        docBuilderFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        // Parse the XML file and build the Document object in RAM
//        Document doc = docBuilder.parse(new File("/tmp.xml"));
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document doc = docBuilder.parse(is);

        // Normalize text representation.
        // Collapses adjacent text nodes into one node.
        doc.getDocumentElement().normalize();


        // use xpath to extract info from document object in Java
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xPathExpression);
        String value = (String) expr.evaluate(doc, XPathConstants.STRING);

        return value;
    }

}
