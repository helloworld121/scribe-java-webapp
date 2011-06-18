package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthUser;
import org.scribe.webapp.oauth.boundary.exception.OAuthProviderException;

/**
 * User: hal
 * Date: 18.06.2011
 * Time: 17:39:29
 */
public class ProviderTwitter
        extends AbstractProvider {

    private Log log = LogFactory.getLog(getClass());


    public void buildRedirectUrl(OAuthUser user) {
        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey("GBBzdY50VngbUETKF7N7jQ")
                .apiSecret("Dw4rwdrF11EidKa9eqDKls2eNR3xXYDAc3xiL1U")
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
                .provider(TwitterApi.class)
                .apiKey("GBBzdY50VngbUETKF7N7jQ")
                .apiSecret("Dw4rwdrF11EidKa9eqDKls2eNR3xXYDAc3xiL1U")
                .callback("http://localhost:8080/callbackoauth")
                .build();

        Verifier verifier = new Verifier(user.getOAuthVerifier());
        Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
        Token accessToken = service.getAccessToken(token, verifier);

        // get GUID
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.xml");
        service.signRequest(accessToken, request);

        Response response = request.send();

        checkResponseCode(response.getCode());

        String xml = response.getBody();
        log.debug("XML response: " + xml);


        String xPathExpressionGuid = "/user/id/text()";
        try {
            String id = getValueOverXPath(xml, xPathExpressionGuid);
            user.setProviderUserId(id);
            log.info("Found id: " + id);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionGuid + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionGuid);
        }

        String xPathExpressionName = "/user/name/text()";
        try {
            String name = getValueOverXPath(xml, xPathExpressionName);
            user.setName(name);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionName + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionName);
        }

        String xPathExpressionNickname = "/user/screen_name/text()";
        try {
            String nickname = getValueOverXPath(xml, xPathExpressionName);
            user.setNickname(nickname);
        } catch(Exception e) {
            log.error("Can't find expression: " + xPathExpressionName + " => XML: " + xml, e);
            throw new OAuthProviderException("Can't find expression: " + xPathExpressionName);
        }

    }
}
