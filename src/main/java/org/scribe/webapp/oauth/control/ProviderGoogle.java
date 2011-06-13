package org.scribe.webapp.oauth.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.webapp.oauth.boundary.OAuthUser;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 20:23:47
 */
public class ProviderGoogle
        implements Provider {

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


    public void readUserData(OAuthUser user) {
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
//        Token accessToken = service.getAccessToken(user.getToken(), verifier);


        OAuthRequest request = new OAuthRequest(Verb.GET, "http://www.google.com/m8/feeds/contacts/default/full");
//        service.signRequest(token, request);
        service.signRequest(accessToken, request);
        request.addHeader("GData-Version", "3.0");

        Response response = request.send();

        log.info("Response: ");
        log.info(response.getCode());
        log.info(response.getBody());

    }

}
