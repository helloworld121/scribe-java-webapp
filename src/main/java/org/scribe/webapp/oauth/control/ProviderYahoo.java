package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthUser;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 21:40:37
 */
public class ProviderYahoo
        extends AbstractProvider {

    private Log log = LogFactory.getLog(getClass());


    public void buildRedirectUrl(OAuthUser user) {
        OAuthService service = new ServiceBuilder()
                .provider(YahooApi.class)
                .apiKey("dj0yJmk9TXZDWVpNVVdGaVFmJmQ9WVdrOWMweHZXbkZLTkhVbWNHbzlNVEl5TWprd05qUTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD0wMw--")
                .apiSecret("262be559f92a2be20c4c039419018f2b48cdfce9")
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
                .provider(YahooApi.class)
                .apiKey("dj0yJmk9TXZDWVpNVVdGaVFmJmQ9WVdrOWMweHZXbkZLTkhVbWNHbzlNVEl5TWprd05qUTJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD0wMw--")
                .apiSecret("262be559f92a2be20c4c039419018f2b48cdfce9")
                .callback("http://localhost:8080/callbackoauth")
                .build();
        Verifier verifier = new Verifier(user.getOAuthVerifier());
        Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
        Token accessToken = service.getAccessToken(token, verifier);

        // get GUID
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://social.yahooapis.com/v1/me/guid");
        service.signRequest(accessToken, request);

        Response response = request.send();

        checkResponseCode(response.getCode());

        String xml = response.getBody();
        log.debug("XML response: " + xml);

        String guid = null;

        String xPathExpressionGuid = "/guid/value/text()";
        try {
            guid = getValueOverXPath(xml, xPathExpressionGuid);
            user.setProviderUserId(guid);
            log.info("Found guid: " + guid);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionGuid + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionGuid);
        }


        // get User data
//        request = new OAuthRequest(Verb.GET, "http://social.yahooapis.com/v1/user/" + guid + "/profile/status");
//        request = new OAuthRequest(Verb.GET, "http://social.yahooapis.com/v1/user/" + guid + "/profile/usercard");
        request = new OAuthRequest(Verb.GET, "http://social.yahooapis.com/v1/user/" + guid + "/profile");
//        request = new OAuthRequest(Verb.GET, "http://social.yahooapis.com/v1/user/" + guid + "/contacts");
        service.signRequest(accessToken, request);

        response = request.send();

        checkResponseCode(response.getCode());

        xml = response.getBody();
        log.debug("XML response: " + xml);

        String xPathExpressionName = "/profile/nickname/text()";
        try {
            String nickname = getValueOverXPath(xml, xPathExpressionName);
            user.setName(nickname);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionName + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionName);
        }


    }

}
