package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.net.HttpURLConnection;

/**
 * User: hal
 * Date: 18.06.2011
 * Time: 11:18:01
 */
public abstract class AbstractProvider
        implements Provider {

    private Log log = LogFactory.getLog(getClass());

    protected void checkResponseCode(int code)
            throws OAuthProviderException {
        log.info("Given HTTP response code: " + code);

        if(code == HttpURLConnection.HTTP_OK) {
            // everything is ok
            return;
        }

        throw new OAuthProviderException("HTTP code: '" + code + "' indicates, that problems in communication occured.");

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
