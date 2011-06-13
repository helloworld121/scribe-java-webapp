package org.scribe.webapp.oauth.boundary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.webapp.oauth.control.Provider;
import org.scribe.webapp.oauth.control.ProviderFactory;

/**
 * User: hal
 * Date: 13.06.2011
 * Time: 19:00:49
 */
public class OAuthService {

    private Log log = LogFactory.getLog(getClass());

    public OAuthUser addAuthorizationUrl(OAuthUser user) {
        log.info("Calling addAuthorizationUrl with oAuthType: " + user.getOAuthType());
        OAuthType oAuthType = user.getOAuthType();

        Provider provider = ProviderFactory.getProvider(oAuthType);

        provider.buildRedirectUrl(user);

        return user;
    }

    public OAuthUser readingUserData(OAuthUser user) {
        log.info("Calling readingUserData with oAuthType: " + user.getOAuthType());
        OAuthType oAuthType = user.getOAuthType();

        Provider provider = ProviderFactory.getProvider(oAuthType);

        provider.readUserData(user);

        return user;
    }

}
